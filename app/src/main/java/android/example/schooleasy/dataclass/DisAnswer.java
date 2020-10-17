package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class DisAnswer {

    @SerializedName("answer")
    String answer;
    @SerializedName("user")
    String user;

    public String getAnswer() {
        return answer;
    }

    public String getUser() {
        return user;
    }
}
