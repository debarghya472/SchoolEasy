package android.example.schooleasy.dataclass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoticeList {
    @SerializedName("notices")
    private List<NoticeDetails> noticeDetailsList;

    public List<NoticeDetails> getNoticeDetailsList() {
        return noticeDetailsList;
    }
}
