package android.example.schooleasy.ui.teacher;

import android.example.schooleasy.JsonPlaceholderApi;
import android.example.schooleasy.R;
import android.example.schooleasy.network.RetrofitClientInstance;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Retrofit;

public class TeacherProfiles extends Fragment {
    JsonPlaceholderApi jsonPlaceholderApi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_teacher,container,false);

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        return root;
    }
}
