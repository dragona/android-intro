package mg.x261.activities

import android.content.Intent
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity


class Question : AppCompatActivity() {

    val KEY_EXTRA_MESSAGE = BuildConfig.APPLICATION_ID
    private var CORRECT: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_question_one)
        val button: Button = findViewById(R.id.btn_submit)
        CORRECT = R.id.radio_green // the correct answer
        var result = false

        // Handle the changes to the radio buttons
        findViewById<RadioGroup>(R.id.radio_group).setOnCheckedChangeListener { _, checkedId ->
            //val radio: RadioButton = findViewById(checkedId)
            button.visibility = VISIBLE
            // Check which radio button was clicked
            result = when (checkedId) {
                CORRECT -> true
                else -> false
            }
        }
        button.setOnClickListener() {
            val intent = Intent(this, EndGame::class.java)
            intent.putExtra(KEY_EXTRA_MESSAGE, result)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK) // finish all previous activities
            startActivity(intent)
        }
    }


}

