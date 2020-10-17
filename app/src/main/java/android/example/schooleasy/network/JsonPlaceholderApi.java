package android.example.schooleasy.network;

import android.example.schooleasy.dataclass.DisQuestion;
import android.example.schooleasy.dataclass.DisQuestionsList;
import android.example.schooleasy.dataclass.LoginResponseStudent;
import android.example.schooleasy.dataclass.LoginResponseTeacher;
import android.example.schooleasy.dataclass.Parent;
import android.example.schooleasy.dataclass.Standard;
import android.example.schooleasy.dataclass.Student;
import android.example.schooleasy.dataclass.StudentList;
import android.example.schooleasy.dataclass.StudentProfileResponse;
import android.example.schooleasy.dataclass.Subject;
import android.example.schooleasy.dataclass.Teacher;
import android.example.schooleasy.dataclass.TeacherList;
import android.example.schooleasy.dataclass.TeacherProfileResponse;
import android.example.schooleasy.ui.home.StandardResponse;
import android.example.schooleasy.ui.home.SubjectList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceholderApi {

    //login
    @POST("students/login")
    Call<LoginResponseStudent> loginStudent(@Body Student post);

    @POST("parents/login")
    Call<Parent> loginParent(@Body Parent post);

    @POST("teachers/login")
    Call<LoginResponseTeacher> loginTeacher(@Body Teacher post);
    //signUp

    @POST("students/signup")
    Call<Student> signUpStud(@Body Student post);

    @POST("parents/signup")
    Call<Parent> signUpPar(@Body Parent post);

    @POST("teachers/signup")
    Call<Teacher> signUpTeach(@Body Teacher post);

    @GET("standards/{standard}/students")
    Call<StudentProfileResponse> showStudentsProfile(@Path("standard") String standard);

    @GET("standards/{standard}/teachers")
    Call<TeacherProfileResponse> showTeachersProfile(@Path("standard") String standard);


    //get subject
    @GET("subjects/{standardId}/all")
    Call<SubjectList> getSubject(@Path("standardId") String standardId);

    @GET("standards/{standardid}/students")
    Call<Student> showStudentProfile();

    //get discussion forum qs
    @GET("discussions/{standardId}")
    Call<DisQuestionsList> getDisQs(@Path("standardId") String standardId);



    //get Standard details
    @GET("standards/{standard}")
    Call<StandardResponse> getStandardId(@Path("standard") String standard);
}
