package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class DisQuestion {
    @SerializedName("question")
    String question;
    @SerializedName("user")
    String user;

    public String getQuestion() {
        return question;
    }

    public String getUser() {
        return user;
    }
}
