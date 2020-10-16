package android.example.schooleasy.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.example.schooleasy.JsonPlaceholderApi;
import android.example.schooleasy.dataclass.Parent;
import android.example.schooleasy.dataclass.Student;
import android.example.schooleasy.dataclass.Teacher;
import android.example.schooleasy.network.RetrofitClientInstance;
import android.example.schooleasy.ui.LoadDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.example.schooleasy.R;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupFragment extends Fragment {

    private Context context;
    private EditText userEmailId;
    private EditText userPassword;
    private Button signupBtn;
    private JsonPlaceholderApi jsonPlaceholderApi;
    private CheckBox studCheckbox;
    private CheckBox parCheckbox;
    private CheckBox teaCheckbox;
    private EditText fullName;
    private EditText mobileNo;
    private EditText studEmail;
    private EditText subject;
    private EditText age;
    private EditText standard;
    private EditText confirmPassword;
    private LoadDialog loadDialog;
    private  LinearLayout linearLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContext() != null) {
            this.context = getContext();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);
        TextView alreadyUser = (TextView) root.findViewById(R.id.already_user);
        linearLayout =(LinearLayout)root.findViewById(R.id.layout);
        userEmailId = (EditText) root.findViewById(R.id.userEmailId);
        userPassword = (EditText) root.findViewById(R.id.password);
        studEmail =(EditText) root.findViewById(R.id.studEmail);
        confirmPassword = (EditText) root.findViewById(R.id.confirmPassword);
        signupBtn = (Button) root.findViewById(R.id.signUpBtn);
        age =(EditText) root.findViewById(R.id.age);
        studCheckbox = (CheckBox) root.findViewById(R.id.student_checkbox);
        parCheckbox = (CheckBox) root.findViewById(R.id.parent_checkbox);
        teaCheckbox=(CheckBox) root.findViewById(R.id.teacher_checkbox);
        mobileNo = (EditText) root.findViewById(R.id.mobileNumber);

        subject = (EditText) root.findViewById(R.id.subject);

//        address = (EditText) root.findViewById(R.id.address);

        fullName = (EditText) root.findViewById(R.id.fullName);
        standard=(EditText) root.findViewById(R.id.standard);


        loadDialog = new LoadDialog(getActivity());

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        alreadyUser.setOnClickListener(v -> {
            if (getActivity() != null) {
                Log.v("TAG", "success");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_auth_container, new LoginFragment())
                        .commit();
            }
        });

        studCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && parCheckbox.isChecked())
                    parCheckbox.toggle();
                if (isChecked && teaCheckbox.isChecked())
                    teaCheckbox.toggle();
                linearLayout.setVisibility(View.VISIBLE);
                mobileNo.setVisibility(View.GONE);
                standard.setVisibility(View.VISIBLE);
                subject.setVisibility(View.GONE);
                age.setVisibility(View.VISIBLE);
                studEmail.setVisibility(View.GONE);

            }
        });
        parCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && studCheckbox.isChecked())
                    studCheckbox.toggle();
                if (isChecked && teaCheckbox.isChecked())
                    teaCheckbox.toggle();
                linearLayout.setVisibility(View.VISIBLE);
                mobileNo.setVisibility(View.VISIBLE);
                subject.setVisibility(View.GONE);
                standard.setVisibility(View.GONE);
                age.setVisibility(View.GONE);
                studEmail.setVisibility(View.VISIBLE);
            }
        });
        teaCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && studCheckbox.isChecked())
                    studCheckbox.toggle();
                if (isChecked && parCheckbox.isChecked())
                    parCheckbox.toggle();
                linearLayout.setVisibility(View.VISIBLE);
                mobileNo.setVisibility(View.VISIBLE);
                subject.setVisibility(View.VISIBLE);
                age.setVisibility(View.GONE);
                studEmail.setVisibility(View.GONE);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEmailId.getText().toString().isEmpty()) {
                    userEmailId.setError("Email not entered");
                    Log.d("email","email is "+ userEmailId);
                    return;
                }
                if (userPassword.getText().toString().isEmpty()) {
                    userPassword.setError("Password not entered");
                    return;
                }
                if (confirmPassword.getText().toString().isEmpty() || !confirmPassword.getText().toString().equals(userPassword.getText().toString())) {
                    confirmPassword.setError("Password doesn't match");
                    return;
                }
                if (fullName.getText().toString().isEmpty()) {
                    fullName.setError("Name not entered");
                    return;
                }
                if (!studCheckbox.isChecked() && !parCheckbox.isChecked() || studCheckbox.isChecked() && parCheckbox.isChecked()) {
                    Toast.makeText(context, "Select doctor or patient", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (parCheckbox.isChecked()) {
                    loadDialog.startLoad();
                    parentSignUp();
                }
                if (studCheckbox.isChecked()) {
                    loadDialog.startLoad();
                    studentSignup();
                }
                if (teaCheckbox.isChecked()) {
                    loadDialog.startLoad();
                    teacherSignUp();
                }
            }
        });
        return root;
    }
    private void studentSignup(){
        String emailEntered = userEmailId.getText().toString();
        String passwordEntered= userPassword.getText().toString();
        String nameEntered = fullName.getText().toString();
        String standardEntered =standard.getText().toString();
        String ageEntered =age.getText().toString();
        Student student = new Student(emailEntered,passwordEntered,null,nameEntered,ageEntered,standardEntered);
        Call<Student> call = jsonPlaceholderApi.signUpStud(student);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if(!response.isSuccessful()){
                    loadDialog.dismissLoad();
                    Toast.makeText(getContext(),response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                loadDialog.dismissLoad();
                Student student1 = response.body();
                Toast.makeText(getContext(),"Signed in",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_auth_container,new LoginFragment())
                        .commit();


            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void parentSignUp(){
        String emailEntered = userEmailId.getText().toString();
        String passwordEntered= userPassword.getText().toString();
        String nameEntered = fullName.getText().toString();
        String mobileEntered = mobileNo.getText().toString();
        String StudentEmail =studEmail.getText().toString();
        Parent parent =new Parent(emailEntered,passwordEntered,null,nameEntered,mobileEntered,StudentEmail);
        Call<Parent> call =jsonPlaceholderApi.signUpPar(parent);
        call.enqueue(new Callback<Parent>() {
            @Override
            public void onResponse(Call<Parent> call, Response<Parent> response) {
                if(!response.isSuccessful()){
                    loadDialog.dismissLoad();
                    Toast.makeText(getContext(),response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                loadDialog.dismissLoad();
                Parent parent1 =response.body();

                Toast.makeText(getContext(),"Signed in",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_auth_container,new LoginFragment())
                        .commit();

            }

            @Override
            public void onFailure(Call<Parent> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void teacherSignUp(){
        String emailEntered = userEmailId.getText().toString();
        String passwordEntered= userPassword.getText().toString();
        String nameEntered = fullName.getText().toString();
        String mobileEntered = mobileNo.getText().toString();
        String subjectEntered = subject.getText().toString();
        Teacher teacher =new Teacher( emailEntered,passwordEntered,null, nameEntered,mobileEntered,subjectEntered);
        Call<Teacher> call =jsonPlaceholderApi.signUpTeach(teacher);
        call.enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                if(!response.isSuccessful()){
                    loadDialog.dismissLoad();
                    Toast.makeText(getContext(),response.message(),Toast.LENGTH_SHORT).show();
                    return;
                }
                loadDialog.dismissLoad();
                Teacher teacher1 =response.body();

                Toast.makeText(getContext(),"Signed in",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_auth_container,new LoginFragment())
                        .commit();


            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
