package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class Forum {
    @SerializedName("forum")
    private DisQuestionsList forum;

    public DisQuestionsList getForum() {
        return forum;
    }
}
