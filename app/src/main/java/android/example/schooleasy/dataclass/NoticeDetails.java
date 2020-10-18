package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class NoticeDetails {
    @SerializedName("text")
    private String noticeContent;

    @SerializedName("heading")
    private String heading;

    @SerializedName("teacher")
    private String teacherName;

    public NoticeDetails(String noticeContent, String heading, String teacherName) {
        this.noticeContent = noticeContent;
        this.heading = heading;
        this.teacherName = teacherName;
    }

    public String getHeading() {
        return heading;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getNoticeContent() {
        return noticeContent;
    }
}
