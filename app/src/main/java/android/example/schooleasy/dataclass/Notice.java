package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

public class Notice {
    @SerializedName("notice")
    private NoticeDetails noticeDetails;

    public NoticeDetails getNoticeDetails() {
        return noticeDetails;
    }
}
