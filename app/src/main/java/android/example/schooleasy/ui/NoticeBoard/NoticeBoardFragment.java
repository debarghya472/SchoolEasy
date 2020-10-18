package android.example.schooleasy.ui.NoticeBoard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.DisQuestionReply;
import android.example.schooleasy.dataclass.NoticeDetails;
import android.example.schooleasy.dataclass.NoticeList;
import android.example.schooleasy.network.JsonPlaceholderApi;
import android.example.schooleasy.network.RetrofitClientInstance;
import android.example.schooleasy.ui.LoadDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NoticeBoardFragment extends Fragment {
    private JsonPlaceholderApi jsonPlaceholderApi;
    private LoadDialog loadDialog;
    private List<NoticeDetails> mlistview;
    private RecyclerView recyclerView;

    private EditText addHeading;
    private EditText addNoticeTxt;
    private Button sendNoticeBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notice,container,false);
        addNoticeTxt=root.findViewById(R.id.addNotice);
        sendNoticeBtn=root.findViewById(R.id.sendNotice);
        addHeading=root.findViewById(R.id.addHeading);

        loadDialog= new LoadDialog(getActivity());
        mlistview= new ArrayList<NoticeDetails>();
        recyclerView = root.findViewById(R.id.recycler_view_all_notice);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
        SharedPreferences info = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        String standard = info.getString("standard",null);
        String token = info.getString("token",null);
        String ifTeacher = info.getString("IsTeacher","No");

        sendNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ifTeacher.equals("Yes")){
                    String noticeCont = addNoticeTxt.getText().toString();
                    String heading = addHeading.getText().toString();
                    loadDialog.startLoad();
                    NoticeDetails details = new NoticeDetails(noticeCont,heading,null);
                    Call<Void> call = jsonPlaceholderApi.addNotice(standard,details,"Bearer "+token);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(!response.isSuccessful())  {
                                Toast.makeText(getContext(),"Notice could not be added",Toast.LENGTH_SHORT).show();
                                loadDialog.dismissLoad();
                                return;
                            }
                            loadDialog.dismissLoad();
                            Toast.makeText(getContext(),"Notice Added",Toast.LENGTH_LONG).show();
                            getNotice(standard);

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                            loadDialog.dismissLoad();
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(),"Only Teachers are allowed to post Notice",Toast.LENGTH_LONG).show();
                }
            }
        });

        getNotice(standard);

        return  root;
    }
    private void attachAdapter(List<NoticeDetails> list){
        final NoticeBoardAdapter adapter= new NoticeBoardAdapter(list,getContext());
        recyclerView.setAdapter(adapter);
    }
    private void getNotice(String standard){
        loadDialog.startLoad();
        Call<NoticeList> call = jsonPlaceholderApi.getNotices(standard);
        call.enqueue(new Callback<NoticeList>() {
            @Override
            public void onResponse(Call<NoticeList> call, Response<NoticeList> response) {
                if(!response.isSuccessful())  {
                    Toast.makeText(getContext(),"Notices could not be found",Toast.LENGTH_SHORT).show();
                    loadDialog.dismissLoad();
                    return;
                }
                loadDialog.dismissLoad();
                List<NoticeDetails> noticeDetailsList = response.body().getNoticeDetailsList();
                if(noticeDetailsList==null){
                    Toast.makeText(getContext(),"No notices in the Notice Board",Toast.LENGTH_SHORT).show();
                }
                else{
                    for(NoticeDetails details: noticeDetailsList){
                        NoticeDetails noticeDetails = new NoticeDetails(details.getNoticeContent(),details.getHeading(),details.getTeacherName());
                        mlistview.add(noticeDetails);
                    }
                }
                attachAdapter(mlistview);
            }

            @Override
            public void onFailure(Call<NoticeList> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                loadDialog.dismissLoad();
            }
        });
    }
}
