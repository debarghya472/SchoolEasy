package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class Subject {
    @SerializedName("name")
    String subname;
    @SerializedName("standard")
    String standard;
    @SerializedName("teacher")
    String teacher;
    @SerializedName("material")
    String materials;

    public Subject(String subname, String standard, String teacher, String materials) {
        this.subname = subname;
        this.standard = standard;
        this.teacher = teacher;
        this.materials = materials;
    }

    public String getSubname() {
        return subname;
    }

    public String getStandard() {
        return standard;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getMaterials() {
        return materials;
    }
}
