package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DisQuestionsList {
    @SerializedName("questions")
    private List<DisQuestion> questionList;

    public List<DisQuestion> getQuestionList() {
        return questionList;
    }
}
