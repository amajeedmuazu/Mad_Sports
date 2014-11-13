package ac.gre.ma500.mad_sports.models;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Majeed on 09/11/14.
 */
public class AppUtility {

    public static Date dateFromString(String sdate)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        try {
            java.util.Date date = formatter.parse(sdate);
            return  new Date(date.getTime());
        }
        catch (Exception e)
        {
            return new Date(Calendar.getInstance().getTimeInMillis());
        }
    }

    public static Time timeFromString(String stime)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        try {
            java.util.Date date = formatter.parse(stime);
            return  new Time(date.getTime());
        }
        catch (Exception e)
        {
            return new Time(Calendar.getInstance().getTimeInMillis());
        }
    }
}
