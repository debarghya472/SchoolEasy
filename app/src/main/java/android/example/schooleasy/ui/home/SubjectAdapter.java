package android.example.schooleasy.ui.home;

import android.content.Context;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.Subject;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.PresHolder> {

    private List<Subject> listitem;
    private Context context;
    private OnItemClickListener listener;

    public SubjectAdapter(List<Subject> listitem, Context context) {
        this.listitem = listitem;
        this.context = context;
    }

    @NonNull
    @Override
    public PresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_list, parent, false);
        return new PresHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PresHolder holder, int position) {
        Subject sub = listitem.get(position);

        holder.subname.setText(sub.getSubname());

    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class PresHolder extends RecyclerView.ViewHolder {

        public TextView subname;

        public PresHolder(@NonNull View itemView) {
            super(itemView);
            subname = (TextView) itemView.findViewById(R.id.subname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(listitem.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Subject subject);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}