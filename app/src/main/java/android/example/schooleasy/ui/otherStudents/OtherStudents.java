package android.example.schooleasy.ui.otherStudents;

import android.content.Context;
import android.content.SharedPreferences;
import android.example.schooleasy.dataclass.StudentProfileResponse;
import android.example.schooleasy.network.JsonPlaceholderApi;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.Student;
import android.example.schooleasy.dataclass.StudentList;
import android.example.schooleasy.network.RetrofitClientInstance;
import android.example.schooleasy.ui.LoadDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OtherStudents extends Fragment {
    private JsonPlaceholderApi jsonPlaceholderApi;
    private LoadDialog loadDialog;
    private List<Student> mlistview;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_otherstudents,container,false);
        loadDialog= new LoadDialog(getActivity());
        mlistview= new ArrayList<Student>();
        recyclerView = root.findViewById(R.id.recycler_view_otherStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        SharedPreferences info = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        String standard = info.getString("standard",null);

        loadDialog.startLoad();
        Call<StudentProfileResponse> call= jsonPlaceholderApi.showStudentsProfile(standard);
        call.enqueue(new Callback<StudentProfileResponse>() {
            @Override
            public void onResponse(Call<StudentProfileResponse> call, Response<StudentProfileResponse> response) {
                if(!response.isSuccessful())  {
                    Toast.makeText(getContext(),"No students found",Toast.LENGTH_SHORT).show();
                    loadDialog.dismissLoad();
                    return;
                }
                loadDialog.dismissLoad();
                StudentList students = response.body().getStudentList();
                List<Student> studentList = students.getStudentList();


                if(studentList==null){
                    Toast.makeText(getContext(),"No students in this class currently",Toast.LENGTH_SHORT).show();
                }
                else{
                    for(Student student: studentList){
                        mlistview.add(new Student(student.getEmail(),null,student.getName(), student.getStandard()));
                    }
                    attach_otherStudentsAdapter(mlistview);
                }

            }

            @Override
            public void onFailure(Call<StudentProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                loadDialog.dismissLoad();
            }
        });


        return root;
    }
    private void attach_otherStudentsAdapter(List<Student> mlistview){
        final OtherStudentsAdapter adapter = new OtherStudentsAdapter(mlistview,getActivity());
        recyclerView.setAdapter(adapter);

    }
}
