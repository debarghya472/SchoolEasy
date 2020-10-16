package android.example.schooleasy.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.example.schooleasy.JsonPlaceholderApi;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private EditText fullName;
    private EditText mobileNo;
    private EditText field;
    private EditText address;
    private EditText confirmPassword;
    private LoadDialog loadDialog;

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
        userEmailId = (EditText) root.findViewById(R.id.userEmailId);
        userPassword = (EditText) root.findViewById(R.id.password);
        confirmPassword = (EditText) root.findViewById(R.id.confirmPassword);
        signupBtn = (Button) root.findViewById(R.id.signUpBtn);
        studCheckbox = (CheckBox) root.findViewById(R.id.student_checkbox);
        parCheckbox = (CheckBox) root.findViewById(R.id.parent_checkbox);
        mobileNo = (EditText) root.findViewById(R.id.mobileNumber);
        address = (EditText) root.findViewById(R.id.address);
        fullName = (EditText) root.findViewById(R.id.fullName);

        loadDialog = new LoadDialog(getActivity());

        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:4000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

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
                if (mobileNo.getText().toString().isEmpty()) {
                    mobileNo.setError("Mobile Number not entered");
                    return;
                }
                if (address.getText().toString().isEmpty()) {
                    address.setError("Address not entered");
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

                if (studCheckbox.isChecked()) {
                    if (field.getText().toString().isEmpty()) {
                        field.setError("Enter field");
                    } else {
                        loadDialog.startLoad();
                    }
                }
                if (studCheckbox.isChecked()) {
                    loadDialog.startLoad();
                }
            }
        });
        return root;
    }
}
