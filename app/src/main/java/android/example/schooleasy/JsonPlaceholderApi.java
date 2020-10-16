package android.example.schooleasy;

import android.example.schooleasy.dataclass.Student;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceholderApi {

    @POST("students/login")
    Call<Student> loginUser(@Body Student post);
}
