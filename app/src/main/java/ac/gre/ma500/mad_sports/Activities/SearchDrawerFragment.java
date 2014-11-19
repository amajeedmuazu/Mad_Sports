package ac.gre.ma500.mad_sports.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import ac.gre.ma500.mad_sports.Activities.SharedFragments.DatePickerFragment;
import ac.gre.ma500.mad_sports.Activities.SharedFragments.DialogCancelledHandler;
import ac.gre.ma500.mad_sports.Activities.SharedFragments.SelectMultipleDialogFragment;
import ac.gre.ma500.mad_sports.Activities.SharedFragments.TimePickerFragment;
import ac.gre.ma500.mad_sports.R;
import ac.gre.ma500.mad_sports.models.AppDbDefination;
import ac.gre.ma500.mad_sports.models.AppUtility;
import ac.gre.ma500.mad_sports.models.SearchModel;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class SearchDrawerFragment extends Fragment implements
        DialogCancelledHandler {

    //These are used to detect which of the MultiChoice Dialogs returned;
    public static final int REF_SPORTSTYPES = 1;
    public static final int REF_LOCATIONS = 2;
    public static final int REF_TEAMS = 3;


    //A pointer to the current callbacks instance (the Activity).
    private SearchDrawerCallbacks mCallbacks;

    //Helper component that ties the action bar to the navigation drawer.
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;
    private View searchView;

    private EditText view_SportType_Entry;
    private EditText view_StartDate_Entry;
    private EditText view_StartTime_Entry;
    private EditText view_Location_Entry;
    private EditText view_Teams_Entry;

    //Search Views ----------------------------------------------


    //-----------------------------------------------------------

    public SearchModel searchModel;

    public SearchDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchModel = new SearchModel();
        //displayResultForModel(searchModel);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchView = inflater.inflate(
                R.layout.fragment_search_drawer, container, false);

        //Populate subviews with the current search model.
        // Eg:

        //((TextView)rowView.findViewById(R.id.listItem_eventTeamHome))
        //.setText(sp.teamHome);

        view_SportType_Entry = (EditText) searchView.findViewById(R.id.view_search_sports_type);
        view_StartDate_Entry = (EditText) searchView.findViewById(R.id.view_search_startdate);
        view_StartTime_Entry = (EditText) searchView.findViewById(R.id.view_search_starttime);
        view_Location_Entry = (EditText) searchView.findViewById(R.id.view_search_location);
        view_Teams_Entry = (EditText) searchView.findViewById(R.id.view_search_teams);


        return searchView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };


        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void displayResultForModel(SearchModel sm) {

        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onSearchModelSelected(sm);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (SearchDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            //inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();

        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }

        if (item.getItemId() == R.id.menu_action_search) {
            displayResultForModel(this.searchModel);


        }

        //ToDo: Modify Handler
        //if (item.getItemId() == R.id.action_example) {
        //    Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    /**
     * As per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Filter Events");
    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    public void onEntryViewClicked(View v) {

        int viewId = v.getId();

        if (viewId == view_SportType_Entry.getId())
            showSportTypesSelector(v);

        else if (viewId == view_StartDate_Entry.getId())
            showSearchStartDatePicker(v);

        else if (viewId == view_StartTime_Entry.getId())
            showSearchStartTimePicker(v);

        else if (viewId == view_Location_Entry.getId())
            showLocationSelector(v);

        else if (viewId == view_Teams_Entry.getId())
            showTeamsSelector(v);

        else
            ;//Do nothing
    }


    public void showSportTypesSelector(View v) {
        SelectMultipleDialogFragment dialog = new SelectMultipleDialogFragment();

        dialog.setUp(REF_SPORTSTYPES, "Select Sports", AppDbDefination.SPORTS_TYPE
                , searchModel.sportNames);
        dialog.show(getFragmentManager(), "MultiChoiceSelector");

    }

    private void showLocationSelector(View v) {
        SelectMultipleDialogFragment dialog = new SelectMultipleDialogFragment();

        dialog.setUp(REF_LOCATIONS, "Select Locations", AppDbDefination.LOCATIONS_FOOTBALL
                , searchModel.locations);
        dialog.show(getFragmentManager(), "MultiChoiceSelector");

    }

    private void showTeamsSelector(View v) {

        SelectMultipleDialogFragment dialog = new SelectMultipleDialogFragment();

        dialog.setUp(REF_TEAMS, "Select Teams", AppDbDefination.TEAMS_NAMES_FOOTBALL
                , searchModel.teams);
        dialog.show(getFragmentManager(), "MultiChoiceSelector");

    }

    public void onMultiChoiceDone(int reference, String[] arrayList) {
        switch (reference) {
            case REF_SPORTSTYPES:
                searchModel.sportNames = arrayList;
                view_SportType_Entry.setText(AppUtility.getDelimitedDescription(
                        searchModel.sportNames, 25, "Sports"));
                break;

            case REF_LOCATIONS:
                searchModel.locations = arrayList;
                view_Location_Entry.setText(AppUtility.getDelimitedDescription(
                        searchModel.locations, 25, "Location"));
                break;

            case REF_TEAMS:
                searchModel.teams = arrayList;
                view_Teams_Entry.setText(AppUtility.getDelimitedDescription(
                        searchModel.teams, 25, "Location"));
                break;

            default:
                return;
        }
    }


    public void onMultiChoiceCancel(int reference) {
        switch (reference) {
            case REF_SPORTSTYPES:
                searchModel.sportNames = null;
                view_SportType_Entry.setText("");
                break;
            case REF_LOCATIONS:
                searchModel.locations = null;
                view_Location_Entry.setText("");
                break;
            case REF_TEAMS:
                searchModel.teams = null;
                view_Teams_Entry.setText("");
                break;
            default:
                return;
        }
    }

    public void showSearchStartDatePicker(View v) {
        //Todo show date picker dialog
        DatePickerFragment dateFragment = new DatePickerFragment();
        dateFragment.setDate(searchModel.startDate != null ?
                searchModel.startDate : AppUtility.getCurrentDate());
        dateFragment.setCallBack(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth, 0, 0);
                searchModel.startDate = new Date(c.getTimeInMillis());
                view_StartDate_Entry.setText("On Date : " +
                        String.format("%02d / %02d / %d", dayOfMonth, (monthOfYear + 1), year));
            }
        });

        dateFragment.reference = 0;
        dateFragment.cancelledHandler = this;
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    public void showSearchStartTimePicker(View v) {
        //Todo show time picker dialog
        TimePickerFragment timeFragment = new TimePickerFragment();
        timeFragment.setTime(searchModel.startTime != null ?
                searchModel.startTime : AppUtility.getCurrentTime());

        timeFragment.setCallBack(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                searchModel.startTime = new Time(c.getTimeInMillis());
                view_StartTime_Entry.setText("Start Time : " +
                        String.format("%02d : %02d ", hourOfDay, minute));
            }
        });
        timeFragment.reference = 1;
        timeFragment.cancelledHandler = this;

        timeFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onDialogCancelled(int reference) {
        if (reference == 0) {
            searchModel.startDate = null;
            view_StartDate_Entry.setText("");
        } else if (reference == 1) {
            searchModel.startTime = null;
            view_StartTime_Entry.setText("");
        }
    }


    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface SearchDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onSearchModelSelected(SearchModel sm);
    }


}
