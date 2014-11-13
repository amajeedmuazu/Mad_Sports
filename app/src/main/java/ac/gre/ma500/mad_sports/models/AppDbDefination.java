package ac.gre.ma500.mad_sports.models;

/**
 * Created by Majeed on 09/11/14.
 */
public final class AppDbDefination {

    //Empty Constructor
    public AppDbDefination(){}


    //CONSTANTS --------------------------------------------------------------
    private static final String TYPE_STRING = " TEXT";
    private static final String TYPE_INT = " INTEGER";
    private static final String TYPE_DATE = " TEXT";
    private static final String TYPE_TIME = " TEXT";
    private static final String TYPE_TIMESTAMP = " TEXT";
    //private static final String TYPE_BOOL = " INT";

    private static final String COMMA = ",";
    private static final String AUTO_PRIMARY_KEY =" PRIMARY KEY AUTOINCREMENT";
    private static final String NOT_NULL = " NOT NULL";



    //TABLES -----------------------------------------------------------------
    public interface  _BaseTable {

        //Primary Key Column for All Tables
        public static final String COLUMN_NAME_ID = "id";

        //Timestamp for when each row is updated (Used for synchronization)
        public static final String COLUMN_NAME_ENTRY_TIMESTAMP = "update_timestamp";


    }

    //Events Table
    public static abstract class SportEventTable implements  _BaseTable {
        public static final String TABLE_NAME = "sport_event";
        public static final String COLUMN_NAME_SPORT_NAME = "sport_name";
        public static final String COLUMN_NAME_COMPETITION = "competition";
        public static final String COLUMN_NAME_START_DATE = "start_date";
        public static final String COLUMN_NAME_START_TIME = "start_time";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_TEAM_HOME = "team_home";
        public static final String COLUMN_NAME_TEAM_AWAY = "team_away";
        public static final String COLUMN_NAME_REFEREE = "referee";

        public static final String CREATE_TABLE_SQL =
                    "CREATE TABLE " + TABLE_NAME + " (" +
                            COLUMN_NAME_ID + TYPE_INT + AUTO_PRIMARY_KEY  + COMMA +
                            COLUMN_NAME_SPORT_NAME + TYPE_STRING + COMMA +
                            COLUMN_NAME_COMPETITION + TYPE_STRING + COMMA +
                            COLUMN_NAME_START_DATE + TYPE_DATE + COMMA +
                            COLUMN_NAME_START_TIME + TYPE_TIME + COMMA +
                            COLUMN_NAME_LOCATION + TYPE_STRING + COMMA +
                            COLUMN_NAME_TEAM_HOME + TYPE_STRING + COMMA +
                            COLUMN_NAME_TEAM_AWAY + TYPE_STRING + COMMA +
                            COLUMN_NAME_REFEREE + TYPE_STRING + COMMA +
                            COLUMN_NAME_ENTRY_TIMESTAMP + TYPE_TIMESTAMP + ")";

        public static final String DELETE_TABLE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;


    }

    //Event's Actions Table
    public static abstract class EventActionTable implements _BaseTable {
        public static final String TABLE_NAME = "event_action";
        public static final String COLUMN_NAME_SPORT_EVENT_ID = "sport_event_id";
        public static final String COLUMN_NAME_ACTION_TYPE = "action_type";
        public static final String COLUMN_NAME_DATE = "action_date";
        public static final String COLUMN_NAME_TIME = "action_time";
        public static final String COLUMN_NAME_ACTION_BY = "by";
        public static final String COLUMN_NAME_ACTION_ON = "on";
        public static final String COLUMN_NAME_COMMENT = "comment";

         public static final String CREATE_TABLE_SQL =
                 "CREATE TABLE " + TABLE_NAME + " (" +
                         COLUMN_NAME_ID + TYPE_INT + AUTO_PRIMARY_KEY + COMMA +
                         COLUMN_NAME_SPORT_EVENT_ID + TYPE_STRING + COMMA +
                         COLUMN_NAME_ACTION_TYPE + TYPE_STRING + NOT_NULL + COMMA +
                         COLUMN_NAME_DATE + TYPE_DATE + NOT_NULL + COMMA +
                         COLUMN_NAME_TIME + TYPE_TIME + NOT_NULL + COMMA +
                         COLUMN_NAME_ACTION_BY + TYPE_STRING + COMMA +
                         COLUMN_NAME_ACTION_ON + TYPE_STRING + COMMA +
                         COLUMN_NAME_COMMENT + TYPE_STRING + COMMA + ")";

        public static final String DELETE_TABLE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


    //Enumerations
    public static final  String[] SPORTS_TYPE = new String[]{
            "Football",
            "Basketball",
            "Hockey",
            "Handball"};

    public static final String[] SPORTS_FILTER = new String[]{"All",
            SPORTS_TYPE[0],
            SPORTS_TYPE[1],
            SPORTS_TYPE[2],
            SPORTS_TYPE[3]};

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
