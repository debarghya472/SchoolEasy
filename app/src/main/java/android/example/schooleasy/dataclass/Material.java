package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class Material {
    @SerializedName("text")
    private String text;

    @SerializedName("file")
    private String filePath;

    public Material(String text, String filePath) {
        this.text = text;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getText() {
        return text;
    }
}
