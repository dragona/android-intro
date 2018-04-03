# Write inside the app private directory

- If you want to <b>save files on external storage</b> that are <u>private to your app</u> and <u>not accessible by the MediaStore content provider</u>, you can acquire a directory that's used by only your app by calling getExternalFilesDir() and passing it a name indicating the type of directory you'd like. Each directory created this way is added to a parent directory that encapsulates all your app's external storage files, which the system deletes when the user uninstalls your app ([read more](https://developer.android.com/training/data-storage/files.html#PrivateFiles)).
- Using the following method to write file in the external directory, you don`t need to request for permissions.
- <i>Files on external storage are not always accessible</i>, because users can mount the external storage to a computer for use as a storage device. So if you need to store files that are critical to your app's functionality, you should instead store them on [internal storage](https://developer.android.com/training/data-storage/files.html#WriteInternalStorage).
- If none of the pre-defined sub-directory names suit your files, you can instead call getExternalFilesDir() and pass null. This returns the root directory for your app's private directory on the external storage.
- Remember that getExternalFilesDir() creates a directory that is deleted when the user uninstalls your app. If the files you're saving should remain available after the user uninstalls your app—such as when your app captures photos and the user should keep those photos—you should instead save the files to a public directory.
```java
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar calendar = Calendar.getInstance();
        String timeStamp /* use as file name */ = String.valueOf(calendar.getTimeInMillis());
        String text_to_save = "Created on " + timeStamp;

        //Save the text_to_save in a file inside the private Directory of the application
        saveFileInPrivateDirectory(this, timeStamp + ".txt", text_to_save);

    }

    /**
     * @param context    the current context.
     * @param folderName represents the folder name to use.
     * @return FileSystem object representing the platform's local file system.
     */
    public File getPrivateStorageDirectory(Context context, String folderName) {

        // Get the directory for the app's private downloads directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS), folderName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }

    /**
     * Creates a file, writes in a string and saves it in the private Storage
     * directory of the application. Writing the private directory of the application
     * does not require specific permissions.
     * Location may vary but here is what I have while testing this:
     * /storage/emulated/0/Android/data/mg.studio.writefileexternalstorage/files/Download/directory
     *
     * @param context   the current context
     * @param sFileName the file name to create to save the string
     * @param sBody     string to save in the text file
     */
    public void saveFileInPrivateDirectory(Context context, String sFileName, String sBody) {

        String folderName = "directory";
        try {
            File root = getPrivateStorageDirectory(context, folderName);
            File file = new File(root, sFileName);
            FileWriter writer = new FileWriter(file);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Log.d(LOG_TAG, "Saved: " + root.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "saveFileInPrivateDirectory(): " + e);
        }
    }
}


```