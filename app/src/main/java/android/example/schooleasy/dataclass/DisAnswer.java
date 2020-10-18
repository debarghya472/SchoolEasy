package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DisAnswer {

    @SerializedName("answers")
    List<Answer> answerList;

    public List<Answer> getAnswerList() {
        return answerList;
    }

}
