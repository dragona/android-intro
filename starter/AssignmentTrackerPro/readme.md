# Technical Report: Assignment Tracker Pro

The `Assignment Tracker Pro` is an Android application that allows students and teachers to track and monitor assignments and reports. The application makes use of the `Volley` library to send and receive data from the server, and `RecyclerView` and `CardView` to display the data in a user-friendly interface.

## Functionality

The application provides the following functionality:

* The ability to select an assignment from a spinner.
* The ability to filter the reports by status.
* The ability to pull-to-refresh the list of reports.
* The ability to view the last update date and time.
* The ability to handle network connectivity issues and display an error message.
* The ability to display the reports in a list format with details such as name, ID, size, and status.

## Architecture

The application architecture is based on the Model-View-Controller (MVC) design pattern.

* Model: `Report` class is the data model that stores information about a report.
* View: `ActivityAssignReport` class is the view that displays the data to the user.
* Controller: `ApiManager` class is the controller that communicates with the server and retrieves the data.

## Libraries

The application uses the following libraries:

* `Volley`: to make HTTP requests to the server and receive responses.
* `RecyclerView`: to display the list of reports in a user-friendly format.
* `CardView`: to display each report in a card-like format.
* `SwipeRefreshLayout`: to allow users to pull-to-refresh the list of reports.
* `GSON`: to parse JSON data received from the server.

## Conclusion

The `Assignment Tracker Pro` is a useful application for students and teachers to track and monitor assignments and reports. The application makes use of various Android libraries to provide a user-friendly interface and handle network connectivity issues.
