package ac.gre.ma500.mad_sports.Activities.SharedFragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Majeed on 02/11/14.
 */
public class TimePickerFragment extends DialogFragment {

    public TimePickerFragment()
    {
        // Use the current time as the default values for the picker
        setTime(Calendar.getInstance().getTime());
    }

    TimePickerDialog.OnTimeSetListener onTimeSet;
    public void setCallBack(TimePickerDialog.OnTimeSetListener onTime) {
        onTimeSet = onTime;
    }

    private int hour, minute;

    public void setTime(Date dateTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateTime);
        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.minute = c.get(Calendar.MINUTE);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of TimePickerDialog and return it

        TimePickerDialog dialog = new TimePickerDialog(getActivity(), onTimeSet, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
        dialog.setTitle("Set Time in GMT");

        return  dialog;

    }

}