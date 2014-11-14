package ac.gre.ma500.mad_sports.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ac.gre.ma500.mad_sports.Activities.CustomAdapters.EventList_Adapter;
import ac.gre.ma500.mad_sports.R;
import ac.gre.ma500.mad_sports.models.AppDbDefination;
import ac.gre.ma500.mad_sports.models.AppRepository;
import ac.gre.ma500.mad_sports.models.SportEvent;

//import android.widget

public class MainEventListActivity extends Activity implements ActionMode.Callback,
        AbsListView.MultiChoiceModeListener, ActionBar.OnNavigationListener{


    //PROPERTIES-----------------------------------

    private AppRepository repo;
    ArrayList<Integer> selectedEvents;
    private void clearSelection()
    {
        if(selectedEvents == null)
            selectedEvents = new ArrayList<Integer>();
        else
            selectedEvents.clear();
    }
    public int currentFilterIndex;
    //End PROPERTIES-------------------------------

    //VIEWS----------------------------------------
    ActionBar actionBar;
    ListView eventListView;
    EventList_Adapter eventListAdapter;
    //DrawerLayout drawer;
    //End VIEWS------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event_list);

        repo = new AppRepository(this);
        repo.LoadData();

        clearSelection();
        this.findViews();
        this.bindViews();

    }

    private void findViews()
    {
        eventListView = (ListView)findViewById(R.id.listView_events);
        // Set up the action bar to show a dropdown list. final
        actionBar = getActionBar();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private void bindViews()
    {
        //Bind Sports Type Spinner
        eventListAdapter = new EventList_Adapter(
                this,  repo.getSportEvents(), selectedEvents);
        eventListView.setAdapter(eventListAdapter);
        eventListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        eventListView.setMultiChoiceModeListener(this);



        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ArrayAdapter<String> actionBarFilterAdapter = new ArrayAdapter<String>(getBaseContext(),
                R.layout.view_navigation_spinner_list_item, R.id.navigation_title, AppDbDefination.SPORTS_TYPE_FILTER);

        actionBar.setListNavigationCallbacks(actionBarFilterAdapter, this);



        //Bind More....

    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        this.currentFilterIndex = itemPosition;
        reloadDataFromFilter();
        return true;
    }

    private void reloadDataFromFilter() {
        if(currentFilterIndex == 0)
            repo.LoadData();
        else
            repo.loadEventsWithSportsNameFilter(new String[]{
                    AppDbDefination.SPORTS_TYPE_FILTER[currentFilterIndex]});

        eventListAdapter.notifyDataSetChanged();
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





    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position,
                                          long id, boolean checked) {
        // Here you can do something when items are selected/de-selected,
        // such as update the title in the CAB
        SportEvent.inSelectMode = !SportEvent.inSelectMode;
        if(selectedEvents.contains(position))
            selectedEvents.remove(position);
        else
            selectedEvents.add(position);
        eventListAdapter.notifyDataSetChanged();

    }


    private ActionMode mode;
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        // Respond to clicks on the actions in the CAB
        this.mode = mode;
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteSelectedItems();
                return true;
            case R.id.action_upload:
                uploadSelectedItems();
                mode.finish(); // Action picked, so close the CAB
                return true;
            default:
                return false;
        }
    }


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate the menu for the CAB
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.ctx_menu_event_list, menu);
        //getWindow().setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
        clearSelection();
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        // Here you can make any necessary updates to the activity when
        // the CAB is removed. By default, selected items are deselected/unchecked.
        SportEvent.inSelectMode = false;
        eventListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // Here you can perform updates to the CAB due to
        // an invalidate() request
        return false;
    }


    private void deleteSelectedItems() {
        //Delete Selected Items
        if(selectedEvents.size()>0) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Events")
                    .setPositiveButton(R.string.alert_response_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            repo.DeleteEventsAtIndexes(selectedEvents);
                            clearSelection();
                            reloadDataFromFilter();

                            SportEvent.inSelectMode = false;
                            eventListAdapter.notifyDataSetChanged();

                            mode.finish(); // Action picked, so closing the CAB

                        }
                    })
                    .setNegativeButton(R.string.alert_response_no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("No Selection")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    private void uploadSelectedItems() {
        SportEvent.inSelectMode = false;
        eventListAdapter.notifyDataSetChanged();
    }
}
