package android.example.schooleasy.ui.discussionForum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.schooleasy.MainActivity;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.DisQuestion;
import android.example.schooleasy.dataclass.Student;
import android.example.schooleasy.network.JsonPlaceholderApi;
import android.example.schooleasy.network.RetrofitClientInstance;
import android.example.schooleasy.ui.Feed.Feed;
import android.example.schooleasy.ui.LoadDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DiscussionForumQuestions extends AppCompatActivity {
    private JsonPlaceholderApi jsonPlaceholderApi;
    private LoadDialog loadDialog;
    private List<Student> mlistview;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_forum);
        ChipNavigationBar bottomNav= findViewById(R.id.bottom_nav);

        loadDialog= new LoadDialog(getParent());
        mlistview= new ArrayList<Student>();
        recyclerView = findViewById(R.id.recycler_view_otherStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        setTitle("Discussion Forum");

        bottomNav.setItemSelected(R.id.discussion,true);

        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;
                    case R.id.Feed:
                        startActivity(new Intent(getApplicationContext(), Feed.class));
                        break;
                    case R.id.discussion:
                        break;
                }
            }
        });

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        loadDialog.startLoad();
        Call<DisQuestion> call = jsonPlaceholderApi.getDisQs();
        call.enqueue(new Callback<DisQuestion>() {
            @Override
            public void onResponse(Call<DisQuestion> call, Response<DisQuestion> response) {
                if(!response.isSuccessful())  {
                    Toast.makeText(getApplicationContext(),"No questions found",Toast.LENGTH_SHORT).show();
                    loadDialog.dismissLoad();
                    return;
                }
                loadDialog.dismissLoad();

            }

            @Override
            public void onFailure(Call<DisQuestion> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                loadDialog.dismissLoad();
            }
        });

    }
}