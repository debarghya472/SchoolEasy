package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("age")
    private String age;
    @SerializedName("standard")
    private String standard;

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getName() { return name; }

    public String getAge() { return age; }

    public String getStandard() { return standard; }

    public Student(String email, String password, String name, String age, String standard) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.standard = standard;
    }
}
