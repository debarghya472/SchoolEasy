package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class DisQuestion {
    @SerializedName("question")
    String question;
    @SerializedName("user")
    UserDetailsForDis user;

    public DisQuestion(String question, UserDetailsForDis user) {
        this.question = question;
        this.user = user;
    }

    public String getQuestion() {
        return question;
    }

    public UserDetailsForDis getUser() {
        return user;
    }
}
