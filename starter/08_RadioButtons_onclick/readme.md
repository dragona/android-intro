# Radio buttons - waiting for the button click before processing

```java
public class MainActivity extends Activity {
	RadioGroup radiogroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Connect the radioGroup
		radiogroup = (RadioGroup) findViewById(R.id.radioGroup);

		// Connect the button
		Button btn = (Button) findViewById(R.id.btn);
		// Adding a listener
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// retrieve the radio button that was selected
				int radioSelected = radiogroup.getCheckedRadioButtonId();

				// get the text associated with that radioId
				RadioButton selected = (RadioButton) findViewById(radioSelected);
				// Show a toast
				/**
				 * See the difference between the two toasts below
				 **/
				// Toast.makeText(getBaseContext(), selected+"",
				// Toast.LENGTH_SHORT).show();

				Toast.makeText(getBaseContext(), selected.getText(),
						Toast.LENGTH_SHORT).show();

			}
		});

	}

}

```