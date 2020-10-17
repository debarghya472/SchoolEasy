package android.example.schooleasy.ui.discussionForum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.schooleasy.MainActivity;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.DisQuestion;
import android.example.schooleasy.dataclass.DisQuestionReply;
import android.example.schooleasy.dataclass.DisQuestionsList;
import android.example.schooleasy.dataclass.Student;
import android.example.schooleasy.dataclass.UserDetailsForDis;
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
    private List<DisQuestionReply> mlistview;
    private RecyclerView recyclerView;
    private String quest;
    private UserDetailsForDis details;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_forum);
        ChipNavigationBar bottomNav= findViewById(R.id.bottom_nav);

        loadDialog= new LoadDialog(this);
        mlistview= new ArrayList<DisQuestionReply>();
        recyclerView = findViewById(R.id.recycler_view_all_questions);
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

        SharedPreferences info = getApplicationContext().getSharedPreferences("info", Context.MODE_PRIVATE);
        String standardId = info.getString("StandardId",null);

        loadDialog.startLoad();
        Call<DisQuestionsList> call = jsonPlaceholderApi.getDisQs(standardId);
        call.enqueue(new Callback<DisQuestionsList>() {
            @Override
            public void onResponse(Call<DisQuestionsList> call, Response<DisQuestionsList> response) {
                if(!response.isSuccessful())  {
                    Toast.makeText(getApplicationContext(),"No questions found",Toast.LENGTH_SHORT).show();
                    loadDialog.dismissLoad();
                    return;
                }
                loadDialog.dismissLoad();
                List<DisQuestion> questions = response.body().getQuestionList();
                if(questions== null){
                    Toast.makeText(getApplicationContext(),"No questions in the forum",Toast.LENGTH_SHORT).show();
                }
                else{
                    for(DisQuestion question : questions){
                        details = question.getUser();
                        quest=question.getQuestion();
                        name = details.getName();
                        mlistview.add(new DisQuestionReply(name,quest));
                    }
                }

                attachAdapter(mlistview);

            }

            @Override
            public void onFailure(Call<DisQuestionsList> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                loadDialog.dismissLoad();
            }
        });

    }
    private void attachAdapter(List<DisQuestionReply> list){
        final QuestionAdapter adapter = new QuestionAdapter(list,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}