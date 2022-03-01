package mg.x261.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

public class ViewDialog {
    Activity mActivity;
    Dialog mDialog;

    ViewDialog(Activity activity) {
        this.mActivity = activity;
        this.mDialog = new Dialog(activity);
    }

    public void showDialog() {

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(false);
        mDialog.setContentView(R.layout.custom_dialog);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        FrameLayout mDialogOk = mDialog.findViewById(R.id.frmOk);
        mDialogOk.setOnClickListener(v -> {
            Toast.makeText(mActivity, "Ok", Toast.LENGTH_SHORT).show();
            mDialog.dismiss();
        });

        mDialog.show();
    }

    public void closeDialog() {
        mDialog.dismiss();
    }


}