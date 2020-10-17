package android.example.schooleasy.ui.discussionForum;

import android.content.Context;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.DisQuestion;
import android.example.schooleasy.ui.otherStudents.OtherStudentsAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QusetionHolder> {

    List<DisQuestion> disQusetions;
    Context context;

    public QuestionAdapter(List<DisQuestion> disQusetions, Context context) {
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
        DisQuestion disQuestion = disQusetions.get(position);
        holder.question.setText(disQuestion.getQuestion());
        holder.user.setText(disQuestion.getUser());
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
        }
    }
}
