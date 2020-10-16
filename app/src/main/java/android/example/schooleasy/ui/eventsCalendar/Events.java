package android.example.schooleasy.ui.eventsCalendar;

public class Events {
    String event, time, date, month, year;

    public String getDate() {
        return date;
    }

    public String getEvent() {
        return event;
    }

    public String getMonth() {
        return month;
    }

    public String getTime() {
        return time;
    }

    public String getYear() {
        return year;
    }
    public Events(String event, String time, String date, String month, String year){
        this.date=date;
        this.event=event;
        this.month=month;
        this.time=time;
        this.year=year;
    }
}
