package mg.x261.happybirthday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var mMenu: Menu
    lateinit var userInput: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_form)
        userInput = findViewById(R.id.editTextTextPersonName)
    }

    /*
    * Update the function btnCreateCard:
    *  - when the user input from userInput is not empty,
    *    and the button Create is pressed, the  layout activity_main should be used
    *  - the action_save (menu save icon) should be visible
    *  - update the textview that holds the greeting message on the card by using the string returned by the function  greetingsBuilder
    */
    fun btnCreateCard(view: View) {}

    /**
     * Inflate the menu save icon which is used for generating the screenshot
     * However, the menu save icon should only be visible when the layout activity_main is used
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    /**
     * Handle action bar item clicks here.
     * override the onOptionsItemSelected to handle the save icon clicks
     * - Show a Toast with the message "Saving your card!" when the save icon is pressed.
     * - Use the HelperScreenShot class to generate a screen shot of the outer most
     *   layout of the layout activity_main
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    /**
     * Update the function greetingsBuilder
     * - so that when the name length is greater than 4,
     *   it should take a new line.
     * - The return should be a string that is composed of the
     *   greeting message and the name formatted as described above.
     * - The first char of the name should be in uppercase and the rest in lowercase.
     *   e.g. "Happy Birthday Demo"
     */
    private fun greetingsBuilder(name: String): String {
        return name
    }
}
