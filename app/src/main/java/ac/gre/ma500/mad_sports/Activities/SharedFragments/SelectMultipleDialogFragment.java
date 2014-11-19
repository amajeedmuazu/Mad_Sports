package ac.gre.ma500.mad_sports.Activities.SharedFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

import ac.gre.ma500.mad_sports.R;

public class SelectMultipleDialogFragment extends DialogFragment {

    private int reference;
    private String title;

    private String[] dataList;
    private ArrayList<String> selectedItems;
    private boolean[] isSelectedItems;


    public void setUp(int reference, String title, String[] datalist, String[] selectedValues) {
        this.reference = reference;
        this.title = title;

        this.dataList = datalist;

        this.isSelectedItems = new boolean[datalist.length];

        if (selectedValues != null) {
            this.selectedItems = new ArrayList<String>(Arrays.asList(selectedValues));
            for (int i = 0; i < datalist.length; i++) {
                isSelectedItems[i] = selectedItems.contains(datalist[i]);
            }
        }

    }

    public interface multiChoiceListDialogListener {
        public void onMultiChoiceDone(int reference, String[] arrayList);

        public void onMultiChoiceCancel(int reference);
    }

    multiChoiceListDialogListener dialogListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            dialogListener = (multiChoiceListDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement multiChoiceListDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (this.title == null)
            this.title = "Select Items";

        if (this.selectedItems == null)
            this.selectedItems = new ArrayList();

        if (this.dataList == null)
            return null;

        if (this.isSelectedItems == null)
            this.isSelectedItems = new boolean[dataList.length];


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(this.title)
                // Specify the list array
                .setMultiChoiceItems(this.dataList, isSelectedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    if (!selectedItems.contains(dataList[which]))
                                        selectedItems.add(dataList[which]);
                                } else if (selectedItems.contains(dataList[which])) {
                                    selectedItems.remove(dataList[which]);
                                }
                            }
                        })

                        // Set action buttons
                .setPositiveButton(R.string.alert_response_done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialogListener.onMultiChoiceDone(reference, selectedItems.toArray(
                                new String[selectedItems.size()]));
                    }
                })

                .setNegativeButton(R.string.alert_response_clear, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialogListener.onMultiChoiceCancel(reference);
                    }
                });

        return builder.create();
    }
}