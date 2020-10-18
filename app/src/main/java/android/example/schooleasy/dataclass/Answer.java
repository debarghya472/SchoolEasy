package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class Answer {
    @SerializedName("answer")
    private String answer;

    @SerializedName("user")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public String getAnswer() {
        return answer;
    }

}
