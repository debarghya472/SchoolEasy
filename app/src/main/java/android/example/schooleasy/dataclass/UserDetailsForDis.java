package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class UserDetailsForDis {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
}
