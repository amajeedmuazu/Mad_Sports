package ac.gre.ma500.mad_sports.models;

import java.util.ArrayList;

/**
 * Created by Majeed on 09/11/14.
 */
public final class AppDbDefination {

    //Empty Constructor
    public AppDbDefination() {
    }


    //CONSTANTS --------------------------------------------------------------
    private static final String TYPE_STRING = " TEXT";
    private static final String TYPE_INT = " INTEGER";
    private static final String TYPE_DATE = " TEXT";
    private static final String TYPE_TIME = " TEXT";
    private static final String TYPE_TIMESTAMP = " TEXT";
    //private static final String TYPE_BOOL = " INT";

    private static final String COMMA = ",";
    private static final String AUTO_PRIMARY_KEY = " PRIMARY KEY AUTOINCREMENT";
    private static final String NOT_NULL = " NOT NULL";


    //TABLES -----------------------------------------------------------------
    public interface _BaseTable {

        //Primary Key Column for All Tables
        public static final String COLUMN_ID = "id";

        //Timestamp for when each row is updated (Used for synchronization)
        public static final String COLUMN_ENTRY_TIMESTAMP = "update_timestamp";


    }

    //Events Table
    public static abstract class SportEventTable implements _BaseTable {
        public static final String TABLE_NAME = "sport_event";
        public static final String COLUMN_SPORT_NAME = "sport_name";
        public static final String COLUMN_COMPETITION = "competition";
        public static final String COLUMN_START_DATE = "start_date";
        public static final String COLUMN_START_TIME = "start_time";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_TEAM_HOME = "team_home";
        public static final String COLUMN_TEAM_AWAY = "team_away";
        public static final String COLUMN_REFEREE = "referee";

        public static final String CREATE_TABLE_SQL =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + TYPE_INT + AUTO_PRIMARY_KEY + COMMA +
                        COLUMN_SPORT_NAME + TYPE_STRING + COMMA +
                        COLUMN_COMPETITION + TYPE_STRING + COMMA +
                        COLUMN_START_DATE + TYPE_DATE + COMMA +
                        COLUMN_START_TIME + TYPE_TIME + COMMA +
                        COLUMN_LOCATION + TYPE_STRING + COMMA +
                        COLUMN_TEAM_HOME + TYPE_STRING + COMMA +
                        COLUMN_TEAM_AWAY + TYPE_STRING + COMMA +
                        COLUMN_REFEREE + TYPE_STRING + COMMA +
                        COLUMN_ENTRY_TIMESTAMP + TYPE_TIMESTAMP + ")";

        public static final String DELETE_TABLE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;


    }

    //Event's Actions Table
    public static abstract class EventActionTable implements _BaseTable {
        public static final String TABLE_NAME = "event_action";
        public static final String COLUMN_SPORT_EVENT_ID = "sport_event_id";
        public static final String COLUMN_ACTION_TYPE = "action_type";
        public static final String COLUMN_DATE = "action_date";
        public static final String COLUMN_TIME = "action_time";
        public static final String COLUMN_ACTION_BY = "by";
        public static final String COLUMN_ACTION_ON = "on";
        public static final String COLUMN_COMMENT = "comment";

        public static final String CREATE_TABLE_SQL =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + TYPE_INT + AUTO_PRIMARY_KEY + COMMA +
                        COLUMN_SPORT_EVENT_ID + TYPE_STRING + COMMA +
                        COLUMN_ACTION_TYPE + TYPE_STRING + NOT_NULL + COMMA +
                        COLUMN_DATE + TYPE_DATE + NOT_NULL + COMMA +
                        COLUMN_TIME + TYPE_TIME + NOT_NULL + COMMA +
                        COLUMN_ACTION_BY + TYPE_STRING + COMMA +
                        COLUMN_ACTION_ON + TYPE_STRING + COMMA +
                        COLUMN_COMMENT + TYPE_STRING + COMMA + ")";

        public static final String DELETE_TABLE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


    //Enumerations
    public static final String[] SPORTS_TYPE = new String[]{
            "Football",
            "Basketball",
            "Hockey",
            "Handball"};

    public static final String[] SPORTS_TYPE_FILTER = GetSportsType();

    public static final String[] TEAMS_NAMES_FOOTBALL = new String[]{
            "Arsenal",
            "Aston Villa",
            "Burnley",
            "Chelsea",
            "Crystal Palace",
            "Everton",
            "Hull City",
            "Leicester City",
            "Liverpool",
            "Manchester City",
            "Manchester United",
            "Newcastle United",
            "Queens Park Rangers",
            "Southampton",
            "Stoke City",
            "Sunderland",
            "Swansea City",
            "Tottenham Hotspur",
            "West Bromwich Albion",
            "West Ham United"};

    public static final String[] LOCATIONS_FOOTBALL = new String[]{
            "Emirates Stadium",
            "Villa Park",
            "Turf Moor",
            "Stamford Bridge",
            "Selhurst Park",
            "Goodison Park",
            "Kingston Stadium",
            "King Power Stadium",
            "Anfield",
            "Etihad Stadium",
            "Old Trafford",
            "St James' Park",
            "Loftus Road",
            "St Mary's Stadium",
            "Britannia Stadium",
            "Stadium of Light",
            "Liberty Stadium",
            "White Hart Lane",
            "The Hawthorns",
            "Boleyn Ground"};


    public static String[] GetSportsType() {
        ArrayList<String> filters = new ArrayList<String>();
        filters.add("All Sports");
        for (int i = 0; i < SPORTS_TYPE.length; i++) {
            filters.add(SPORTS_TYPE[i]);
        }
        return filters.toArray(new String[SPORTS_TYPE.length + 1]);
    }

    //SQL COMMANDS -----------------------------------------------------------------

    //Ordered Set of create table commands
    public static final String CREATE_TABLES_SQL =
            SportEventTable.CREATE_TABLE_SQL + " " +
                    EventActionTable.CREATE_TABLE_SQL;


    //Ordered Set of drop table commands
    public static final String DROP_TABLES_SQL =
            EventActionTable.DELETE_TABLE_SQL + " " +
                    SportEventTable.DELETE_TABLE_SQL;


    public static final String CLEAR_TABLES_SQL =
            "";
}
