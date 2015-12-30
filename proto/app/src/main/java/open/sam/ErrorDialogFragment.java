package open.sam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ErrorDialogFragment extends DialogFragment {
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";

    public Dialog onCreateDialog(Bundle savedInstance) {
        Bundle arguments = getArguments();
        String title = arguments.getString(TITLE);
        String message = arguments.getString(MESSAGE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do nothing
                    }
                });
        return builder.create();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(this, tag);
        transaction.commitAllowingStateLoss();
    }
}