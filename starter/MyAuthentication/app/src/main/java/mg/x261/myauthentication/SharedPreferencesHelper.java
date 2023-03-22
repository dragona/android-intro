package mg.x261.myauthentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

public class SharedPreferencesHelper {

    Context mContext;
    SharedPreferences mSharedPreferences;

    public SharedPreferencesHelper(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
    }

    /**
     * Initializes the studentIdEditText and emailEditText fields with the values
     * from the SharedPreferences if they exist, and displays a message asking the
     * user to enter the student ID and email if they do not.
     *
     * @param studentIdEditText EditText field for entering the student ID
     * @param emailEditText     EditText field for entering the email
     */
    public void initEditTextsFromSharedPreferences(EditText studentIdEditText, EditText emailEditText) {
        String studentId = mSharedPreferences.getString("student_id", null);
        String email = mSharedPreferences.getString("email", null);

        if (studentId == null || email == null) {
            ToastHelper.showToast(mContext, R.string.enter_student_id_and_email);
        } else {
            studentIdEditText.setText(studentId);
            emailEditText.setText(email);
        }
    }

    /**
     * Checks whether the student ID and email exist in SharedPreferences.
     *
     * @return true if the student ID and email exist in SharedPreferences, false otherwise.
     */
    public boolean isStudentIdAndEmailExist() {
        String studentId = mSharedPreferences.getString("student_id", null);
        String email = mSharedPreferences.getString("email", null);
        return studentId != null && email != null;
    }

    /**
     * Gets the student ID from SharedPreferences.
     *
     * @return the student ID if it exists in SharedPreferences, null otherwise.
     */
    public String getStudentId() {
        return mSharedPreferences.getString("student_id", null);
    }

    /**
     * Gets the email from SharedPreferences.
     *
     * @return the email if it exists in SharedPreferences, null otherwise.
     */
    public String getEmail() {
        return mSharedPreferences.getString("email", null);
    }

    /**
     * Sets the student ID and email in SharedPreferences.
     *
     * @param studentId the student ID to be set
     * @param email     the email to be set
     */
    public void setStudentIdAndEmail(String studentId, String email) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("student_id", studentId);
        editor.putString("email", email);
        editor.apply();
    }

    /**
     * Resets the student ID and email in SharedPreferences to null.
     */
    public void resetStudentIdAndEmail() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("student_id", null);
        editor.putString("email", null);
        editor.apply();
    }

    /**
     * Checks whether the account is activated.
     *
     * @return true if the account is activated, false otherwise.
     */
    public boolean isActivated() {
        return mSharedPreferences.getBoolean("activated", false);
    }

    /**
     * Sets the account as activated in SharedPreferences.
     */
    public void setActivated() {
        setActivated(true);
    }

    /**
     * Sets the account as activated or deactivated in SharedPreferences.
     *
     * @param activated true if the account is to be activated, false if it is to be deactivated.
     */
    public void setActivated(boolean activated) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("activated", activated);
        editor.apply();
    }

}
