package ac.gre.ma500.mad_sports.models;

import android.content.ContentValues;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class SportEvent {
    public Integer _id;
    public String sportName, competition, location;
    public String teamHome, teamAway, referee;
    public Date startDate; public Time startTime;

    public SportEvent()
    {
        _id = 0;
        sportName = " ";
        competition = " ";
        location = " ";
        teamHome = " ";
        teamAway = " ";
        referee = " ";
        startDate = new Date(Calendar.getInstance().getTimeInMillis());
        startTime = new Time(Calendar.getInstance().getTimeInMillis());
    }

    public SportEvent(ContentValues cv)
    {
        _id = cv.getAsInteger(AppDbDefination.SportEventTable.COLUMN_ID);
        sportName = cv.getAsString(AppDbDefination.SportEventTable.COLUMN_SPORT_NAME);
        competition = cv.getAsString(AppDbDefination.SportEventTable.COLUMN_COMPETITION);
        location = cv.getAsString(AppDbDefination.SportEventTable.COLUMN_LOCATION);
        teamHome = cv.getAsString(AppDbDefination.SportEventTable.COLUMN_TEAM_HOME);
        teamAway = cv.getAsString(AppDbDefination.SportEventTable.COLUMN_TEAM_AWAY);
        referee = cv.getAsString(AppDbDefination.SportEventTable.COLUMN_REFEREE);
        startDate = AppUtility.dateFromString(
                cv.getAsString(AppDbDefination.SportEventTable.COLUMN_START_DATE));

        startTime= AppUtility.timeFromString(
                cv.getAsString(AppDbDefination.SportEventTable.COLUMN_START_TIME));

    }

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(AppDbDefination.SportEventTable.COLUMN_ID, _id);
        values.put(AppDbDefination.SportEventTable.COLUMN_SPORT_NAME, sportName);
        values.put(AppDbDefination.SportEventTable.COLUMN_COMPETITION, competition);
        values.put(AppDbDefination.SportEventTable.COLUMN_LOCATION, location);
        values.put(AppDbDefination.SportEventTable.COLUMN_TEAM_HOME, teamHome);
        values.put(AppDbDefination.SportEventTable.COLUMN_TEAM_AWAY, teamAway);
        values.put(AppDbDefination.SportEventTable.COLUMN_REFEREE, referee);
        values.put(AppDbDefination.SportEventTable.COLUMN_START_DATE, startDate.toString());
        values.put(AppDbDefination.SportEventTable.COLUMN_START_TIME, startTime.toString());

        values.put(AppDbDefination.SportEventTable.COLUMN_ENTRY_TIMESTAMP,
                new Date(Calendar.getInstance().getTimeInMillis()).toString());

        return  values;
    }


    public static boolean inSelectMode = false;
    public boolean isSelected;
}
