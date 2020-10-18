package android.example.schooleasy.ui.otherStudents;

import android.content.Context;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.Student;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OtherStudentsAdapter extends RecyclerView.Adapter<OtherStudentsAdapter.OtherStudentsHolder> {
    private List<Student> studentList;
    private Context context;


    public OtherStudentsAdapter(List<Student> studentList,Context context) {
        this.context=context;
        this.studentList=studentList;
    }

    @NonNull
    @Override
    public OtherStudentsAdapter.OtherStudentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_students_item,parent,false);
        return new OtherStudentsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherStudentsAdapter.OtherStudentsHolder holder, int position) {
        Student student = studentList.get(position);
        //holder.stud_number.setText(student.);
        holder.stud_name.setText(student.getName());
        holder.stud_email.setText(student.getEmail());
        holder.stud_number.setText(student.getStandard());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
    public class OtherStudentsHolder extends RecyclerView.ViewHolder{
        private TextView stud_name;
        private TextView stud_email;
        private TextView stud_number;

        public OtherStudentsHolder(@NonNull View itemView) {
            super(itemView);
            stud_email=itemView.findViewById(R.id.student_email);
            stud_name=itemView.findViewById(R.id.student_name);
            stud_number=itemView.findViewById(R.id.student_standard);
        }
    }
}
