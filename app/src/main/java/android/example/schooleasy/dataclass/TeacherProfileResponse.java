package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class TeacherProfileResponse {
    @SerializedName("standard")
    private TeacherList list;

    public TeacherList getTeacherList() {
        return list;
    }
}
