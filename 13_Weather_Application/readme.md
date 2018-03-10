# Weather application

For our first Android application, let us design a Weather application. We will first complete the application, then discuss the details later. We will learn by doing.
If we need to specify what we will be doing, here is the list:
- [Designing the application in Android Studio](#designing-the-application-in-android-studio)
- [Connecting the application to the Internet and updating the content based on the weather forecast](#connecting-the-application-to-the-internet-and-updating-the-content-based-on-the-weather-forecast)


# Designing the application in Android Studio

Here are the steps:
1. Redraw and slice needed images for the application
2. Create a new Android project and complete the core design
3. Refine the design and refactor the code

Here is the design we need to achieve.

![Weather application - Design](display/need_to_achieve_weather_app.gif)

Looking at this design, there are images which I need to re-draw: the weather conditions (windy, sunny, etc), the bell, the zigzag looking like stalactite.
First I will redraw everything using adobe illustator, this way I can have vectorized and high quality images. Next, I just need to slice the parts that I need.

![Weather app design redraw](display/re_draw.gif)

For the initial design, I only need the following 5 slices.

![Image slices](display/slices.gif)

It is now time to create a new android project with an empty Activity.

![Creating a new project with an empty Activity](display/create_a_new_project.gif)

My newly created project looks like this. 

![Newly created project](display/new_project.gif)

In order to check whether everything is working properly, I always run my application for testing as soon as I am done creating a new project. After major changes during my design and development process, I also proceed to a frequent testing by running the application.
My AVD specication is: 

- Device name: Galaxy Nexus API 23
- Height: 1280
- Width: 720
- API level: 23


I will do most of the design work in the 'activity_main.xml' which is located inside the 'res/layout' folder. Currently, below is what I have.

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="mg.studio.weather.MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</android.support.constraint.ConstraintLayout>

```

For this project, I will choose to use a combination of [LinearLayout](https://developer.android.com/guide/topics/ui/layout/linear.html) and [RelativeLayout](https://developer.android.com/guide/topics/ui/layout/relative.html) instead of this [ConstraintLayout](https://developer.android.com/training/constraint-layout/index.html) which was auto-generated. I will change it as follow.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="mg.studio.weather.MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</LinearLayout>

```

I still have the auto-generated 'TextView' inside my 'LinearLayout'. I will remove that in order to start clean. Notice the 'android:orientation="vertical"' which specifies the orientation of my 'LinearLayout'


```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="mg.studio.weather.MainActivity">

</LinearLayout>

```

I will now start modifying the Layout to reflect the required design. 

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#2495d1"
    android:orientation="vertical"
    android:weightSum="8"
    tools:context="mg.studio.weather.MainActivity">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginTop="10dp"
        android:layout_weight="1"
        android:gravity="center"
        android:text="Sunday"
        android:textAllCaps="true"
        android:textColor="#10000000"
        android:textSize="36sp"
        android:textStyle="bold" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginBottom="10dp"
        android:layout_weight="2"
        android:orientation="vertical">

        <ImageView
            android:id="@+id/imageView"
            android:layout_width="48dp"
            android:layout_height="48dp"
            android:layout_gravity="center"
            app:srcCompat="@mipmap/ic_launcher" />

        <TextView
            android:id="@+id/tv_news"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_margin="8dp"
            android:text="You have 1 appointment"
            android:textSize="10sp" />

        <Button
            android:id="@+id/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:background="@drawable/ic_launcher_background"
            android:gravity="center"
            android:paddingLeft="40dp"
            android:paddingRight="40dp"
            android:text="Go to Calendar"
            android:textColor="#50ffffff" />
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="3"
        android:orientation="vertical"
        android:weightSum="3">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="2">

            <LinearLayout
                android:id="@+id/linearLayout"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:layout_alignParentLeft="true"
                android:layout_margin="16dp"
                android:gravity="center_vertical"
                android:orientation="vertical">

                <ImageView
                    android:id="@+id/img_weather_condition"
                    android:layout_width="48dp"
                    android:layout_height="48dp"
                    android:layout_gravity="center"
                    app:srcCompat="@mipmap/ic_launcher" />

                <TextView
                    android:id="@+id/tv_location"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center"
                    android:text="Location"
                    android:textColor="@android:color/white"
                    android:textStyle="bold" />

                <TextView
                    android:id="@+id/tv_date"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="03/03/2018"
                    android:textColor="@android:color/white" />

            </LinearLayout>

            <LinearLayout
                android:id="@+id/tv_temperature"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentEnd="true"
                android:layout_alignParentRight="true"
                android:layout_centerVertical="true"
                android:layout_marginEnd="30dp"
                android:layout_marginRight="30dp"
                android:orientation="horizontal">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="18"
                    android:textColor="@android:color/white"
                    android:textSize="100sp"
                    android:textStyle="bold" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="right"
                    android:text="°C"
                    android:textColor="@android:color/white"
                    android:textSize="22sp" />
            </LinearLayout>


        </RelativeLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1">

            <ImageView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:scaleType="fitXY"
                app:srcCompat="@mipmap/ic_launcher" />
        </LinearLayout>
    </LinearLayout>


    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="2"
        android:background="@android:color/white"
        android:orientation="horizontal"
        android:weightSum="4"
        tools:layout_editor_absoluteX="8dp"
        tools:layout_editor_absoluteY="380dp">

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="center"
            android:orientation="vertical">

            <ImageView
                android:layout_width="48dp"
                android:layout_height="48dp"
                app:srcCompat="@mipmap/ic_launcher" />

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:text="mon"
                android:textAllCaps="true"
                android:textColor="#909090" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="center"
            android:orientation="vertical">

            <ImageView
                android:layout_width="48dp"
                android:layout_height="48dp"
                app:srcCompat="@mipmap/ic_launcher" />

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:text="tue"
                android:textAllCaps="true"
                android:textColor="#909090" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="center"
            android:orientation="vertical">

            <ImageView
                android:layout_width="48dp"
                android:layout_height="48dp"
                app:srcCompat="@mipmap/ic_launcher" />

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:text="thu"
                android:textAllCaps="true"
                android:textColor="#909090" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="center"
            android:orientation="vertical">

            <ImageView
                android:layout_width="48dp"
                android:layout_height="48dp"
                app:srcCompat="@mipmap/ic_launcher" />

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:text="fri"
                android:textAllCaps="true"
                android:textColor="#909090" />
        </LinearLayout>
    </LinearLayout>
</LinearLayout>

```

Here is the current look of my layout

![Current layout](display/layout_part_one.gif)

To see the fidelity of the design in my AVD, here is the output when I run the application.

![Primary design](display/design_version_01.gif)


I will copy the images I created earlier and paste them inside my drawable folder. If you are following with me, there is something I would like to note here. By default, Android Studio displays your project files in the Android view. This view does not reflect the actual file hierarchy on disk, but is organized by modules and file types to simplify navigation between key source files of your project, hiding certain files or directories that are not commonly used. ([Read more](https://developer.android.com/studio/projects/index.html))

![The drawable folder](display/drawable.gif)


To make sure, my images are placed in the folder I want it to be, I will paste them inside my drawable folder as shown below.

![Paste the images in the drawable folder](display/paste_drawables.gif)

I can use those images now, but before, let me first get rid of the toolbar which is on top of my application.
Next, I will remove the action bar, as I want the application to use the full screen. 

![Primary design](display/manifest_no_action_bar.gif)

See the impact of that small change with the image below showing the before at the left and the after at the right.

![Before and the after remove action bar](display/toolbar_none.gif)

I will now continue with the images I added to my project. I will replace all the paths currently pointing to 'app:srcCompat="@mipmap/ic_launcher' and that display the green image in my application, and use the correct path for each image to be used. 
My images are inside the drawable folder, from the layout xml file, I can easily access them at 'app:srcCompat="@drawable/image_name_without_the_extension'

The good thing is, Android Studio will help me get the correct names with the auto-complete function.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#2495d1"
    android:orientation="vertical"
    android:weightSum="8"
    tools:context="mg.studio.weather.MainActivity">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginTop="10dp"
        android:layout_weight="1"
        android:gravity="center"
        android:text="Sunday"
        android:textAllCaps="true"
        android:textColor="#10000000"
        android:textSize="36sp"
        android:textStyle="bold" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginBottom="10dp"
        android:layout_weight="2"
        android:orientation="vertical">

        <ImageView
            android:id="@+id/imageView"
            android:layout_width="48dp"
            android:layout_height="48dp"
            android:layout_gravity="center"
            app:srcCompat="@drawable/notification" />

        <TextView
            android:id="@+id/tv_news"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_margin="8dp"
            android:text="You have 1 appointment"
            android:textSize="10sp" />

        <Button
            android:id="@+id/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:background="@drawable/ic_launcher_background"
            android:gravity="center"
            android:paddingLeft="40dp"
            android:paddingRight="40dp"
            android:text="Go to Calendar"
            android:textColor="#50ffffff" />
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="3"
        android:orientation="vertical"
        android:weightSum="3">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="2">

            <LinearLayout
                android:id="@+id/linearLayout"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:layout_alignParentLeft="true"
                android:layout_margin="16dp"
                android:gravity="center_vertical"
                android:orientation="vertical">

                <ImageView
                    android:id="@+id/img_weather_condition"
                    android:layout_width="48dp"
                    android:layout_height="48dp"
                    android:layout_gravity="center"
                    app:srcCompat="@drawable/rainy_up" />

                <TextView
                    android:id="@+id/tv_location"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center"
                    android:text="Location"
                    android:textColor="@android:color/white"
                    android:textStyle="bold" />

                <TextView
                    android:id="@+id/tv_date"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="03/03/2018"
                    android:textColor="@android:color/white" />

            </LinearLayout>

            <LinearLayout
                android:id="@+id/tv_temperature"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentEnd="true"
                android:layout_alignParentRight="true"
                android:layout_centerVertical="true"
                android:layout_marginEnd="30dp"
                android:layout_marginRight="30dp"
                android:orientation="horizontal">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="18"
                    android:textColor="@android:color/white"
                    android:textSize="100sp"
                    android:textStyle="bold" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="right"
                    android:text="°C"
                    android:textColor="@android:color/white"
                    android:textSize="22sp" />
            </LinearLayout>


        </RelativeLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1">

            <ImageView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:scaleType="fitXY"
                app:srcCompat="@drawable/design" />
        </LinearLayout>
    </LinearLayout>


    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="2"
        android:background="@android:color/white"
        android:orientation="horizontal"
        android:weightSum="4"
        tools:layout_editor_absoluteX="8dp"
        tools:layout_editor_absoluteY="380dp">

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="center"
            android:orientation="vertical">

            <ImageView
                android:layout_width="48dp"
                android:layout_height="48dp"
                app:srcCompat="@drawable/rainy_small" />

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:text="mon"
                android:textAllCaps="true"
                android:textColor="#909090" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="center"
            android:orientation="vertical">

            <ImageView
                android:layout_width="48dp"
                android:layout_height="48dp"
                app:srcCompat="@drawable/partly_sunny_small" />

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:text="tue"
                android:textAllCaps="true"
                android:textColor="#909090" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="center"
            android:orientation="vertical">

            <ImageView
                android:layout_width="48dp"
                android:layout_height="48dp"
                app:srcCompat="@drawable/windy_small" />

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:text="thu"
                android:textAllCaps="true"
                android:textColor="#909090" />
        </LinearLayout>

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="center"
            android:orientation="vertical">

            <ImageView
                android:layout_width="48dp"
                android:layout_height="48dp"
                app:srcCompat="@drawable/sunny_small" />

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:text="fri"
                android:textAllCaps="true"
                android:textColor="#909090" />
        </LinearLayout>
    </LinearLayout>
</LinearLayout>


```

![After adding the images to the design](display/design_version_02.gif)

So far so good, but I still need to update my button. I could use an image for this button as well, but I know I can achieve this desired design by using shapes.

In Android, we can intercept the states of a given button: normal position or pressed. I can then specify the design to be used for each given state.
I will create 3 new files and will place them in my drawable folder. A selector and two other files for shape definitions.

- button_selector.xml
- button_shape.xml
- button_shape_pressed.xml

Here is the content of my button_selector file

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!--When the button is pressed-->
    <item android:drawable="@drawable/button_shape_pressed" android:state_pressed="true" />
    <!--Default-->
    <item android:drawable="@drawable/button_shape" />
</selector>

```

For the shape definitions:
button_shape:

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#31a7dc" />
    <corners android:radius="90dp" />
</shape>
```

button_shape_pressed:

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#2191ce" />
    <corners android:radius="90dp" />
</shape>
```

Next, I just need to use the selector (button_selector.xml) as the background of my button.

Before: 

```xml
    android:background="@drawable/ic_launcher_background"

```

After:

```xml
    android:background="@drawable/button_selector"

```

See the output difference now:

![Button before and after using the selector](display/button.gif)

The design is almost there, but there is something I would like to fix. Notice that I always design having in mind that the user will use the application in portrait mode. If I rotate the AVD while the application is running, see the result:

![In Landscapre mode, the design does not look good](display/landscape.gif)

I can fix this temporarily by setting the layout screen orientation of the activity to be in portrait mode despite any phone rotation. For that, I can update that my android manifest file.

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="mg.studio.weather">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AppCompat.NoActionBar">
        <activity android:name=".MainActivity"
            android:screenOrientation="portrait">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```
So now, even if the phone is rotated in landscape mode, the activity of the application won`t rotate and the design won`t break.

# Connecting the application to the Internet and updating the content based on the weather forecast


