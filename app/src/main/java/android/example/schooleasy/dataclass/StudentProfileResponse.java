package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class StudentProfileResponse {
    @SerializedName("standard")
    private StudentList list;

    public StudentList getStudentList() {
        return list;
    }
}
