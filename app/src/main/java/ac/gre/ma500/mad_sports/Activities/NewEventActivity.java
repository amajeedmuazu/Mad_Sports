package ac.gre.ma500.mad_sports.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import ac.gre.ma500.mad_sports.Activities.SharedFragments.DatePickerFragment;
import ac.gre.ma500.mad_sports.Activities.SharedFragments.TimePickerFragment;
import ac.gre.ma500.mad_sports.R;
import ac.gre.ma500.mad_sports.models.AppRepository;
import ac.gre.ma500.mad_sports.models.SportEvent;


public class NewEventActivity extends Activity {

    //PROPERTIES-----------------------------------
    public SportEvent sportEvent;

    private AppRepository repo;
    //End PROPERTIES-------------------------------

    //VIEWS----------------------------------------
    Spinner sportsTypeSelector;
    AutoCompleteTextView locationEntry;
    AutoCompleteTextView homeTeamEntry, awayTeamEntry;
    TextView eventDateEntry, eventTimeEntry;

    //End VIEWS------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        repo = new AppRepository(this);
        repo.LoadData();

        this.initSportEvent();
        this.findViews();
        this.bindViews();
        //this.loadEventInView();
    }


    public void initSportEvent()
    {
        if(sportEvent == null)
        {
            ArrayList<SportEvent> ses = repo.getSportEvents();
            if(!ses.isEmpty())
                sportEvent = ses.get(ses.size() - 1);
            else{
            sportEvent = new SportEvent();
        }
        }


    }

    private void findViews()
    {
        sportsTypeSelector = (Spinner)findViewById(R.id.sports_type_selector);
        locationEntry = (AutoCompleteTextView)findViewById(R.id.location_entry);
        eventDateEntry = (TextView)findViewById(R.id.event_date_entry);
        eventTimeEntry = (TextView)findViewById(R.id.event_time_entry);
        homeTeamEntry = (AutoCompleteTextView)findViewById(R.id.team_home_entry);
        awayTeamEntry = (AutoCompleteTextView)findViewById(R.id.team_away_entry);
    }

    private void bindViews()
    {
        //Bind Sports Type Spinner
        ArrayAdapter<CharSequence> sportsTypeAdapter =
                ArrayAdapter.createFromResource(this, R.array.data_newevent_sport_types,
                                                            android.R.layout.simple_spinner_item);
        sportsTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportsTypeSelector.setAdapter(sportsTypeAdapter);

        //Bind Event Location auto-complete list
        ArrayAdapter<CharSequence> locationAutoCompleteAdapter =
                ArrayAdapter.createFromResource(this, R.array.data_newevent_locations,
                        android.R.layout.simple_spinner_item);
        locationAutoCompleteAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        locationEntry.setAdapter(locationAutoCompleteAdapter);

        //Bind Event Location auto-complete list
        ArrayAdapter<CharSequence> teamAutoCompleteAdapter =
                ArrayAdapter.createFromResource(this, R.array.data_newevent_teams,
                        android.R.layout.simple_spinner_item);
        teamAutoCompleteAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        homeTeamEntry.setAdapter(teamAutoCompleteAdapter);
        awayTeamEntry.setAdapter(teamAutoCompleteAdapter);

        //Bind More....



    }

    private void loadEventInView()
    {

        homeTeamEntry.setText(sportEvent.teamHome);
        awayTeamEntry.setText(sportEvent.teamAway);
    }

    public void showEventDatePicker(View v)
    {
        //Todo show date picker dialog
        DatePickerFragment dateFragment = new DatePickerFragment();
        dateFragment.setDate(sportEvent.startDate);
        dateFragment.setCallBack(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth, 0, 0);
                sportEvent.startDate = new Date(c.getTimeInMillis());
                eventDateEntry.setText(String.format("%02d / %02d / %d",dayOfMonth, (monthOfYear+1), year));
            }
        });
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    public void showEventTimePicker(View v)
    {
        //Todo show time picker dialog
        TimePickerFragment timeFragment = new TimePickerFragment();
        timeFragment.setTime(sportEvent.startTime);
        timeFragment.setCallBack(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                sportEvent.startTime = new Time(c.getTimeInMillis());
                eventTimeEntry.setText(String.format("%02d : %02d   GMT",hourOfDay, minute));
            }
        });
        timeFragment.show(getFragmentManager(), "timePicker");
    }


    private boolean validateData()
    {
        sportEvent.sportName = sportsTypeSelector.getSelectedItem().toString();
        sportEvent.location = locationEntry.getText().toString();
        sportEvent.teamHome = homeTeamEntry.getText().toString();
        sportEvent.teamAway = awayTeamEntry.getText().toString();

        return true;
    }

    private void showDataEntered()
    {
        new AlertDialog.Builder(this).setTitle("Details Entered").setMessage(
                 sportEvent.sportName + ":\n"
                        + sportEvent.teamHome + " vs " +sportEvent.teamAway + "\n\t"
                        + eventDateEntry.getText() + "  " + eventTimeEntry.getText() + "\n\t\t"
                        + sportEvent.location + "\n")

                .setNeutralButton("Back",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing - it will just close when clicked
                            }
                        }).show();

    }

    private void showFailedToAddEvent()
    {
        new AlertDialog.Builder(this).setTitle("Details Entered")
                .setMessage("Could NOT save Event")
                .setNeutralButton("Back",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing - it will just close when clicked
                            }
                        }).show();

    }

    private boolean saveData()
    {
        return this.repo.AddEvent(sportEvent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_save) {
            if(validateData()) {
                if(saveData())
                    showDataEntered();
                else
                    showFailedToAddEvent();
            }
            else{
                //Todo : Show validation message in Toast
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
