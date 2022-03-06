package mg.x261.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia)
    }
    fun btnPlay(view: View) {
        //TODO: go to Activity Question
        startActivity(Intent(this, Question::class.java))
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                startActivity(Intent(this, About::class.java))
                true
            }
            R.id.action_rules -> {
                startActivity(Intent(this, Rules::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}