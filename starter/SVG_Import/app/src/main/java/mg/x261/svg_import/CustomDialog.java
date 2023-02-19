// CustomDialog class to show a custom dialog with a given layout in the app
package mg.x261.svg_import;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

public class CustomDialog {
    // mActivity to hold reference to the calling activity
    Activity mActivity;
    // mDialog to hold the Dialog object
    Dialog mDialog;

    // constructor to initialize mActivity and mDialog
    CustomDialog(Activity activity) {
        this.mActivity = activity;
        this.mDialog = new Dialog(activity);
    }

    // function to show the custom dialog
    public void showDialog() {

        // set dialog feature to not show the title bar
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set the dialog to not cancelable
        mDialog.setCancelable(false);
        // set the layout for the dialog
        mDialog.setContentView(R.layout.custom_dialog);
        // set the background color to be transparent
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // show the dialog
        mDialog.show();
    }

    // function to close the dialog
    public void closeDialog() {
        mDialog.dismiss();
    }


}
