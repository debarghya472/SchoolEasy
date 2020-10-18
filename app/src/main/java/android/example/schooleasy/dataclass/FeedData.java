package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class FeedData {
    @SerializedName("caption")
    String caption;
    @SerializedName("file")
    String file;

    public FeedData(String caption, String file) {
        this.caption = caption;
        this.file = file;
    }

    public String getCaption() {
        return caption;
    }

    public String getFile() {
        return file;
    }
}
