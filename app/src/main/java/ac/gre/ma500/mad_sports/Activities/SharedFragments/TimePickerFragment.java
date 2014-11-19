package ac.gre.ma500.mad_sports.Activities.SharedFragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;


public class TimePickerFragment extends DialogFragment implements DialogInterface.OnDismissListener {

    public TimePickerFragment() {
        // Use the current time as the default values for the picker
        setTime(Calendar.getInstance().getTime());
    }

    public int reference;

    TimePickerDialog.OnTimeSetListener onTimeSet;
    public DialogCancelledHandler cancelledHandler;

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
        dialog.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Any Time", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (cancelledHandler != null)
                    cancelledHandler.onDialogCancelled(reference);
            }
        });
        return dialog;

    }

}