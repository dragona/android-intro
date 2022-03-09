# PregressBar

```kotlin
package mg.x261.progressbar001

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showProgress()
    }

    private fun showProgress() {
        val progressbar = findViewById<ProgressBar>(R.id.progressBar)
        val txtView = findViewById<TextView>(R.id.tvDisplay)
        val handler = Handler() // This needs to be outside the thread
        progressbar.visibility = View.VISIBLE
        var i = progressbar.progress
        Thread(Runnable {
            // run from 0 to 99
            while (i < 100) {
                i += 1
                // interact with the UI -> post handler

                handler.post(Runnable {
                    progressbar.progress = i
                    // update the UI with the current value of i
                    (i.toString() + "/" + progressbar.max).also { txtView!!.text = it }
                })
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            progressbar.visibility = View.INVISIBLE
        }).start()
    }
}
```