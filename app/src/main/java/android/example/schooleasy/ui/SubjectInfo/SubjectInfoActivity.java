package android.example.schooleasy.ui.SubjectInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.example.schooleasy.BuildConfig;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.Material;
import android.example.schooleasy.dataclass.MaterialList;
import android.example.schooleasy.network.JsonPlaceholderApi;
import android.example.schooleasy.network.RetrofitClientInstance;
import android.example.schooleasy.ui.LoadDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubjectInfoActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CODE = 200;
    private  TextView textView;
    private TextView standard1;
    private File file;
    private Button upload;
    private JsonPlaceholderApi jsonPlaceholderApi;
    private LoadDialog loadDialog;
    private RecyclerView recyclerView;
    private Uri filePath;
    private List<Material> mlistView;
    private DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_info);
        getWindow().setBackgroundDrawableResource(R.color.background_color);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_view_materials);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        mlistView= new ArrayList<Material>();

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        loadDialog = new LoadDialog(this);

        standard1 = this.findViewById(R.id.stand);

        Intent intent = getIntent();
        String subName = intent.getStringExtra("subname");
        String standard = intent.getStringExtra("Standard");
        String stand = intent.getStringExtra("Stand");
        String subjectId = intent.getStringExtra("SubjectId");
        Log.d("sub id", subjectId);

        standard1.setText("Standard " + stand);

        SharedPreferences info = this.getSharedPreferences("info", MODE_PRIVATE);
        String token = info.getString("token",null);
        setTitle(subName);

        Button bn = findViewById(R.id.but);
        textView = findViewById(R.id.text);
        upload = findViewById(R.id.Upload);

        loadDialog.startLoad();
        Call<MaterialList> call = jsonPlaceholderApi.getFiles(subjectId);
        call.enqueue(new Callback<MaterialList>() {
            @Override
            public void onResponse(Call<MaterialList> call, Response<MaterialList> response) {
                if(!response.isSuccessful())  {
                    Toast.makeText(getApplicationContext(),"No materials found",Toast.LENGTH_SHORT).show();
                    loadDialog.dismissLoad();
                    return;
                }
                loadDialog.dismissLoad();
                List<Material> materials = response.body().getMaterials();
                if(materials==null){
                    Toast.makeText(getApplicationContext(),"No materials added",Toast.LENGTH_SHORT).show();
                }
                else {
                    for (Material material:materials){
                        mlistView.add(new Material(material.getText(),material.getFilePath()));
                    }
                    attatchAdapter(mlistView);
                }

            }

            @Override
            public void onFailure(Call<MaterialList> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                loadDialog.dismissLoad();
            }
        });

//        ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri path = Uri.fromFile(file);
//                Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//                pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                pdfOpenintent.setDataAndType(path, "*/pdf");
//                try {
//                    startActivity(pdfOpenintent);
//                }
//                catch (ActivityNotFoundException e) {
//
//                }
//            }
//        });

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=23){
                    if(checkPermission()){
                        filePicker();
                    }
                    else{
                        requestPermission();
                    }
                }
                else{
                    filePicker();
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = FilePath.getPath(getApplication(), filePath);

                file=new File(path);

                RequestBody textname =RequestBody.create(MediaType.parse("multipart/form-data"),"homework-1");


                RequestBody fileitem =RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part requestFile =MultipartBody.Part.createFormData("materials",file.getName(),fileitem);

                loadDialog.startLoad();
                Call<ResponseBody> call = jsonPlaceholderApi.uploadFile(requestFile,subjectId,"Bearer "+token,textname);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"File Upload successfully",Toast.LENGTH_LONG).show();
                            loadDialog.dismissLoad();
                        }
                        loadDialog.dismissLoad();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                        loadDialog.dismissLoad();
                    }
                });
            }
        });
    }
    private void filePicker(){

        //.Now Permission Working
        Toast.makeText(SubjectInfoActivity.this, "File Picker Call", Toast.LENGTH_SHORT).show();
        //Let's Pick File
        Intent openFile =new Intent(Intent.ACTION_GET_CONTENT);
        openFile.addCategory(Intent.CATEGORY_OPENABLE);
        openFile.setType("*/*");
        startActivityForResult(openFile,REQUEST_CODE);
//        Intent opengallery=new Intent(Intent.ACTION_PICK);
//        opengallery.setType("image/*");
//        startActivityForResult(opengallery,REQUEST_GALLERY);
    }
    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(SubjectInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(SubjectInfoActivity.this,"Please enter permission to Upload a file",Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(SubjectInfoActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }
    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(SubjectInfoActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        } else
            return false;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(SubjectInfoActivity.this, "Permission Successfull", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SubjectInfoActivity.this, "Permission Failed", Toast.LENGTH_SHORT).show();
                }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data.getData()!=null){
            filePath =data.getData();
            //String path = uri.getPath();

        }
    }
    private void attatchAdapter(List<Material> list){
        final MaterialAdapter adapter = new MaterialAdapter(list,getApplicationContext());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MaterialAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Material material) {
                String path = material.getFilePath();
                String a = path.substring(9);
                String finalPath = "localhost:"+a;
                Log.d("fh",finalPath);

//                final Uri data = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", new File(finalPath));
//                getApplicationContext().grantUriPermission(getApplicationContext().getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                final Intent intent = new Intent(Intent.ACTION_VIEW)
//                        .setDataAndType(data, "*/*")
//                        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                getApplicationContext().startActivity(intent);

                downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(path);
                DownloadManager.Request request =new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);

            }
        });
    }

}