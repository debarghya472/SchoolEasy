package android.example.schooleasy.ui.Feed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.schooleasy.MainActivity;
import android.example.schooleasy.R;

import android.example.schooleasy.dataclass.FeedData;
import android.example.schooleasy.dataclass.Teacher;
import android.example.schooleasy.ui.SubjectInfo.SubjectInfoActivity;
import android.example.schooleasy.ui.login.loginSignupActivity;

import android.example.schooleasy.ui.discussionForum.DiscussionForumQuestions;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {

    private List<FeedData> mfeedDataList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getWindow().setBackgroundDrawableResource(R.color.background_color);
        setTitle("Feed");
        ChipNavigationBar bottomNav= findViewById(R.id.bottom_nav);

        bottomNav.setItemSelected(R.id.Feed,true);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.click);
        LinearLayout linearLayout1 =(LinearLayout )findViewById(R.id.postAns);

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
}