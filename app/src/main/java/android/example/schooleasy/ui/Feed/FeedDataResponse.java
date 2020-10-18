package android.example.schooleasy.ui.Feed;

import android.example.schooleasy.dataclass.FeedData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedDataResponse {
    @SerializedName("posts")
    List<FeedData> feedDataList;

    public List<FeedData> getFeedDataList() {
        return feedDataList;
    }
}
