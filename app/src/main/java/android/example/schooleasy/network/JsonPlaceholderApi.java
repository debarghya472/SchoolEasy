package android.example.schooleasy.network;

import android.example.schooleasy.dataclass.DisAnswerList;
import android.example.schooleasy.dataclass.DisAnswerReply;
import android.example.schooleasy.dataclass.DisQuestionReply;
import android.example.schooleasy.dataclass.DisQuestionsList;
import android.example.schooleasy.dataclass.FeedData;
import android.example.schooleasy.dataclass.Forum;
import android.example.schooleasy.dataclass.LoginResponseStudent;
import android.example.schooleasy.dataclass.LoginResponseTeacher;
import android.example.schooleasy.dataclass.NoticeDetails;
import android.example.schooleasy.dataclass.NoticeList;
import android.example.schooleasy.dataclass.Parent;
import android.example.schooleasy.dataclass.Student;
import android.example.schooleasy.dataclass.StudentProfileResponse;
import android.example.schooleasy.dataclass.Teacher;
import android.example.schooleasy.dataclass.TeacherProfileResponse;
import android.example.schooleasy.ui.Feed.FeedDataResponse;
import android.example.schooleasy.ui.home.StandardResponse;
import android.example.schooleasy.ui.home.SubjectList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.Multipart;

import retrofit2.http.Header;

import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @GET("discussions/{forumId}")
    Call<Forum> getDisQs(@Path("forumId") String forumId);


    @POST("discussions/{forumId}/question/add")
    Call<Void> postQuestion(@Path("forumId") String forumId, @Body DisQuestionReply question,@Header("Authorization" )String header);

    @GET("discussions/{qsId}/answer")
    Call<DisAnswerList> getDisAns(@Path("qsId") String qsId);

    @POST("discussions/{qsId}/answer")
    Call<Void> postAnswer(@Path("qsId") String qsId, @Body DisAnswerReply answer,@Header("Authorization" )String header);

    //get Standard details
    @GET("standards/{standard}")
    Call<StandardResponse> getStandardId(@Path("standard") String standard);


    //study materials
    @Multipart
    @POST("materials/5f8d5ec9556822d9a05d47a9/add")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file,
                                  @Part("text")RequestBody text);

    @POST("notices/{standard}/add")
    Call<Void> addNotice(@Path("standard") String standard, @Body NoticeDetails details,@Header("Authorization" )String header);

    @GET("notices/{standard}/getAll")
    Call<NoticeList> getNotices(@Path("standard") String standard);

    //feed
    @GET("posts/5f8c61a8db99b1224469e6b9/get")
    Call<FeedDataResponse> getFeed();
    @POST("posts/5f8c61a8db99b1224469e6b9/add")
    Call<FeedData> postFeed(@Body FeedData st,@Header("Authorization" )String header);

}
