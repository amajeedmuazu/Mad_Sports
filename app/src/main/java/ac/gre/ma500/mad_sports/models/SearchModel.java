package ac.gre.ma500.mad_sports.models;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Majeed on 14/11/14.
 */
public class SearchModel {

    public String[] sportNames;
    public String[] teams;
    public String[] locations;
    public Date startDate;
    public Time startTime;

    public void clear() {
        sportNames = null;
        teams = null;
        locations = null;
        startDate = null;
        startTime = null;
    }

}
