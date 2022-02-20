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




    fun btnCreateCard(view: View) {
        // TODO 4: Update the function btnCreateCard:
        //          4.1 : when the user input from userInput is not empty,
        //              and the button Create is pressed, the  layout activity_main should be used
        //          4.2 : the action_save (menu save icon) should be visible
        //          4.3 : update the textview that holds the greeting message on the card by using the string returned by the function  greetingsBuilder
    }

    /**
     * Inflate the menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // TODO 1:  Inflate the menu save icon which is used for generating the screenshot
        //          1.1 : However, the menu save icon should only be visible when the layout activity_main is used
        return true
    }

    /**
     * Handle action bar item clicks here.
     */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // TODO 2:  override the onOptionsItemSelected to handle the save icon clicks
        //          2.1 : Show a Toast with the message "Saving your card!" when the save icon is pressed.
        //          2.2 : Use the HelperScreenShot class to generate a screen shot of the outer most
        //          layout of the layout activity_main

        return super.onOptionsItemSelected(item)
    }




    private fun greetingsBuilder(name: String): String {
        // TODO 3: Update the function greetingsBuilder
        //         so that when the name length is greater than 4,
        //         it should take a new line.
        //         3.1 : The return should be a string that is composed of the
        //         greeting message and the name formatted as described above.
        //         3.2 : The first char of the name should be in uppercase and the rest in lowercase.
        //         e.g. "Happy Birthday Demo"
        return name
    }


}