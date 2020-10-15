package android.example.schooleasy.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.schooleasy.JsonPlaceholderApi;
import android.example.schooleasy.MainActivity;
import android.example.schooleasy.R;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {

    private EditText email;
    private EditText password;
    private CheckBox show_hide_password;
    private JsonPlaceholderApi jsonPlaceholderApi;
    private Button loginBtn;
    private TextView createaccount;
    NavigationView navigationView;
    private CheckBox docCheckbox;
    private CheckBox patCheckbox;
    private LoadDialog loadDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);
        show_hide_password = (CheckBox) root.findViewById(R.id.show_hide_password);
        email = (EditText) root.findViewById(R.id.login_emailid);
        password = (EditText) root.findViewById(R.id.login_password);
        loginBtn = (Button) root.findViewById(R.id.loginBtn);
        navigationView = root.findViewById(R.id.nav_view);
        docCheckbox = (CheckBox) root.findViewById(R.id.doctor_checkbox1);
        patCheckbox = (CheckBox) root.findViewById(R.id.patient_checkbox1);


        loadDialog = new LoadDialog(getActivity());

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://darshil.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

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


        docCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && patCheckbox.isChecked())
                    patCheckbox.toggle();
            }
        });

        patCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && docCheckbox.isChecked())
                    docCheckbox.toggle();
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
                if (docCheckbox.isChecked()) {
                    loadDialog.startLoad();

                } else if (patCheckbox.isChecked()) {
                    loadDialog.startLoad();


                } else {
                    Toast.makeText(getActivity(), "Please select doctor or patient", Toast.LENGTH_SHORT).show();
                    return;
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
}