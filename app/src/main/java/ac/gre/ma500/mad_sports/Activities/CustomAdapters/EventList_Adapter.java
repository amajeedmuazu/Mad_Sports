package ac.gre.ma500.mad_sports.Activities.CustomAdapters;

/**
 * Created by Majeed on 11/11/14.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ac.gre.ma500.mad_sports.R;
import ac.gre.ma500.mad_sports.models.SportEvent;

public class EventList_Adapter extends ArrayAdapter<SportEvent> {

    private final Activity context;
    private final ArrayList<SportEvent> sportEvents;

    public EventList_Adapter(Activity context, ArrayList<SportEvent> sportEvents) {
        super(context,R.layout.event_listview_item, sportEvents);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.sportEvents = sportEvents;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.event_listview_item, null,true);

        SportEvent sp = sportEvents.get(position);

        ((TextView)rowView.findViewById(R.id.listItem_eventTeamHome))
                .setText(sp.teamHome);
        ((TextView)rowView.findViewById(R.id.listItem_eventTeamAway))
                .setText(sp.teamAway);

        ((TextView)rowView.findViewById(R.id.listItem_eventTime))
                .setText(sp.startTime.toString().substring(0,5));

        //((TextView)rowView.findViewById(R.id.listItem_eventLocation))
        //        .setText(sp.location);

        int icon = 0;
        if(sp.sportName.equalsIgnoreCase("football"))
            icon = R.drawable.football;
        else if (sp.sportName.equalsIgnoreCase("Basketball"))
            icon = R.drawable.baseball;
        else if (sp.sportName.equalsIgnoreCase("Hockey"))
            icon = R.drawable.golf;
        else if (sp.sportName.equalsIgnoreCase("Handball"))
            icon = R.drawable.tennis;

        ((ImageView)rowView.findViewById(R.id.imageView_eventIcon))
                .setImageResource(icon);

        if(SportEvent.inSelectMode)
        {
            (rowView.findViewById(R.id.event_listItem_selectItemView))
                    .setVisibility(View.VISIBLE);
        }
        else
        {
            (rowView.findViewById(R.id.event_listItem_selectItemView))
                    .setVisibility(View.GONE);
        }


        //((CheckBox)rowView.findViewById(R.id.event_listItem_checkbox)).setTag(sp);
        (rowView.findViewById(R.id.event_listItem_checkbox)).setTag(sp._id);
        ((CheckBox)rowView.findViewById(R.id.event_listItem_checkbox))
                .setChecked(sp.isSelected);

        return rowView;

    };
}

