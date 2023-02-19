package mg.x261.downloadfile

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException


class MainActivity : AppCompatActivity() {


    companion object {
        private const val TAG = "MainActivity"
        lateinit var newFileVersionId: String
        var localFileVersion: Int = 0

    }

    lateinit var versionFileManager: VersionFileManager
    var versionFileId = 0   // This should be updated based on
    // the version of the file shipped with the app

    private lateinit var myTextView: TextView


    private val myExecutor = Executors.newSingleThreadExecutor()
    private val myHandler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myTextView = findViewById<TextView>(R.id.textView)

        //Get reference to our userManager class
        versionFileManager = VersionFileManager(this)

        //Get current CSV file version used by the app
        versionFileManager.currentVersionFlow.asLiveData().observe(this) {
            localFileVersion = it
            myTextView.text = "CSV version : $localFileVersion"
        }
        // Get the CSV version on the server
        getOnlineFileVersionId()


    }


    private fun getOnlineFileVersionId() {
        myExecutor.execute {
            // Download data
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://studio.mg/api-demo-android/version.txt")
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                // TODO: need a better error handling - connect timed out
                for ((name, value) in response.headers) {
                    println("$name: $value")
                }
                newFileVersionId = response.body!!.string()
                println(newFileVersionId)
            }
            //Update the UI
            myHandler.post {
                myTextView.text = "CSV versions : $localFileVersion vs. $newFileVersionId"
            }
        }
    }

    fun btnDownload(view: View) {
        compareFileVersionIds()
    }

    private fun compareFileVersionIds() {

        // Compare version local and server
        if (newFileVersionId.toInt() > localFileVersion) {
            myExecutor.execute {
                // Download the CSV
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://studio.mg/api-demo-android/sample.csv")
                    .build()
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    // TODO: need a better error handling - connect timed out

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }
                    val data = response.body!!.string()
                    println(data)
                    // TODO : Save the data as a CSV content

                    //Update the local localFileVersion - Stores the new newFileVersionId
                    GlobalScope.launch {
                        versionFileManager.storeVersion(newFileVersionId.toInt())
                    }
                }
            }
        } else {
            myTextView.text = "CSV version up to date!"
        }
    }
}

