package android.example.schooleasy.ui.discussionForum;

import android.content.Context;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.DisAnswer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerHolder> {


    List<DisAnswer> disAnswers;
    Context context;

    public AnswerAdapter(List<DisAnswer> disAnswers, Context context) {
        this.disAnswers = disAnswers;
        this.context = context;
    }

    @NonNull
    @Override
    public AnswerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item,parent,false);
        return new AnswerAdapter.AnswerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerHolder holder, int position) {
        DisAnswer disAnswer =disAnswers.get(position);
        holder.answer.setText(disAnswer.getAnswer());
        holder.user.setText(disAnswer.getUser());
    }

    @Override
    public int getItemCount() {
        return disAnswers.size();
    }

    public class AnswerHolder extends RecyclerView.ViewHolder {

        private TextView answer;
        private TextView user;

        public AnswerHolder(@NonNull View itemView) {
            super(itemView);

            answer=itemView.findViewById(R.id.answer);
            user = itemView.findViewById(R.id.author1);
        }
    }
}
