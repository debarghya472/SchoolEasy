package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class Standard {
    @SerializedName("_id")
    String standardId;

    @SerializedName("subject")
    String subject;

    public String getStandardId() {
        return standardId;
    }

    public String getSubject() {
        return subject;
    }
}

