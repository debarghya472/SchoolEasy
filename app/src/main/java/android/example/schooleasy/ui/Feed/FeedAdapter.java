package android.example.schooleasy.ui.Feed;

import android.content.Context;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.FeedData;
import android.example.schooleasy.ui.teacher.TeachersAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {
    private List<FeedData> feedlist;
    private Context context;

    public FeedAdapter(List<FeedData> feedlist, Context context) {
        this.feedlist = feedlist;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_feed_item,parent,false);
        return new FeedAdapter.FeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        FeedData feed  =feedlist.get(position);
        holder.cap.setText(feed.getCaption());

    }

    @Override
    public int getItemCount() {
        return feedlist.size();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        public TextView cap;
        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            cap =itemView.findViewById(R.id.caption);
        }
    }
}
