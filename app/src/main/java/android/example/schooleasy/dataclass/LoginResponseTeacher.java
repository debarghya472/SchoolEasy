package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class LoginResponseTeacher {
    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private Teacher user;

    public Teacher getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
