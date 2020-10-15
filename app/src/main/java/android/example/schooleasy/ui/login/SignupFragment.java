package android.example.schooleasy.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.concurrent.TimeUnit;

public class SignupFragment extends Fragment {

    private Context context;
    private EditText userEmailId;
    private EditText userPassword;
    private Button signupBtn;
    private JsonPlaceholderApi jsonPlaceholderApi;
    private CheckBox docCheckbox;
    private CheckBox patCheckbox;
    private EditText fullName;
    private EditText mobileNo;
    private EditText field;
    private EditText address;
    private EditText confirmPassword;
    private LoadDialog loadDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getContext()!=null)
        {
            this.context = getContext();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_signup,container,false);
        TextView alreadyUser =(TextView)root.findViewById(R.id.already_user);
        userEmailId = (EditText) root.findViewById(R.id.userEmailId);
        userPassword= (EditText) root.findViewById(R.id.password);
        confirmPassword = (EditText) root.findViewById(R.id.confirmPassword);
        signupBtn = (Button) root.findViewById(R.id.signUpBtn);
        docCheckbox=(CheckBox) root.findViewById(R.id.doctor_checkbox);
        patCheckbox=(CheckBox) root.findViewById(R.id.patient_checkbox);
        field=(EditText) root.findViewById(R.id.field);
        mobileNo =(EditText) root.findViewById(R.id.mobileNumber);
        address = (EditText) root.findViewById(R.id.address);
        fullName = (EditText) root.findViewById(R.id.fullName);

        loadDialog =new LoadDialog(getActivity());

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

        alreadyUser.setOnClickListener(v -> {
            if(getActivity()!=null) {
                Log.v("TAG","success");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_auth_container, new loginFragment())
                        .commit();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userEmailId.getText().toString().isEmpty()){
                    userEmailId.setError("Email not entered");
                    return;
                }
                if(userPassword.getText().toString().isEmpty()){
                    userPassword.setError("Password not entered");
                    return;
                }
                if(mobileNo.getText().toString().isEmpty()){
                    mobileNo.setError("Mobile Number not entered");
                    return;
                }
                if(address.getText().toString().isEmpty()){
                    address.setError("Address not entered");
                    return;
                }
                if(confirmPassword.getText().toString().isEmpty() || !confirmPassword.getText().toString().equals(userPassword.getText().toString())){
                    confirmPassword.setError("Password doesn't match");
                    return;
                }
                if(fullName.getText().toString().isEmpty()){
                    fullName.setError("Name not entered");
                    return;
                }
                if(!docCheckbox.isChecked() && !patCheckbox.isChecked() || docCheckbox.isChecked() && patCheckbox.isChecked()){
                    Toast.makeText(context,"Select doctor or patient",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(docCheckbox.isChecked()){
                    if(field.getText().toString().isEmpty()){
                        field.setError("Enter field");
                    }
                    else {
                        loadDialog.startLoad();
                        signupDoctor();
                    }
                }
                if(patCheckbox.isChecked()){
                    loadDialog.startLoad();
                    signupPatient();
                }
            }
        });
        return root;
    }
    private void signupDoctor(){
        String emailEntered = userEmailId.getText().toString();
        String passwordEntered= userPassword.getText().toString();
        String phoneEntered = mobileNo.getText().toString();
        String nameEntered = fullName.getText().toString();
        String fieldEntered = field.getText().toString();
        String addressEntered = address.getText().toString();
        Post post = new Post(emailEntered,passwordEntered,null,nameEntered,phoneEntered,addressEntered,fieldEntered);
        Call<Post> call = jsonPlaceholderApi.signupDoctor(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"User not signed in",Toast.LENGTH_SHORT).show();
                    return;
                }
                loadDialog.dismissLoad();
                Post postResponse = response.body();
                SharedPreferences info = getContext().getSharedPreferences("info",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = info.edit();
                editor.putString("email",postResponse.getEmail());
                editor.putString("name",postResponse.getName());
                editor.putString("phone",postResponse.getPhone());
                editor.putString("field",postResponse.getField());
                editor.putString("address",postResponse.getAddress());
                editor.apply();

                Toast.makeText(getContext(),"Signed in",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_auth_container,new loginFragment())
                        .commit();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void signupPatient(){
        String emailEntered = userEmailId.getText().toString();
        String passwordEntered= userPassword.getText().toString();
        String phoneEntered = mobileNo.getText().toString();
        String nameEntered = fullName.getText().toString();
        Post post = new Post(emailEntered,passwordEntered,null,nameEntered,phoneEntered,null,null);
        Call<Post> call = jsonPlaceholderApi.signupPatient(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"User not signed in",Toast.LENGTH_SHORT).show();
                    return;
                }
                loadDialog.dismissLoad();
                Post postResponse = response.body();
                SharedPreferences info = getContext().getSharedPreferences("info",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = info.edit();
                editor.putString("email",postResponse.getEmail());
                editor.putString("name",postResponse.getName());
                editor.putString("phone",postResponse.getPhone());
                editor.apply();

                Toast.makeText(getContext(),"Signed in",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_auth_container,new loginFragment())
                        .commit();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}