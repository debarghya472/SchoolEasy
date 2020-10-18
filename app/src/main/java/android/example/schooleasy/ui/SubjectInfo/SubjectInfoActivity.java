package android.example.schooleasy.ui.SubjectInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.example.schooleasy.R;
import android.example.schooleasy.network.JsonPlaceholderApi;
import android.example.schooleasy.network.RetrofitClientInstance;
import android.example.schooleasy.ui.LoadDialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

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
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_info);

        loadDialog =new LoadDialog(this);

        ll=(LinearLayout)findViewById(R.id.view_mat);
        standard1 =this.findViewById(R.id.stand);

        Intent intent=getIntent();
        String subName = intent.getStringExtra("subname");
        String standard =intent.getStringExtra("Standard");
        String stand =intent.getStringExtra("Stand");

        standard1.setText("Standard "+stand);

        SharedPreferences info = this.getSharedPreferences("info", MODE_PRIVATE);
        setTitle(subName);

        Button bn = findViewById(R.id.but);
        textView =findViewById(R.id.text);
        upload =findViewById(R.id.Upload);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri path = Uri.fromFile(file);
                Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pdfOpenintent.setDataAndType(path, "*/pdf");
                try {
                    startActivity(pdfOpenintent);
                }
                catch (ActivityNotFoundException e) {

                }
            }
        });

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

                Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
                jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

                RequestBody textname =RequestBody.create(MediaType.parse("multipart/form-data"),"homework-1");


                RequestBody fileitem =RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part requestFile =MultipartBody.Part.createFormData("materials",file.getName(),fileitem);

                loadDialog.startLoad();
                Call<ResponseBody> call = jsonPlaceholderApi.uploadFile(requestFile,textname,standard);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"File Upload successfully",Toast.LENGTH_LONG).show();
                            loadDialog.dismissLoad();
                        }
                        Toast.makeText(getApplicationContext(),"Cannot Upload File",Toast.LENGTH_LONG).show();
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
        }else
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
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri uri =data.getData();
            //String path = uri.getPath();

            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+uri.getPath());
            textView.setText(file.getName());
//                Toast.makeText(this,file.getName(),Toast.LENGTH_LONG).show();



        }
    }

}