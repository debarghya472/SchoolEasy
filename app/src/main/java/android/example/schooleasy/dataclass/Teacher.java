package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class Teacher {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("subject")
    private String subject;
    @SerializedName("standard")
    private String standard;

    public Teacher(String email, String password, String name, String subject, String standard) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.subject = subject;
        this.standard=standard;
    }

    public String getStandard() {
        return standard;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }
}
