package android.example.schooleasy.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.schooleasy.dataclass.Standard;
import android.example.schooleasy.dataclass.Subject;
import android.example.schooleasy.network.JsonPlaceholderApi;
import android.example.schooleasy.network.RetrofitClientInstance;
import android.example.schooleasy.ui.LoadDialog;
import android.example.schooleasy.ui.SubjectInfo.SubjectInfoActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.example.schooleasy.R;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private TextView textView;
    private RecyclerView recyclerView;
    private List<Subject> mSubList;
    private JsonPlaceholderApi jsonPlaceholderApi;
    private LoadDialog loadDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_student, container, false);

        SharedPreferences info = getContext().getSharedPreferences("info",Context.MODE_PRIVATE);

        loadDialog = new LoadDialog(getActivity());

        textView = (TextView) root.findViewById(R.id.standard1);
        recyclerView = root.findViewById(R.id.recycler_view_subject);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        String stand=info.getString("standard","0");

        textView.setText("Standard "+stand);

        mSubList = new ArrayList<Subject>();

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);


        Call<StandardResponse> call1 = jsonPlaceholderApi.getStandardId(stand);
        call1.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"User not logged in",Toast.LENGTH_SHORT).show();

                    return;
                }

                Standard standard =response.body().getStandard();
                String standardId = standard.getStandardId();
                String discussionForumId = standard.getDiscId();
                SharedPreferences info = getContext().getSharedPreferences("info",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =info.edit();
                editor.putString("StandardId",standardId);
                editor.putString("DiscId",discussionForumId);
                Log.d("forum", "id is "+discussionForumId);
                editor.apply();
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        getsubject(stand);


//        mSubList.add(0,"History");
//        mSubList.add(1,"Geography");
//        mSubList.add(2,"Maths");
//        mSubList.add(3,"English");


        return root;
    }
    private void attachAdapter(List<Subject> list,String s,String stand){

        final SubjectAdapter adapter = new SubjectAdapter(list,getActivity());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SubjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Subject subject) {
                Intent intent = new Intent(getActivity(), SubjectInfoActivity.class);
                intent.putExtra("subname",subject.getSubname());
                intent.putExtra("Standard",s);
                intent.putExtra("Stand",stand);
                startActivity(intent);
            }
        });


    }
    public void getsubject(String stand){
        SharedPreferences info = getContext().getSharedPreferences("info",Context.MODE_PRIVATE);

//        Toast.makeText(getContext(),info.getString("StandardId","0"),Toast.LENGTH_LONG).show();
        String st=info.getString("StandardId","0");


        loadDialog.startLoad();
        Call<SubjectList> call = jsonPlaceholderApi.getSubject(st);
        call.enqueue(new Callback<SubjectList>() {
            @Override
            public void onResponse(Call<SubjectList> call, Response<SubjectList> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"User not logged in",Toast.LENGTH_SHORT).show();
                    loadDialog.dismissLoad();
                    return;
                }
                loadDialog.dismissLoad();

                List<Subject> subjectList = response.body().getSubjectList();

                for(Subject subject : subjectList){
                    mSubList.add(new Subject(subject.getSubname(),subject.getTeacher(),subject.getSubid()));
                }
                attachAdapter(mSubList,st,stand);

            }

            @Override
            public void onFailure(Call<SubjectList> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT);
                loadDialog.dismissLoad();

            }
        });

    }
}