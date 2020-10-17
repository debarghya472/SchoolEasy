package android.example.schooleasy.ui.teacher;

import android.content.Context;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.Teacher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeachersAdapter extends RecyclerView.Adapter<TeachersAdapter.TeachersViewHolder> {
    private List<Teacher> teachersList;
    private Context context;

    public TeachersAdapter(List<Teacher> teachersList, Context context) {
        this.context=context;
        this.teachersList=teachersList;
    }

    @NonNull
    @Override
    public TeachersAdapter.TeachersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_teacher_item,parent,false);
        return new TeachersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TeachersAdapter.TeachersViewHolder holder, int position) {
        Teacher teacher=teachersList.get(position);
        //holder.teach_number.setText(teacher.);
        holder.teach_name.setText(teacher.getName());
        holder.teach_email.setText(teacher.getEmail());
        //holder.teach_age.setText(teacher.);


    }

    @Override
    public int getItemCount() {
        return teachersList.size();
    }

    public class TeachersViewHolder extends RecyclerView.ViewHolder{
        private TextView teach_name;
        private TextView teach_age;
        private TextView teach_email;
        private TextView teach_number;

        public TeachersViewHolder(@NonNull View itemView) {
            super(itemView);
            teach_age=itemView.findViewById(R.id.teacher_age);
            teach_email=itemView.findViewById(R.id.teacher_email);
            teach_name=itemView.findViewById(R.id.teacher_name);
            teach_number=itemView.findViewById(R.id.teacher_number);
        }
    }
}
