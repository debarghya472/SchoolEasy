package android.example.schooleasy.ui.eventsCalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.example.schooleasy.R;
import android.os.Bundle;

public class EventsActivity extends AppCompatActivity {
    CustomCalenderView customCalenderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.color.background_color);
        setContentView(R.layout.activity_events);
        customCalenderView=findViewById(R.id.custom_calendar_view);
    }
}