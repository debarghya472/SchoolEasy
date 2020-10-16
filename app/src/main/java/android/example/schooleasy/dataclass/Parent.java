package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class Parent {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("token")
    private String token;
    @SerializedName("name")
    private String name;
    @SerializedName("mobileNo")
    private String mobile;
    @SerializedName("student")
    private  String studEmail;


    public Parent(String email, String password, String token, String name, String mobile,String studEmail) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.name = name;
        this.mobile = mobile;
        this.studEmail=studEmail;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }
    public String getStudEmail(){
        return studEmail;
    }
}