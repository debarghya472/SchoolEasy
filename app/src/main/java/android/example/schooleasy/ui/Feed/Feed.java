package android.example.schooleasy.ui.Feed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.schooleasy.MainActivity;
import android.example.schooleasy.R;
import android.example.schooleasy.ui.SubjectInfo.SubjectInfoActivity;
import android.example.schooleasy.ui.discussionForum.DiscussionForum;
import android.example.schooleasy.ui.login.loginSignupActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Feed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        setTitle("Feed");
        ChipNavigationBar bottomNav= findViewById(R.id.bottom_nav);

        bottomNav.setItemSelected(R.id.Feed,true);

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
                        startActivity(new Intent(getApplicationContext(), DiscussionForum.class));
                        break;
                }
            }
        });

    }
}