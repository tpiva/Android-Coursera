package thiago.miniproject.coursera.com.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by Thiago on 26/09/2015.
 */
public class AlertDialogFragment extends DialogFragment {

    private static String MOMA_URL = "http://www.moma.org/";

    public static AlertDialogFragment newInstance() {
        return new AlertDialogFragment();
    }

    // Build AlertDialog using AlertDialog.Builder
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.dialog_message_moma)

                // User cannot dismiss dialog by hitting back button
                .setCancelable(false)

                        // Set up No Button
                .setNegativeButton("Not Now",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dismiss();
                            }
                        })

                .setPositiveButton("Visit Moma",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    final DialogInterface dialog, int id) {
                                Intent implicityIntent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(MOMA_URL));
                                startActivity(implicityIntent);
                            }
                        }).create();
    }
}
