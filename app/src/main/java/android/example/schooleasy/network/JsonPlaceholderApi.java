package android.example.schooleasy;

import android.example.schooleasy.dataclass.Parent;
import android.example.schooleasy.dataclass.Student;
import android.example.schooleasy.dataclass.StudentList;
import android.example.schooleasy.dataclass.Teacher;
import android.example.schooleasy.dataclass.TeacherList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceholderApi {

    //login
    @POST("students/login")
    Call<Student> loginStudent(@Body Student post);

    @POST("parents/login")
    Call<Parent> loginParent(@Body Parent post);

    @POST("teachers/login")
    Call<Teacher> loginTeacher(@Body Teacher post);
    //signUp

    @POST("students/signup")
    Call<Student> signUpStud(@Body Student post);

    @POST("parents/signup")
    Call<Parent> signUpPar(@Body Parent post);

    @POST("teachers/signup")
    Call<Teacher> signUpTeach(@Body Teacher post);

    @GET("standards/{standard}/students")
    Call<StudentList> showStudentsProfile(@Path("standard") String standard);

    @GET("standards/{standard}/teachers")
    Call<TeacherList> showTeachersProfile(@Path("standard") String standard);
}
