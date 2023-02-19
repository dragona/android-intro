package mg.x261.downloadfile

import android.content.Context
import android.widget.Toast
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class VersionFileManager(context: Context) {

    //Create the dataStore
    private val dataStore = context.createDataStore(name = "user_prefs")
    private val FILEVERSION = preferencesKey<Int>("FILE_VERSION_NUMBER")

    //Store user data
    suspend fun storeVersion(version: Int) {
        dataStore.edit {
            it[FILEVERSION] = version
        }
    }

    //Version flow
    val currentVersionFlow: Flow<Int> = dataStore.data.map {
        val fileVersion = it[FILEVERSION] ?: 0
        Toast.makeText(context, "FILE VERSION $fileVersion", Toast.LENGTH_SHORT).show()
        fileVersion
    }


}