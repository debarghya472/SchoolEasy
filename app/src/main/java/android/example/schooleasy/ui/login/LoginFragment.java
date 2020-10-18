package android.example.schooleasy.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.schooleasy.dataclass.LoginResponseStudent;
import android.example.schooleasy.dataclass.LoginResponseTeacher;
import android.example.schooleasy.dataclass.TeacherProfileResponse;
import android.example.schooleasy.network.JsonPlaceholderApi;
import android.example.schooleasy.MainActivity;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.Parent;
import android.example.schooleasy.dataclass.Student;
import android.example.schooleasy.dataclass.Teacher;
import android.example.schooleasy.network.RetrofitClientInstance;
import android.example.schooleasy.ui.LoadDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {

    private EditText email;
    private EditText password;
    private CheckBox show_hide_password;
    private JsonPlaceholderApi jsonPlaceholderApi;
    private Button loginBtn;
    private TextView createaccount;
    NavigationView navigationView;
    private LoadDialog loadDialog;
    private CheckBox studCheckbox;
    private CheckBox parCheckbox;
    private CheckBox teaCheckbox;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);
        show_hide_password = (CheckBox) root.findViewById(R.id.show_hide_password);
        email = (EditText) root.findViewById(R.id.login_emailid);
        password = (EditText) root.findViewById(R.id.login_password);
        loginBtn = (Button) root.findViewById(R.id.loginBtn);
        navigationView = root.findViewById(R.id.nav_view);
        studCheckbox =root.findViewById(R.id.student_checkbox1);
        parCheckbox =root.findViewById(R.id.parent_checkbox1);
        teaCheckbox =root.findViewById(R.id.teacher_checkbox1);



        loadDialog = new LoadDialog(getActivity());

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);


        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {
                        // If it is checked then show password else hide
                        // password
                        if (isChecked) {
                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text
                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text
                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password
                        }
                    }
                });
        studCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && parCheckbox.isChecked())
                    parCheckbox.toggle();
                if (isChecked && teaCheckbox.isChecked())
                    teaCheckbox.toggle();
            }
        });
        parCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && studCheckbox.isChecked())
                    studCheckbox.toggle();
                if (isChecked && teaCheckbox.isChecked())
                    teaCheckbox.toggle();
            }
        });
        teaCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && studCheckbox.isChecked())
                    studCheckbox.toggle();
                if (isChecked && parCheckbox.isChecked())
                    parCheckbox.toggle();
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()) {
                    email.setError("Email not entered");
                    return;
                }
                if (password.getText().toString().isEmpty()) {
                    password.setError("Password not entered");
                    return;
                }
                if(studCheckbox.isChecked()){
                    loadDialog.startLoad();
                    loginStudent();
                }
                if(parCheckbox.isChecked()){
                    loadDialog.startLoad();
                    loginParent();
                }
                if (teaCheckbox.isChecked()){
                    loadDialog.startLoad();
                    loginTeacher();
                }
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        createaccount = (TextView) view.findViewById(R.id.createAccount);
        createaccount.setOnClickListener(view1 -> {
            Fragment fragment = new SignupFragment();
            if (getActivity() != null) {
                Log.v("TAG", "Success");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_auth_container, new SignupFragment())
                        .commit();
            }
        });
    }
    private void loginStudent(){
        String emailEntered = email.getText().toString();
        String passwordEntered = password.getText().toString();
        Student  student =new Student(emailEntered,passwordEntered,null,null,null);
        Call<LoginResponseStudent> call =jsonPlaceholderApi.loginStudent(student);
        call.enqueue(new Callback<LoginResponseStudent>() {
            @Override
            public void onResponse(Call<LoginResponseStudent> call, Response<LoginResponseStudent> response) {
                if(!response.isSuccessful())  {
                    Toast.makeText(getContext(),"User not logged in",Toast.LENGTH_SHORT).show();
                    loadDialog.dismissLoad();
                    return;
                }
                loadDialog.dismissLoad();
                assert response.body() != null;
                String token = response.body().getToken();
                Log.d("token", token);
                Student student1 = response.body().getUser();
                Toast.makeText(getContext(),"Logged in",Toast.LENGTH_SHORT).show();
                SharedPreferences info = getContext().getSharedPreferences("info",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = info.edit();
                editor.putString("loggedIn","Yes");
                editor.putString("IsStudent","Yes");
                editor.putString("IsParent","No");
                editor.putString("IsTeacher","No");
                editor.putString("token",token);
                editor.putString("standard",student1.getStandard());
                editor.putString("name",student1.getName());
                editor.putString("age",student1.getAge());
                editor.putString("email",student1.getEmail());
                editor.apply();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<LoginResponseStudent> call, Throwable t) {
                loadDialog.dismissLoad();
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void loginParent(){
        String emailEntered = email.getText().toString();
        String passwordEntered = password.getText().toString();
        Parent parent =new Parent(emailEntered,passwordEntered,null,null,null,null);
        Call<Parent> call =jsonPlaceholderApi.loginParent(parent);
        call.enqueue(new Callback<Parent>() {
            @Override
            public void onResponse(Call<Parent> call, Response<Parent> response) {
                if(!response.isSuccessful())  {
                    Toast.makeText(getContext(),"User not logged in",Toast.LENGTH_SHORT).show();
                    loadDialog.dismissLoad();
                    return;
                }
                loadDialog.dismissLoad();
                Parent parent1 = response.body();
                Toast.makeText(getContext(),"Logged in",Toast.LENGTH_SHORT).show();
                SharedPreferences info = getContext().getSharedPreferences("info",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = info.edit();
                editor.putString("loggedIn","Yes");
                editor.putString("IsParent","Yes");
                editor.putString("IsStudent","No");
                editor.putString("IsTeacher","No");
                editor.putString("token",parent1.getToken());
                editor.apply();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<Parent> call, Throwable t) {

                loadDialog.dismissLoad();
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void loginTeacher(){
        String emailEntered = email.getText().toString();
        String passwordEntered = password.getText().toString();
        Teacher teacher =new Teacher(emailEntered,passwordEntered,null,null,null,null);
        Call<LoginResponseTeacher> call = jsonPlaceholderApi.loginTeacher(teacher);
        call.enqueue(new Callback<LoginResponseTeacher>() {
            @Override
            public void onResponse(Call<LoginResponseTeacher> call, Response<LoginResponseTeacher> response) {
                if(!response.isSuccessful())  {
                    Toast.makeText(getContext(),"User not logged in",Toast.LENGTH_SHORT).show();
                    loadDialog.dismissLoad();
                    return;
                }
                loadDialog.dismissLoad();
                String token = response.body().getToken();
                Log.d("token", token);
                Teacher teacher1 = response.body().getUser();
                Toast.makeText(getContext(),"Logged in",Toast.LENGTH_SHORT).show();
                SharedPreferences info = getContext().getSharedPreferences("info",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = info.edit();
                editor.putString("loggedIn","Yes");
                editor.putString("IsParent","No");
                editor.putString("IsStudent","No");
                editor.putString("IsTeacher","Yes");
                editor.putString("token",token);
                editor.putString("subject",teacher1.getSubject());
                editor.putString("standard",teacher1.getStandard());
                editor.apply();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<LoginResponseTeacher> call, Throwable t) {
                loadDialog.dismissLoad();
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
}