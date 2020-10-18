package android.example.schooleasy.ui.NoticeBoard;

import android.content.Context;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.DisQuestionReply;
import android.example.schooleasy.dataclass.Notice;
import android.example.schooleasy.dataclass.NoticeDetails;
import android.example.schooleasy.ui.discussionForum.QuestionAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardAdapter.NoticeBoardHolder> {
    List<NoticeDetails> notices;
    Context context;

    public NoticeBoardAdapter(List<NoticeDetails> notices, Context context) {
        this.notices = notices;
        this.context = context;
    }

    @NonNull
    @Override
    public NoticeBoardAdapter.NoticeBoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item,parent,false);
        return new NoticeBoardHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeBoardAdapter.NoticeBoardHolder holder, int position) {
        NoticeDetails notice = notices.get(position);
        holder.userName.setText(notice.getTeacherName());
        holder.noticeContent.setText(notice.getNoticeContent());
        holder.heading.setText(notice.getHeading());

    }

    @Override
    public int getItemCount() {
        return notices.size();
    }
    public class NoticeBoardHolder extends RecyclerView.ViewHolder{
        private TextView heading;
        private TextView noticeContent;
        private TextView userName;

        public NoticeBoardHolder(@NonNull View itemView) {
            super(itemView);
            heading=itemView.findViewById(R.id.notice_heading);
            noticeContent=itemView.findViewById(R.id.noticeSent);
            userName=itemView.findViewById(R.id.authorNotice);
        }
    }
}
