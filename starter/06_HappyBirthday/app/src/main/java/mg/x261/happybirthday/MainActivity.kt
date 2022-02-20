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


    /**
     * Gets the name from the user input
     */
    fun btnCreateCard(view: View) {
        lateinit var userName: String
        if (userInput.text.trim().isNotEmpty()) {
            // generate the card
            setContentView(R.layout.activity_main)
            //Show the menu
            mMenu.findItem(R.id.action_save).setVisible(true)
            // Update the message on the Card
            val tvWishes: TextView = findViewById(R.id.tvTo)
            userName = userInput.text.trim().toString()
            if (false) {
                // Method 1
                tvWishes.text = baseContext.getString(R.string.txt_wishes).plus(", ")
                    .plus(userName.replaceFirstChar { it.uppercase() })
            } else {
                tvWishes.text = greetingsBuilder(userName)
            }

        }

    }

    /**
     * Inflate the menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.screenshot, menu)
        mMenu = menu
        var itemView: MenuItem = mMenu.findItem(R.id.action_save)
        itemView.isVisible = false
        return true
    }

    /**
     * Handle action bar item clicks here.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            android.widget.Toast.makeText(
                this,
                " Saving your card!",
                android.widget.Toast.LENGTH_SHORT
            )
                .show()
            val helperScreenShot = mg.x261.happybirthday.HelperScreenShot()
            helperScreenShot.process(findViewById(mg.x261.happybirthday.R.id.mainLayout), this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Format the new lines that comes with the
     * birthday wishes text
     */
    private fun greetingsBuilder(name: String): String {
        val wishes = { separator: String ->
            baseContext.getString(R.string.txt_wishes).plus(separator)
                .plus(name.trim().replaceFirstChar { it.uppercase() })
        }
        return if (name.length > 4) wishes("\n") else wishes(",")
    }


}