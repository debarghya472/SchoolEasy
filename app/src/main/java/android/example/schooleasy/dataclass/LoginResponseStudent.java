package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class LoginResponseStudent {
    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private Student user;

    public Student getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
