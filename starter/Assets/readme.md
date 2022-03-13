# Assets

The Assets folder is designed to store and allow provide access to raw files. It is packaged with
the apk, thus we need to create the ```assets``` folder and add the required files. In this example,
we have added the following files within the ```assets```

```
|-- assets
|       |   |   |-- data.txt
|       |   |   |-- files
|       |   |   |   |-- file1.txt
|       |   |   |   `-- file2.txt
|       |   |   `-- img
|       |   |       `-- feilong.jpeg
```

The *.txt files we have added contain some text inside, our first goal in this demo is to open these
files, read the content, and display them in a text view.

Thus, our first approach is to create a function that requires a text file name (with a complete path)
and returns its text content.

```java

private String readTextFileContentFromAssets(String fileName){
        BufferedReader bufferedReader;
        try{
        bufferedReader=new BufferedReader(new InputStreamReader(getAssets().open(fileName)));
        StringBuilder stringBuilder=new StringBuilder();
        String temp;
        while((temp=bufferedReader.readLine())!=null){
        stringBuilder.append(temp);
        }
        return"Demo "+stringBuilder.toString();
        }catch(IOException e){
        Log.e(TAG_LOG,"Get file from assets, readTextFileContentFromAssets() : "+e.toString());
        return null;
        }
        }
```


The function is ready, we can use it now.

```java
        /*Read some text files from the assets folder and display the content in a textView*/
        String textContent = readTextFileContentFromAssets("files/file1.txt");
        ((TextView) findViewById(R.id.tvContent1)).setText(textContent);
        Log.d(TAG_LOG, "accessAssetsOne():" + textContent);

        ((TextView) findViewById(R.id.tvContent2)).setText(readTextFileContentFromAssets("data.txt"));
        ((TextView) findViewById(R.id.tvContent3)).setText(readTextFileContentFromAssets("files/file2.txt"));
```

For the image file, we need to create another function.

```java

    /**
     * Opens the image feilong.jpeg from the assets and use it in a view
     */
    private void readImageFromAssets() {
        InputStream inputStream;
        try {
            inputStream = getAssets().open("img/feilong.jpeg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```

Not always needed, but if you wish to, you copy the content of the assets into the phone's internal
memory. Thus, making it available to other applications as well.

As we are using WRITE_EXTERNAL_STORAGE, it is vital to handle the cases where we may want to target
Android 11 and +. As the Android documentation specifies WRITE_EXTERNAL_STORAGE no longer provides
write access when targeting Android 11+, even when using requestLegacyExternalStorage

Following the abo,ve we need to add the following to the AndroidManifest file

```xml

<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

and

```xml

<application android:requestLegacyExternalStorage="true"...
```

