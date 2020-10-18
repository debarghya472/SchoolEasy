package android.example.schooleasy.ui.discussionForum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.schooleasy.MainActivity;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.Answer;
import android.example.schooleasy.dataclass.DisAnswer;
import android.example.schooleasy.dataclass.DisAnswerList;
import android.example.schooleasy.dataclass.DisAnswerReply;
import android.example.schooleasy.dataclass.DisQuestionReply;
import android.example.schooleasy.dataclass.UserDetailsForDis;
import android.example.schooleasy.network.JsonPlaceholderApi;
import android.example.schooleasy.network.RetrofitClientInstance;
import android.example.schooleasy.ui.Feed.Feed;
import android.example.schooleasy.ui.LoadDialog;
import android.os.Bundle;
import android.util.Log;
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

public class DicussionForumAnswers extends AppCompatActivity {
    private JsonPlaceholderApi jsonPlaceholderApi;
    private LoadDialog loadDialog;
    private List<DisAnswerReply> mlistview;
    private RecyclerView recyclerView;

    private EditText addAnsTxt;
    private Button sendAnsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicussion_forum_answers);
        ChipNavigationBar bottomNav= findViewById(R.id.bottom_nav);
        addAnsTxt=findViewById(R.id.addAns);
        sendAnsBtn=findViewById(R.id.sendAns);

        loadDialog= new LoadDialog(this);
        mlistview= new ArrayList<DisAnswerReply>();
        recyclerView = findViewById(R.id.recycler_view_all_answers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        SharedPreferences info = getApplicationContext().getSharedPreferences("info", Context.MODE_PRIVATE);
        String discId = info.getString("DiscId",null);
        String token = info.getString("token",null);

        setTitle("Discussion Forum");
        Intent intent = getIntent();
        String qsId = intent.getStringExtra("qsId");
        Log.d("id", qsId);

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

        sendAnsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans = addAnsTxt.getText().toString();
                loadDialog.startLoad();
                DisAnswerReply answerReply = new DisAnswerReply(ans,null);
                Call<Void> call = jsonPlaceholderApi.postAnswer(qsId,answerReply,"Bearer "+ token);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(!response.isSuccessful())  {
                            Toast.makeText(getApplicationContext(),"Answer could not be added",Toast.LENGTH_SHORT).show();
                            loadDialog.dismissLoad();
                            return;
                        }
                        loadDialog.dismissLoad();
                        Toast.makeText(getApplicationContext(),"Answer Added",Toast.LENGTH_LONG).show();
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
        Call<DisAnswerList> call = jsonPlaceholderApi.getDisAns(qsId);
        call.enqueue(new Callback<DisAnswerList>() {
            @Override
            public void onResponse(Call<DisAnswerList> call, Response<DisAnswerList> response) {
                if(!response.isSuccessful())  {
                    Toast.makeText(getApplicationContext(),"No answers found",Toast.LENGTH_SHORT).show();
                    loadDialog.dismissLoad();
                    return;
                }
                loadDialog.dismissLoad();
                DisAnswer disAnswer = response.body().getDisAnswer();

                List<Answer> answerList= disAnswer.getAnswerList();
                if(answerList==null){
                    Toast.makeText(getApplicationContext(),"No answers in the forum",Toast.LENGTH_SHORT).show();
                }
                else {
                    for(Answer answer: answerList){
                        String answer1 = answer.getAnswer();
                        String userName = answer.getUserName();
                        mlistview.add(new DisAnswerReply(answer1,userName));
                    }
                    attachAdapter(mlistview);
                }
            }

            @Override
            public void onFailure(Call<DisAnswerList> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                loadDialog.dismissLoad();
            }
        });
    }
    private void attachAdapter(List<DisAnswerReply> list){
        final AnswerAdapter adapter = new AnswerAdapter(list,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

}