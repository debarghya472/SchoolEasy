package android.example.schooleasy.ui.profile;

import android.annotation.SuppressLint;
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
import android.widget.TextView;


public class StudentProfile extends Fragment {


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_student_profile, container, false);
        SharedPreferences info = getContext().getSharedPreferences("info",Context.MODE_PRIVATE);
        String b =info.getString("isStudent",null);
        String name =info.getString("name",null);
        String email =info.getString("email",null);
        String standard =info.getString("standard",null);
        String age =info.getString("age",null);

        TextView role =root.findViewById(R.id.role);
        TextView name1 =root.findViewById(R.id.name);
        TextView age1 =root.findViewById(R.id.age);
        TextView email1 =root.findViewById(R.id.email);
        TextView standard1 =root.findViewById(R.id.standard);

        role.setText("Role:  Student");
        name1.setText("Name:  "+name);
        age1.setText("Age:  "+age);
        email1.setText("Email:  "+email);
        standard1.setText("Standard:  "+standard);

        LinearLayout linearLayout =(LinearLayout)root.findViewById(R.id.logout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = info.edit();
                editor.putString("loggedIn","No").apply();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        return root;
    }
}