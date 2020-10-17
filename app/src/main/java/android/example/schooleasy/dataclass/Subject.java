package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class Subject {
    @SerializedName("_id")
    String Subid;
    @SerializedName("name")
    String subname;
//    @SerializedName("standard")
//    String standard;
    @SerializedName("teacher")
    String teacher;


    public Subject(String subname,  String teacher,String subid) {
        this.subname = subname;
//        this.standard = standard;
        this.teacher = teacher;
        this.Subid = subid;
//        this.materials = materials;
    }

    public String getSubname() {
        return subname;
    }

    public String getSubid() {
        return Subid;
    }

    //    public String getStandard() {
//        return standard;
//    }

    public String getTeacher() {
        return teacher;
    }
//
//    public String getMaterials() {
//        return materials;
//    }
}
