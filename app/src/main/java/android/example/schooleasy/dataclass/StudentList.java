package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentList {
    @SerializedName("students")
    private List<Student> studentList;

    public List<Student> getStudentList() {
        return studentList;
    }
}
