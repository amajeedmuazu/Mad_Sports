package ac.gre.ma500.mad_sports.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ac.gre.ma500.mad_sports.Activities.CustomAdapters.EventList_Adapter;
import ac.gre.ma500.mad_sports.R;
import ac.gre.ma500.mad_sports.models.AppDbDefination;
import ac.gre.ma500.mad_sports.models.AppRepository;
import ac.gre.ma500.mad_sports.models.SportEvent;

public class EventListActivity extends Activity {


    //PROPERTIES-----------------------------------

    private AppRepository repo;
    ArrayList<Integer> selectedEvents;
    private void clearSelection()
    {
        selectedEvents = new ArrayList<Integer>();
    }
    //End PROPERTIES-------------------------------

    //VIEWS----------------------------------------
    ActionBar actionBar;
    ListView eventListView;
    EventList_Adapter eventListAdapter;
    //End VIEWS------------------------------------

    private void findViews()
    {
        eventListView = (ListView)findViewById(R.id.listView_events);
        // Set up the action bar to show a dropdown list. final
        actionBar = getActionBar();

    }

    private void bindViews()
    {
        //Bind Sports Type Spinner
        eventListAdapter = new EventList_Adapter(
                this,  repo.getSportEvents());
        eventListView.setAdapter(eventListAdapter);

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ArrayAdapter<String> actionBarFilterAdapter = new ArrayAdapter<String>(getBaseContext(),
                R.layout.navigation_spinner_layout,R.id.navigation_title, AppDbDefination.SPORTS_TYPE_FILTER);

        ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
               if(itemPosition == 0)
                    repo.LoadData();
                else
                   repo.loadEventsWithSportsNameFilter(new String[]{
                           AppDbDefination.SPORTS_TYPE_FILTER[itemPosition]});

                eventListAdapter.notifyDataSetChanged();
                return true;
            }
        };
        actionBar.setListNavigationCallbacks(actionBarFilterAdapter, navigationListener);


        //Bind More....

    }


    public void selectEvent(View v)
    {
        Integer id = (Integer)v.getTag();
        if(selectedEvents.contains(id))
            selectedEvents.remove(id);
        else
            selectedEvents.add(id);
        //SportEvent sp = (SportEvent)v.getTag();
        //sp.isSelected = !sp.isSelected;
        //eventListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        repo = new AppRepository(this);
        repo.LoadData();

        clearSelection();
        this.findViews();
        this.bindViews();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_event_list, menu);
        //menu.add("New Event");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(item.getTitle().toString().equalsIgnoreCase("Add Event"))
        {
            Intent intent = new Intent(this, NewEventActivity.class);

            startActivityForResult(intent,1);
            return true;
        }

        else if(item.getTitle().toString().equalsIgnoreCase("Search"))
        {
           SportEvent.inSelectMode = !SportEvent.inSelectMode;
            eventListAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.repo.LoadData();
        eventListAdapter.notifyDataSetChanged();
    }
}
