package android.example.schooleasy.ui.home;

import android.example.schooleasy.dataclass.Subject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectResponse {
    @SerializedName("subjects")
    List<Subject> subjectList;


    public List<Subject> getSubjectList() {
        return subjectList;
    }
}
