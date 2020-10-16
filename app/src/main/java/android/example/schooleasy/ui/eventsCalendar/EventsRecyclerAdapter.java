package android.example.schooleasy.ui.eventsCalendar;

import android.content.Context;
import android.example.schooleasy.R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.MyViewHolder> {
    Context context;
    ArrayList<Events> arrayList;

    public EventsRecyclerAdapter(Context context, ArrayList<Events> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView DateTxt,Event,Time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            DateTxt=itemView.findViewById(R.id.eventDate);
            Event = itemView.findViewById(R.id.eventName);
            Time= itemView.findViewById(R.id.eventTime);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_row_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Events events = arrayList.get(position);
        Toast.makeText(context,"Event "+ events.getEvent(),Toast.LENGTH_SHORT).show();
        holder.Event.setText(events.getEvent());
        holder.DateTxt.setText(events.getDate());
        holder.Time.setText(events.getTime());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
