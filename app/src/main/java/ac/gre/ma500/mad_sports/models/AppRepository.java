package ac.gre.ma500.mad_sports.models;

/**
 * Created by Majeed on 09/11/14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public void loadEventsWithSportsNameFilter(String[] sportNames)
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
    }

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
    public boolean DeleteEvent(int id)
    {
        try {
            String where = AppDbDefination.SportEventTable.COLUMN_ID + " LIKE ?";
            String[] args = {String.valueOf(id)};

            db.delete(AppDbDefination.SportEventTable.TABLE_NAME, where, args);

            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }




}
