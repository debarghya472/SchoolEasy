package android.example.schooleasy.ui.eventsCalendar;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.example.schooleasy.BuildConfig;
import android.example.schooleasy.R;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CustomCalenderView extends LinearLayout {
    ImageButton nextBtn, prevBtn;
    TextView currentDate;
    GridView gridView;
    DBOpenHolder dbOpenHolder;
    MyGridAdapter myGridAdapter;
    AlertDialog alertDialog;
    private static final int MAX_CALENDAR_DAYS=42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy",Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM",Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);
    SimpleDateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);

    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();

    public CustomCalenderView(Context context) {
        super(context);
    }

    public CustomCalenderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        InitializeLayout();
        SetUpCalendar();

        prevBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,-1);
                SetUpCalendar();
            }
        });

        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,1);
                SetUpCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_new_event_layout,null);
                final EditText eventName = addView.findViewById(R.id.eventType);
                final TextView eventTime = addView.findViewById(R.id.eventtime);
                ImageButton setTime = addView.findViewById(R.id.setEventTime);
                Button addEvent = addView.findViewById(R.id.add_event);
                setTime.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(addView.getContext(), R.style.Theme_AppCompat_Dialog
                                , new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat format = new SimpleDateFormat("K:mm a",Locale.ENGLISH);
                                String event_Time = format.format(calendar.getTime());
                                eventTime.setText(event_Time);

                            }
                        },hours,minute,false);
                        timePickerDialog.show();

                    }
                });
                final String date = eventDateFormat.format(dates.get(position));
                final String month = monthFormat.format(dates.get(position));
                final String year = yearFormat.format(dates.get(position));
                addEvent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SaveEvent(eventName.getText().toString(),eventTime.getText().toString(),date,month,year);
                        alertDialog.dismiss();

                    }
                });
                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();

            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String date = eventDateFormat.format(dates.get(position));
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View showView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_layout,null);
                RecyclerView recyclerView = showView.findViewById(R.id.events_rv);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                EventsRecyclerAdapter eventsRecyclerAdapter = new EventsRecyclerAdapter(showView.getContext(),CollectEventByDate(date));
                recyclerView.setAdapter(eventsRecyclerAdapter);
                eventsRecyclerAdapter.notifyDataSetChanged();

                builder.setView(showView);
                alertDialog= builder.create();
                alertDialog.show();


                return true;
            }
        });


    }

    private ArrayList<Events> CollectEventByDate(String date){
        ArrayList<Events> arrayList = new ArrayList<>();
        dbOpenHolder= new DBOpenHolder(context);
        SQLiteDatabase database = dbOpenHolder.getReadableDatabase();
        Cursor cursor = dbOpenHolder.ReadEvents(date,database);
        while (cursor.moveToNext()){
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.Event));
            String time = cursor.getString(cursor.getColumnIndex(DBStructure.time));
            String Date = cursor.getString(cursor.getColumnIndex(DBStructure.date));
            String Month = cursor.getString(cursor.getColumnIndex(DBStructure.month));
            String Year = cursor.getString(cursor.getColumnIndex(DBStructure.year));
            Events events = new Events(event,time,Date,Month,Year);
            arrayList.add(events);

        }
        cursor.close();
        dbOpenHolder.close();
        return arrayList;
    }
    private void InitializeLayout(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout,this);
        nextBtn = view.findViewById(R.id.nextBtn);
        prevBtn=view.findViewById(R.id.previous_button);
        currentDate=view.findViewById(R.id.current_date);
        gridView=view.findViewById(R.id.gridview);
    }

    private void SaveEvent(String event, String time, String date, String month, String year){
        dbOpenHolder = new DBOpenHolder(context);
        SQLiteDatabase database = dbOpenHolder.getWritableDatabase();
        dbOpenHolder.saveEvent(event,time,date,month,year,database);
        dbOpenHolder.close();
        Toast.makeText(context,"Event Saved",Toast.LENGTH_SHORT).show();

    }

    public CustomCalenderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    private void SetUpCalendar(){
        String currentdate = dateFormat.format(calendar.getTime());
        currentDate.setText(currentdate);
        dates.clear();
        Calendar month_calendar = (Calendar) calendar.clone();
        month_calendar.set(Calendar.DAY_OF_MONTH,1);
        int first_day_of_month = month_calendar.get(Calendar.DAY_OF_WEEK)-1;
        month_calendar.add(Calendar.DAY_OF_MONTH,-first_day_of_month);
        CollectEventsPerMonth(monthFormat.format(calendar.getTime()),yearFormat.format(calendar.getTime()));

        while (dates.size() < MAX_CALENDAR_DAYS){
            dates.add(month_calendar.getTime());
            month_calendar.add(Calendar.DAY_OF_MONTH,1);
        }

        myGridAdapter = new MyGridAdapter(context,dates,calendar,eventsList);
        gridView.setAdapter(myGridAdapter);

    }
    private void CollectEventsPerMonth(String month, String year){
        eventsList.clear();
        dbOpenHolder= new DBOpenHolder(context);
        SQLiteDatabase database = dbOpenHolder.getReadableDatabase();
        Cursor cursor = dbOpenHolder.ReadEventsPerMonth(month,year,database);
        while (cursor.moveToNext()){
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.Event));
            String time = cursor.getString(cursor.getColumnIndex(DBStructure.time));
            String date = cursor.getString(cursor.getColumnIndex(DBStructure.date));
            String Month = cursor.getString(cursor.getColumnIndex(DBStructure.month));
            String Year = cursor.getString(cursor.getColumnIndex(DBStructure.year));
            Events events = new Events(event,time,date,Month,Year);
            eventsList.add(events);
        }
        cursor.close();
        dbOpenHolder.close();
    }
}
