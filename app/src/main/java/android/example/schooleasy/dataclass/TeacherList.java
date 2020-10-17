package android.example.schooleasy.dataclass;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherList {
    @SerializedName("teachers")
    private List<Teacher> teacherList;

    public List<Teacher> getTeacherList() {
        return teacherList;
    }
}
