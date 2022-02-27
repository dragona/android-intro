# Build an implicit intent

Implicit intents do not declare the class name of the component to start, but instead declare an action to perform. The action specifies the thing you want to do, such as view, edit, send, or get something.

Read more at https://developer.android.com/training/basics/intents/sending

```java
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnOpenBrowser(View view) {
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://bing.com"));
        startActivity(mIntent);
    }


    public void btnDialer(View view) {
        String phoneNumber = "12345678";
        Intent mIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        startActivity(mIntent);
    }

    public void btnSendMessage(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // The intent does not have a URI, so declare the "text/plain" MIME type
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jan@example.com"}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
        startActivity(emailIntent);

    }
```