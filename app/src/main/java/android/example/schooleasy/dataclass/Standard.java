package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class Standard {
    @SerializedName("_id")
    String standardId;

    @SerializedName("discussionForum")
    String discId;

    @SerializedName("subject")
    String subject;

    public String getStandardId() {
        return standardId;
    }

    public String getDiscId() {
        return discId;
    }

    public String getSubject() {
        return subject;
    }
}

