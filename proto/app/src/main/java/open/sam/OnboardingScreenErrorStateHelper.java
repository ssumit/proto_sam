package open.sam;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

public class OnboardingScreenErrorStateHelper {

    private final Context context;
    private final FragmentManager fragmentManager;

    public OnboardingScreenErrorStateHelper(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public void displayNoNetworkConnectionAvailableError() {
        String title = context.getString(R.string.not_connected_to_network_title);
        String message = context.getString(R.string.not_connected_to_network_message);

        showDialog(fragmentManager, title, message);
    }

    public void displayUnknownError() {
        String title = context.getString(R.string.unknown_error_title);
        String message = context.getString(R.string.unknown_error_message);

        showDialog(fragmentManager, title, message);
    }

    public void displayInvalidEmailAddressEntered() {
        String title = context.getString(R.string.invalid_email_title);
        String message = context.getString(R.string.invalid_email_message);

        showDialog(fragmentManager, title, message);
    }

    public void showDialog(FragmentManager fragmentManager, String title, String message) {
        ErrorDialogFragment dialog = new ErrorDialogFragment();

        Bundle arguments = new Bundle();
        arguments.putString(ErrorDialogFragment.TITLE, title);
        arguments.putString(ErrorDialogFragment.MESSAGE, message);

        dialog.setArguments(arguments);

        dialog.show(fragmentManager, title);
    }
}