package android.example.schooleasy.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.schooleasy.MainActivity;
import android.example.schooleasy.ui.login.loginSignupActivity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.example.schooleasy.R;
import android.widget.LinearLayout;


public class StudentProfile extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_student_profile, container, false);

        LinearLayout linearLayout =(LinearLayout)root.findViewById(R.id.logout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences info = getContext().getSharedPreferences("info",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = info.edit();
                editor.putString("loggedIn","No").apply();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        return root;
    }
}