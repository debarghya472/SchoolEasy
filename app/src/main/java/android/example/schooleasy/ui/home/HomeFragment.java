package android.example.schooleasy.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.example.schooleasy.JsonPlaceholderApi;
import android.example.schooleasy.network.RetrofitClientInstance;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.example.schooleasy.R;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private TextView textView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<String> mSubList;
    private JsonPlaceholderApi jsonPlaceholderApi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_student, container, false);

        textView =(TextView) root.findViewById(R.id.standard1);
        recyclerView=root.findViewById(R.id.recycler_view_subject);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();

        mSubList=new ArrayList<String>();

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        Call<String> call = jsonPlaceholderApi.getSubject();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        mSubList.add(0,"History");
        mSubList.add(1,"Geography");
        mSubList.add(2,"Maths");
        mSubList.add(3,"English");

        adapter = new SubjectAdapter(mSubList,getActivity());
        recyclerView.setAdapter(adapter);


        return root;
    }
}