# Android Weather Notification App

In this tutorial, we will create a simple Android app that fetches and displays weather data using a background service. 
The goal is to understand how services work and how to use them in Android applications.

## Overview

The app consists of a `MainActivity` and a `WeatherService`. The `MainActivity` displays a list of weather forecasts using a `ListView` and a custom adapter. The `WeatherService` is responsible for fetching the weather data from an API and updating the data in the `MainActivity`. The communication between the `WeatherService` and the `MainActivity` is done through a broadcast.
<details>
  <summary>Click to read more about BroadcastReceiver and Service</summary>

Before we continue, let us see what `BroadcastReceiver` and `Service` are:

### BroadcastReceiver

A `BroadcastReceiver` is an Android component that allows your application to respond to system-wide or application-specific events. These events are called broadcasts. A `BroadcastReceiver` listens for specific broadcast events and executes the code in its `onReceive()` method when those events occur.

Broadcasts can be sent by the Android system, other applications, or within your application itself. They are useful for decoupling components, as the sender and receiver don't need to know about each other's existence.

There are two types of broadcasts: ordered and unordered. Ordered broadcasts are delivered to the registered receivers in a specific order, while unordered broadcasts are sent to all registered receivers simultaneously.

Example use cases for a `BroadcastReceiver` include:

- Responding to system events, such as device boot-up, connectivity changes, or battery status changes.
- Communicating between different components of your application, like sending a message from a background service to an activity.

### Service

A `Service` is an Android component that runs in the background and performs long-running tasks without a user interface. A service can continue to run even if the user switches to another app or if the application's activity is destroyed. Services are useful for tasks that require continuous processing or monitoring, such as downloading large files, playing music, or fetching data from a server.

There are two main types of services: started and bound.

1. **Started Service**: A started service is initiated by a call to the `startService()` method. The service will continue running until it is stopped, either by calling `stopService()` or by the service itself calling `stopSelf()`. A started service is typically used for tasks that need to run independently of other components.

2. **Bound Service**: A bound service is initiated by a call to the `bindService()` method. This type of service allows a client (usually an activity) to bind to the service and interact with it directly. A bound service is typically used for tasks that require communication between components.

Services can be run in the foreground or background. A foreground service is required to show a persistent notification to the user, making them aware that the service is running. Background services have more restrictions, especially in newer versions of Android, to prevent excessive battery usage and optimize system performance.

Example use cases for a `Service` include:

- Playing music in the background while the user interacts with other apps.
- Fetching data from a server periodically and updating the UI or storing it locally.
- Performing time-consuming tasks, such as file downloads or data processing.


A `BroadcastReceiver` and a `Service` are both important Android components that allow you to build more complex and robust applications by decoupling different parts of your app and enabling background tasks without a user interface.

</details>

## Implementation

### 1. Create the WeatherForecast model

Create a `WeatherForecast` class representing the weather data. It should contain fields for the date, temperature, and description. Make the class implement `Parcelable` so that it can be passed between the service and activity:

```java
public class WeatherForecast implements Parcelable {
    // Fields, constructor, getters, and setters

    // Parcelable implementation
}
```

### 2. Create a custom adapter

Create a custom adapter called `WeatherAdapter` that extends `ArrayAdapter<WeatherForecast>` for displaying the weather data in a ListView:

```java
public class WeatherAdapter extends ArrayAdapter<WeatherForecast> {
    // Constructor and getView method
}

```


### 3. Implement the MainActivity

- Initialize the ListView and set the custom adapter.
- Start the WeatherService as a foreground service.
- Register a BroadcastReceiver to receive updates from the WeatherService.


```java
public class MainActivity extends AppCompatActivity {
    // Fields, onCreate, onDataUpdated, and onDestroy methods
}

```

### 4. Implement the WeatherService

Implement the WeatherService as a subclass of Service:
- Start the service as a foreground service with a notification.
- Fetch the weather data periodically (e.g., every minute) using an AsyncTask.
- 
<details>
<summary>Read more</summary>

"Fetching data from a server periodically and updating the UI or storing it locally" can be implemented using either a foreground or background service, depending on your app's requirements and the frequency of the updates. If the updates are frequent and crucial for the user, a foreground service would be more appropriate. However, if the updates are less frequent and can be delayed without significantly affecting the user experience, a background service or an alternative approach can be used.

In newer versions of Android, background services have more restrictions to conserve battery life and optimize system performance. To overcome these restrictions, you can use alternatives such as:

- **WorkManager**: WorkManager is part of Android Jetpack and is designed to perform deferrable, asynchronous tasks that are expected to run even if the app exits or the device restarts. WorkManager takes care of handling constraints, such as network availability or charging status, and automatically retries tasks if they fail. It is best suited for tasks that can be deferred but must be executed eventually.
- **JobScheduler**: JobScheduler is an Android system service that schedules jobs to be executed based on various conditions, such as network connectivity or device charging state. It is available on Android 5.0 (API level 21) and above. JobScheduler is suitable for tasks that can be deferred and have specific constraints.
- **AlarmManager**: AlarmManager is an Android system service that allows you to schedule tasks to be executed at specific times or intervals, even when the device is in sleep mode. It is suitable for tasks that need to be executed at particular times or intervals but can be deferred when the device is inactive.

To decide which approach to use, consider the following:

- Use WorkManager for tasks that can be deferred but must be executed eventually, even if the app is not running or the device is restarted.
- Use JobScheduler for tasks that can be deferred and have specific constraints, such as network connectivity or device charging state (available on Android 5.0 and above).
- Use AlarmManager for tasks that need to be executed at specific times or intervals but can be deferred when the device is inactive.

Each of these alternatives has its own set of advantages and use cases. Choose the one that best fits your app's requirements and the frequency of the updates.

</details>

- When the data is fetched, send a broadcast with the updated data.

```java
public class WeatherService extends Service {
    // Fields, onStartCommand, onBind, onDestroy, fetchData, and broadcastWeatherData methods
}

```


### 5. Update the AndroidManifest.xml

Add the necessary permissions and service declaration to the AndroidManifest.xml:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

<!-- Inside the <application> tag -->
<service android:name=".WeatherService" />

```


The `WeatherService` runs in the background, periodically fetching the weather data and sending a broadcast when the data is updated. The `MainActivity` listens for this broadcast and updates the UI with the new data.

A `Service` is used to perform tasks in the background without a UI, while an `Activity` represents a single screen with a user interface. In this app, the `WeatherService` is responsible for fetching the weather data, allowing the `MainActivity` to focus on displaying the data.

The communication between the `WeatherService` and the `MainActivity` is done through a broadcast. This is a simple and effective way to decouple the components, allowing them to operate independently. The `WeatherService` sends a broadcast with the updated weather data, and the `MainActivity` listens for this broadcast and updates the UI accordingly.
