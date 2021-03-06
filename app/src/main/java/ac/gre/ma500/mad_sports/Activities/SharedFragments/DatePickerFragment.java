package ac.gre.ma500.mad_sports.Activities.SharedFragments;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.sql.Date;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    OnDateSetListener onDateSet;

    public int reference;
    public DialogCancelledHandler cancelledHandler;

    public DatePickerFragment() {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    public void setCallBack(OnDateSetListener ondate) {
        onDateSet = ondate;
    }

    private int year, month, day;

    public void setDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH);
        this.day = c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog d = new DatePickerDialog(getActivity(), onDateSet, year, month, day);
        d.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Any Date", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (cancelledHandler != null)
                    cancelledHandler.onDialogCancelled(reference);
            }
        });
        return d;
    }


}
