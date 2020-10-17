package android.example.schooleasy.ui.teacher;

import android.content.Context;
import android.content.SharedPreferences;
import android.example.schooleasy.JsonPlaceholderApi;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.Student;
import android.example.schooleasy.dataclass.Teacher;
import android.example.schooleasy.dataclass.TeacherList;
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

public class TeacherProfiles extends Fragment {
    private JsonPlaceholderApi jsonPlaceholderApi;
    private LoadDialog loadDialog;
    private List<Teacher> mlistview;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_teacher,container,false);
        loadDialog= new LoadDialog(getActivity());
        mlistview= new ArrayList<Teacher>();
        recyclerView = root.findViewById(R.id.recycler_view_teachers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        SharedPreferences info = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        String standard = info.getString("standard",null);
        loadDialog.startLoad();

        Call<TeacherList> call = jsonPlaceholderApi.showTeachersProfile(standard);
        call.enqueue(new Callback<TeacherList>() {
            @Override
            public void onResponse(Call<TeacherList> call, Response<TeacherList> response) {
                if(!response.isSuccessful())  {
                    Toast.makeText(getContext(),"No students found",Toast.LENGTH_SHORT).show();
                    loadDialog.dismissLoad();
                    return;
                }
                loadDialog.dismissLoad();
                List<Teacher> teacherList = response.body().getTeacherList();
                if(teacherList==null){
                    Toast.makeText(getContext(),"No teachers for this class currently",Toast.LENGTH_SHORT).show();
                }
                else{
                    for(Teacher teacher: teacherList){
                        mlistview.add(new Teacher(teacher.getEmail(),null,null,teacher.getName(),teacher.getMobile(),teacher.getSubject()));
                    }
                    attach_teacherAdapter(mlistview);
                }
            }

            @Override
            public void onFailure(Call<TeacherList> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                loadDialog.dismissLoad();
            }
        });

        return root;
    }
    private void attach_teacherAdapter(List<Teacher> list){
        final TeachersAdapter adapter = new TeachersAdapter(list,getActivity());
        recyclerView.setAdapter(adapter);

    }
}
