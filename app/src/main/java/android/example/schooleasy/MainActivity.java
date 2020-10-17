package android.example.schooleasy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.schooleasy.ui.Feed.Feed;
import android.example.schooleasy.ui.discussionForum.DiscussionForum;
import android.example.schooleasy.ui.eventsCalendar.EventsActivity;
import android.example.schooleasy.ui.home.HomeFragment;
import android.example.schooleasy.ui.login.loginSignupActivity;
import android.example.schooleasy.ui.otherStudents.OtherStudents;
import android.os.Bundle;
import android.util.AttributeSet;

import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences info = getApplicationContext().getSharedPreferences("info", Context.MODE_PRIVATE);
        String isLogged = info.getString("loggedIn","No");
        if(isLogged.equals("No")){
            Toast.makeText(this,"Please Log In",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,loginSignupActivity.class));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_timetable, R.id.nav_otherStudent,R.id.nav_discussion,R.id.nav_teacher)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ChipNavigationBar bottomNav = findViewById(R.id.bottom_nav);

        bottomNav.setItemSelected(R.id.Home , true);
        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.Home:
                        break;
                    case R.id.Feed:
                        startActivity(new Intent(getApplicationContext(), Feed.class));
                        break;
                    case R.id.discussion:
                        startActivity(new Intent(getApplicationContext(),DiscussionForum.class));
                        break;
                }
            }
        });

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        SharedPreferences info = getApplicationContext().getSharedPreferences("info", Context.MODE_PRIVATE);
        String isLogged = info.getString("loggedIn","No");
        if(isLogged=="No"){
            this.finish();
        }
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_event:
                Intent intent = new Intent(this, EventsActivity.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}