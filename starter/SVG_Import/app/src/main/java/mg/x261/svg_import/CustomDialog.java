package mg.x261.svg_import;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

public class CustomDialog {
    Activity mActivity;
    Dialog mDialog;

    CustomDialog(Activity activity) {
        this.mActivity = activity;
        this.mDialog = new Dialog(activity);
    }

    public void showDialog() {

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(false);
        mDialog.setContentView(R.layout.custom_dialog);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.show();
    }

    public void closeDialog() {
        mDialog.dismiss();
    }


}