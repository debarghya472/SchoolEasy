package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class DisQuestionReply {
    @SerializedName("name")
    private String name;

    @SerializedName("question")
    private String qs;

    public String getName() {
        return name;
    }

    public String getQs() {
        return qs;
    }

    public DisQuestionReply(String name, String qs) {
        this.name = name;
        this.qs = qs;
    }

}
