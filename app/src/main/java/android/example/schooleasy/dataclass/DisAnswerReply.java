package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class DisAnswerReply {
    @SerializedName("answer")
    private String answer;

    @SerializedName("user")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public DisAnswerReply(String answer, String userName) {
        this.answer = answer;
        this.userName=userName;
    }

    public String getAnswer() {
        return answer;
    }
}
