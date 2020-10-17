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
import android.example.schooleasy.dataclass.Forum;
import android.example.schooleasy.dataclass.UserDetailsForDis;
import android.example.schooleasy.network.JsonPlaceholderApi;
import android.example.schooleasy.network.RetrofitClientInstance;
import android.example.schooleasy.ui.Feed.Feed;
import android.example.schooleasy.ui.LoadDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText addQsTxt;
    private Button sendQsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_forum);
        ChipNavigationBar bottomNav= findViewById(R.id.bottom_nav);
        addQsTxt=findViewById(R.id.addQs);
        sendQsBtn=findViewById(R.id.sendQs);

        loadDialog= new LoadDialog(this);
        mlistview= new ArrayList<DisQuestionReply>();
        recyclerView = findViewById(R.id.recycler_view_all_questions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        SharedPreferences info = getApplicationContext().getSharedPreferences("info", Context.MODE_PRIVATE);
        String standardId = info.getString("StandardId",null);
        String discId = info.getString("DiscId",null);
        String token = info.getString("token",null);

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

        sendQsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qs = addQsTxt.getText().toString();
                loadDialog.startLoad();
                DisQuestionReply question = new DisQuestionReply(null,qs);
                Call<Void> call = jsonPlaceholderApi.postQuestion(discId,question,"Bearer "+ token);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(!response.isSuccessful())  {
                            Toast.makeText(getApplicationContext(),"Question could not be added",Toast.LENGTH_SHORT).show();
                            loadDialog.dismissLoad();
                            return;
                        }
                        loadDialog.dismissLoad();
                        Toast.makeText(getApplicationContext(),"Question Added",Toast.LENGTH_LONG).show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                        loadDialog.dismissLoad();
                    }
                });
            }
        });



        loadDialog.startLoad();
        Call<Forum> call = jsonPlaceholderApi.getDisQs(discId);
        call.enqueue(new Callback<Forum>() {
            @Override
            public void onResponse(Call<Forum> call, Response<Forum> response) {
                if(!response.isSuccessful())  {
                    Toast.makeText(getApplicationContext(),"No questions found",Toast.LENGTH_SHORT).show();
                    loadDialog.dismissLoad();
                    return;
                }
                loadDialog.dismissLoad();
                DisQuestionsList questionsList = response.body().getForum();
                List<DisQuestion> questions = questionsList.getQuestionList();

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
            public void onFailure(Call<Forum> call, Throwable t) {
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