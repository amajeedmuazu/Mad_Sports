package ac.gre.ma500.mad_sports.models;

/**
 * Created by Majeed on 09/11/14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class AppRepository {

    SQLiteDatabase db;
    AppDbHelper helper;

    public AppRepository(Context context)
    {
        helper = new AppDbHelper(context);
        db = helper.getWritableDatabase();
        _sportEvents = new ArrayList<SportEvent>();


    }

    public void SaveData()
    {
        for(SportEvent se : this._sportEvents)
        {
            db.insertWithOnConflict(AppDbDefination.SportEventTable.TABLE_NAME,
                    null,
                    se.getContentValues(),
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
    }


    public void WipeDB()
    {

    }

    public void LoadData()
    {
        this._sportEvents.clear();
        Cursor sportEventsCursor = db.query(AppDbDefination.SportEventTable.TABLE_NAME,
                null,null,null,null,null,null);

        if(sportEventsCursor.moveToFirst())
        {
            while(!sportEventsCursor.isAfterLast())
            {
                this._sportEvents.add(new SportEvent(getValuesFromCursor(sportEventsCursor)));
                sportEventsCursor.moveToNext();
            }
        }
    }

    private static final String OPEN = "( ";
    private static final String CLOSE = " )";
    private static final String OR = " OR ";
    private static final String AND = " AND ";
    private static final String LIKE = " LIKE ? ";
    private static final String EQUALS = " = ? ";
    private static final String GREATER_OR_EQUAL = " >= ? ";
    private static final String LESSER_OR_EQUAL = " <= ? ";

    public void loadDataForSearchModel(SearchModel sm) {

        this._sportEvents.clear();
        Cursor sportEventsCursor = null;
        if (sm != null) {
            ArrayList<String> args = new ArrayList<String>();
            String whereClause = OPEN;

            //Where Sport Names
            if (sm.sportNames != null && sm.sportNames.length > 0) {
                whereClause += OPEN;
                for (int i = 0; i < sm.sportNames.length; i++) {

                    if (!whereClause.endsWith(OPEN))
                        whereClause += OR;

                    whereClause += AppDbDefination.SportEventTable.COLUMN_SPORT_NAME + LIKE;
                    args.add(sm.sportNames[i]);
                }
                whereClause += CLOSE;
            }

            //Where Locations
            if (sm.locations != null && sm.locations.length > 0) {
                if (whereClause.length() > OPEN.length()) whereClause += AND;
                whereClause += OPEN;
                for (int i = 0; i < sm.locations.length; i++) {

                    if (!whereClause.endsWith(OPEN))
                        whereClause += OR;

                    whereClause += AppDbDefination.SportEventTable.COLUMN_LOCATION + LIKE;
                    args.add(sm.locations[i]);
                }
                whereClause += CLOSE;
            }

            //Where Teams
            if (sm.teams != null && sm.locations.length > 0) {
                if (whereClause.length() > OPEN.length()) whereClause += AND;
                whereClause += OPEN;
                for (int i = 0; i < sm.teams.length; i++) {

                    if (!whereClause.endsWith(OPEN)) {
                        whereClause += OR;

                    }

                    if (sm.teams.length > 1) whereClause += OPEN;

                    whereClause += AppDbDefination.SportEventTable.COLUMN_TEAM_HOME + LIKE;
                    args.add(sm.teams[i]);

                    whereClause += OR;

                    whereClause += AppDbDefination.SportEventTable.COLUMN_TEAM_AWAY + LIKE;
                    args.add(sm.teams[i]);

                    if (sm.teams.length > 1) whereClause += CLOSE;


                }
                whereClause += CLOSE;
            }

            //Where Startdate
            if (sm.startDate != null) {
                if (whereClause.length() > OPEN.length()) whereClause += AND;

                whereClause += OPEN;
                whereClause += AppDbDefination.SportEventTable.COLUMN_START_DATE + GREATER_OR_EQUAL;
                args.add(sm.startDate.toString());
                whereClause += CLOSE;
            }

            if (sm.startTime != null) {
                if (whereClause.length() > OPEN.length()) whereClause += AND;

                whereClause += OPEN;
                whereClause += AppDbDefination.SportEventTable.COLUMN_START_TIME + GREATER_OR_EQUAL;
                args.add(sm.startTime.toString());
                whereClause += CLOSE;
            }

            whereClause += CLOSE;

            try {


                if (whereClause.length() > OPEN.length() && args.size() > 0) {
                    String[] whereArgs = new String[args.size()];
                    args.toArray(whereArgs);

                    sportEventsCursor = db.query(AppDbDefination.SportEventTable.TABLE_NAME,
                            null, whereClause, whereArgs, null, null, null);
                } else {
                    sportEventsCursor = db.query(AppDbDefination.SportEventTable.TABLE_NAME,
                            null, null, null, null, null, null);
                }

                if (sportEventsCursor.moveToFirst()) {
                    while (!sportEventsCursor.isAfterLast()) {
                        this._sportEvents.add(new SportEvent(getValuesFromCursor(sportEventsCursor)));
                        sportEventsCursor.moveToNext();
                    }
                }
            } catch (Exception ex) {
                Log.e("Error", ex.getMessage());
            }
        } else {
            sportEventsCursor = db.query(AppDbDefination.SportEventTable.TABLE_NAME,
                    null, null, null, null, null, null);

            if (sportEventsCursor.moveToFirst()) {
                while (!sportEventsCursor.isAfterLast()) {
                    this._sportEvents.add(new SportEvent(getValuesFromCursor(sportEventsCursor)));
                    sportEventsCursor.moveToNext();
                }
            }
        }

    }

    //Deprecated
    /*public void loadEventsWithSportsNameFilter(String[] sportNames)
    {
        this._sportEvents.clear();


        String whereClause = "";
        for (int i = 0; i < sportNames.length; i++) {
            if(i ==0)
                whereClause += AppDbDefination.SportEventTable.COLUMN_SPORT_NAME + " = ?";
            else
                whereClause += " OR " + AppDbDefination.SportEventTable.COLUMN_SPORT_NAME + " = ?";
        }
        Cursor sportEventsCursor = db.query(AppDbDefination.SportEventTable.TABLE_NAME,
                null,whereClause,sportNames,null,null,null);


        if(sportEventsCursor.moveToFirst())
        {
            while(!sportEventsCursor.isAfterLast())
            {
                this._sportEvents.add(new SportEvent(getValuesFromCursor(sportEventsCursor)));
                sportEventsCursor.moveToNext();
            }
        }
    }*/

    private ContentValues getValuesFromCursor(Cursor c)
    {
        ContentValues v = new ContentValues();
        for (int i = 0; i < c.getColumnCount(); i++) {
            String col = c.getColumnName(i);
            v.put(col,c.getString(c.getColumnIndex(col)));
        }
        return  v;
    }

    private ArrayList<SportEvent> _sportEvents;
    public ArrayList<SportEvent> getSportEvents() {
        if(_sportEvents == null)
                _sportEvents = new ArrayList<SportEvent>();
        return _sportEvents;
    }

    public boolean AddEvent(SportEvent se)
    {
        try {
            ContentValues cv = se.getContentValues();
            cv.remove(AppDbDefination.SportEventTable.COLUMN_ID);
            se._id = (int)

            db.insertOrThrow(AppDbDefination.SportEventTable.TABLE_NAME,
                    null, cv);

            this._sportEvents.add(se);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

    }
    public boolean DeleteEventsAtIndexes(ArrayList<Integer> indexes)
    {
        try {

            String where = AppDbDefination.SportEventTable.COLUMN_ID + " LIKE ?";
            for (int i = 0; i < indexes.size(); i++) {
                String[] args = {_sportEvents.get(indexes.get(i))._id.toString()};
                db.delete(AppDbDefination.SportEventTable.TABLE_NAME, where, args);
            }


            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }




}
