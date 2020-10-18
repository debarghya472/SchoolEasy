package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class DisAnswerList {
    @SerializedName("question")
    private DisAnswer disAnswer;

    public DisAnswer getDisAnswer() {
        return disAnswer;
    }
}
