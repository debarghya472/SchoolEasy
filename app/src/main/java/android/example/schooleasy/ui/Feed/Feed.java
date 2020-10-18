package android.example.schooleasy.ui.Feed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.schooleasy.MainActivity;
import android.example.schooleasy.R;

import android.example.schooleasy.dataclass.FeedData;
import android.example.schooleasy.dataclass.Teacher;
import android.example.schooleasy.network.JsonPlaceholderApi;
import android.example.schooleasy.network.RetrofitClientInstance;
import android.example.schooleasy.ui.SubjectInfo.SubjectInfoActivity;
import android.example.schooleasy.ui.login.loginSignupActivity;

import android.example.schooleasy.ui.discussionForum.DiscussionForumQuestions;

import android.example.schooleasy.ui.teacher.TeachersAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.security.AccessController.getContext;

public class Feed extends AppCompatActivity {

    private List<FeedData> mfeedDataList;
    private RecyclerView recyclerView;
    private EditText editText;
    private JsonPlaceholderApi jsonPlaceholderApi;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getWindow().setBackgroundDrawableResource(R.color.background_color);
        setTitle("Feed");
        ChipNavigationBar bottomNav= findViewById(R.id.bottom_nav);

        bottomNav.setItemSelected(R.id.Feed,true);
        editText= findViewById(R.id.addAns);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.click);
        LinearLayout linearLayout1 =(LinearLayout )findViewById(R.id.postAns);
        Button button = findViewById(R.id.sendAns);

        mfeedDataList=new ArrayList<FeedData>();
        recyclerView = findViewById(R.id.recycler_view_feeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout1.setVisibility(View.VISIBLE);
            }
        });
        retrofit = RetrofitClientInstance.getRetrofitInstance();
        jsonPlaceholderApi =retrofit.create(JsonPlaceholderApi.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postFeed();
            }
        });

        Call<FeedDataResponse> call = jsonPlaceholderApi.getFeed();
        call.enqueue(new Callback<FeedDataResponse>() {
            @Override
            public void onResponse(Call<FeedDataResponse> call, Response<FeedDataResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Feed.this,"User not logged in",Toast.LENGTH_SHORT).show();
                }
                List<FeedData> feedDataList=  response.body().getFeedDataList();
                for(FeedData feedData: feedDataList){
                    mfeedDataList.add(new FeedData(feedData.getCaption(),feedData.getFile()));
                    attach_feedAdapter(mfeedDataList);
                }

            }

            @Override
            public void onFailure(Call<FeedDataResponse> call, Throwable t) {

            }
        });

        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;
                    case R.id.Feed:
                        break;
                    case R.id.discussion:
                        startActivity(new Intent(getApplicationContext(), DiscussionForumQuestions.class));
                        break;
                }
            }
        });

    }

    private void postFeed() {
        SharedPreferences info = Feed.this.getSharedPreferences("info",MODE_PRIVATE);
        String token =info.getString("token",null);
        String feedPost = editText.getText().toString();
        Toast.makeText(Feed.this,feedPost,Toast.LENGTH_SHORT).show();
        FeedData feedData = new FeedData(feedPost,null);
        Call<FeedData> call =jsonPlaceholderApi.postFeed(feedData,"Bearer "+token);
        call.enqueue(new Callback<FeedData>() {
            @Override
            public void onResponse(Call<FeedData> call, Response<FeedData> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Feed.this,"Feed Added successfully",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Feed.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FeedData> call, Throwable t) {

                Toast.makeText(Feed.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent =getIntent();
        finish();startActivity(intent);

    }

    private void attach_feedAdapter(List<FeedData> mfeedDataList) {
        final FeedAdapter adapter = new FeedAdapter(mfeedDataList,Feed.this);
        recyclerView.setAdapter(adapter);
    }
}