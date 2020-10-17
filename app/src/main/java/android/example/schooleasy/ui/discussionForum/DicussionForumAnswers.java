package android.example.schooleasy.ui.discussionForum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.schooleasy.MainActivity;
import android.example.schooleasy.R;
import android.example.schooleasy.ui.Feed.Feed;
import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class DicussionForumAnswers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicussion_forum_answers);
        ChipNavigationBar bottomNav= findViewById(R.id.bottom_nav);

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
    }
}