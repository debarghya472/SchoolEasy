package android.example.schooleasy.ui.timetable;

import android.content.Context;
import android.content.SharedPreferences;
import android.example.schooleasy.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.zip.Inflater;

public class Timetable extends Fragment {

    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timetable,container,false);

        textView = root.findViewById(R.id.time);
        SharedPreferences info = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        String st =info.getString("standard",null);
        textView.setText("Timetable for class "+st);

        return root;
    }
}
