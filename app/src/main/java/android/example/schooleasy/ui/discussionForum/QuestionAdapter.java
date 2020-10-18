package android.example.schooleasy.ui.discussionForum;

import android.content.Context;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.DisQuestion;
import android.example.schooleasy.dataclass.DisQuestionReply;
import android.example.schooleasy.ui.otherStudents.OtherStudentsAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QusetionHolder> {

    List<DisQuestionReply> disQusetions;
    Context context;
    private OnItemClickListener listener;

    public QuestionAdapter(List<DisQuestionReply> disQusetions, Context context) {
        this.disQusetions = disQusetions;
        this.context = context;
    }

    @NonNull
    @Override
    public QusetionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item,parent,false);
        return new QusetionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QusetionHolder holder, int position) {
        DisQuestionReply disQuestion = disQusetions.get(position);
        holder.question.setText(disQuestion.getQs());
        holder.user.setText(disQuestion.getName());
    }

    @Override
    public int getItemCount() {
        return disQusetions.size();
    }

    public class QusetionHolder extends RecyclerView.ViewHolder{

        private TextView question;
        private TextView user;
        public QusetionHolder(@NonNull View itemView) {
            super(itemView);

            question=itemView.findViewById(R.id.question);
            user = itemView.findViewById(R.id.author);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(disQusetions.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DisQuestionReply reply);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
