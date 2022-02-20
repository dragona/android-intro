package mg.x261.lifefcycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ActivitySecond : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
}