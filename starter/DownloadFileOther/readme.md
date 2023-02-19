# Understanding Android Download Task

The Android `DownloadTask` is a utility class that helps you to perform background operations and publish results on the UI thread. It is useful when you have a small amount of background work to do that doesn't require a separate service or thread.

In this article, we will be looking at a sample Android code that shows how to download data from the web and display the result in a text view using the `DownloadTask` class.

## Understanding the Code

The code starts with an `onCreate()` method, which sets up the UI of the app. There is a button (`mDownloadButton`), an edit text (`mUrlEditText`) where the user can input the URL of the file they want to download, and a text view (`mResultTextView`) that will display the result of the download.

```java
mDownloadButton = findViewById(R.id.download_button);
mUrlEditText = findViewById(R.id.url_edit_text);
mResultTextView = findViewById(R.id.result_text_view);
```

When the user clicks the download button (mDownloadButton), the app checks if the URL is valid, and if it is, it disables the button and starts the download using the DownloadTask class.

```java

mDownloadButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String url = mUrlEditText.getText().toString();

        if (url.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
            return;
        }

        if (url.matches("(https?)://[a-zA-Z0-9./_-]+")) {
            // URL is valid, proceed with the request
            mDownloadButton.setEnabled(false); // Disable the button
            new DownloadTask().execute(url);
        } else {
            // URL is not valid, show an error message to the user
            Toast.makeText(MainActivity.this, "Invalid URL. Please enter a valid HTTPS or HTTP URL.", Toast.LENGTH_LONG).show();
        }

    }
});

```

The DownloadTask class extends AsyncTask<String, Void, String>, which means that it takes a String as a parameter, doesn't use a progress bar, and returns a String.

```java

private class DownloadTask extends AsyncTask<String, Void, String> {
    // ...
}

```

The DownloadTask class has three methods that you need to implement: onPreExecute(), doInBackground(), and onPostExecute().

## onPreExecute()

The onPreExecute() method is called before the doInBackground() method is executed. In this method, we disable the download button.

```java
@Override
protected void onPreExecute() {
    super.onPreExecute();
    mDownloadButton.setEnabled(false);
}

```

## doInBackground()
The doInBackground() method is where the actual download takes place. This method runs in the background thread, so it doesn't block the UI thread. In this method, we create a URL object with the URL of the file we want to download, open an HttpURLConnection, set the request method to "GET", and set the user agent.

```java
protected String doInBackground(String... urls) {

        String result = "";
        String url = urls[0];
        HttpURLConnection urlConnection = null;

        try {
        URL urlObject = new URL(url);
        urlConnection = (HttpURLConnection) urlObject.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("User-Agent", USER_AGENT);
        InputStream in = urlConnection.getInputStream();
        InputStreamReader reader = new InputStreamReader(in);
        int data = reader.read();

        while (data != -1) {
        char current = (char) data;
        result += current;
        data = reader.read();
        }
        } catch (Exception e) {
        e.printStackTrace();
        result = "Failed";
        } finally {
        if (urlConnection != null) {
        urlConnection.disconnect();
        }
        }

        return result;
        }
```

## onPostExecute()
The onPostExecute() method is called after the doInBackground() method is finished. In this method, we enable the download button and display the result in the text view.

```java
@Override
protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mDownloadButton.setEnabled(true); // Enable the button
        mResultTextView.setText(s);
        }

```

## Conclusion

In this article, we've looked at a sample Android code that uses the DownloadTask class to download data from the web and display it in a text view. We saw how the DownloadTask class works and how to implement its three methods: onPreExecute(), doInBackground(), and onPostExecute(). You can use this code as a starting point for your own download tasks in your Android apps.