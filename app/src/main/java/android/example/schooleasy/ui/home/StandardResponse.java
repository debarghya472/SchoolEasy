package android.example.schooleasy.ui.home;

import android.example.schooleasy.dataclass.Standard;

import com.google.gson.annotations.SerializedName;

public class StandardResponse {
    @SerializedName("standard")
    Standard standard;

    public Standard getStandard() {
        return standard;
    }
}
