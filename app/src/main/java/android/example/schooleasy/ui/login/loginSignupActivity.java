package android.example.schooleasy.ui.login;

import android.example.schooleasy.R;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class loginSignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_auth_container,new LoginFragment())
                .commit();
    }
}
