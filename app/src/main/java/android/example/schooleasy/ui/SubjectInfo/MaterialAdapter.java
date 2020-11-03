package android.example.schooleasy.ui.SubjectInfo;

import android.content.Context;
import android.example.schooleasy.R;
import android.example.schooleasy.dataclass.DisQuestionReply;
import android.example.schooleasy.dataclass.Material;
import android.example.schooleasy.dataclass.Student;
import android.example.schooleasy.ui.discussionForum.QuestionAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MaterialHolder> {
    private List<Material> materialList;
    private Context context;
    private OnItemClickListener listener;

    public MaterialAdapter(List<Material> materialList, Context context) {
        this.materialList = materialList;
        this.context = context;
    }

    @NonNull
    @Override
    public MaterialAdapter.MaterialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.get_materials_item,parent,false);
        return new MaterialHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialAdapter.MaterialHolder holder, int position) {
        Material material = materialList.get(position);
        holder.text.setText(material.getText());
    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }
    public class MaterialHolder extends RecyclerView.ViewHolder{
        private TextView text;

        public MaterialHolder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.material_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(materialList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Material material);
    }

    public void setOnItemClickListener(MaterialAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
