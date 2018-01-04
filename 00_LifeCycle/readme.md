## Android activity life cycle

An activity serves as the entry point for an app's interaction with the user.  It is a crucial component of an Android application, and the way activities are launched and put together is a fundamental part of the platform's application model. [1](https://developer.android.com/guide/components/activities/intro-activities.html)
In a given app, most of the time, a user will have to navigate through different activities. During such interaction, the Activity instances in the app transition through different states in their lifecycle. [2](https://developer.android.com/guide/components/activities/activity-lifecycle.html)
We, as a developer can intervene during this different states using the six (6)callbacks that the Activity class provides:  onCreate(), onStart(), onResume(), onPause(), onStop(), and onDestroy().

This application shows a log output each time the main activity changes state. 

![Layouts](display/display.png)
