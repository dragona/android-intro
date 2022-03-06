package mg.x261.activities

import android.R.attr.button
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class EndGame : AppCompatActivity() {

    val KEY_EXTRA_MESSAGE = BuildConfig.APPLICATION_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extra = intent.getBooleanExtra(KEY_EXTRA_MESSAGE, false)
        if (extra) {
            setContentView(R.layout.activity_congrats)
            findViewById<Button>(R.id.button_next_match).setOnClickListener {
                restartGame()
            }

        } else {
            setContentView(R.layout.activity_try)
            // animation
            val button: ImageButton = findViewById(R.id.btn_try_again)
            val deg: Float = button.rotation + 360f
            button.animate().rotation(deg).interpolator = AccelerateDecelerateInterpolator()

            // handling the imageButton click
            findViewById<ImageButton>(R.id.btn_try_again).setOnClickListener {
                restartGame()
            }
        }
    }
    private fun restartGame() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    fun btnRestart(view: View) {
        restartGame()
    }


}



