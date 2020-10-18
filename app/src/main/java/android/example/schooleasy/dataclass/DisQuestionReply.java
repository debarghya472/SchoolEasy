package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class DisQuestionReply {
    @SerializedName("name")
    private String name;

    @SerializedName("question")
    private String qs;

    @SerializedName("_id")
    private String qsId;

    public String getName() {
        return name;
    }

    public String getQs() {
        return qs;
    }

    public String getQsId() {
        return qsId;
    }

    public DisQuestionReply(String name, String qs, String qsId) {
        this.name = name;
        this.qs = qs;
        this.qsId=qsId;
    }

}
