# TodoList App

The TodoList app is a simple to-do list application that allows users to add and remove tasks using a ListView.

## UI Elements

The app contains the following UI elements:
- An EditText for entering new tasks
- A ListView for displaying the tasks
- A Button for adding new tasks

## Features

The TodoListActivity is implemented in Kotlin and contains the following features:
- Retrieving and parsing user input for tasks.
- Displaying tasks using a ListView and ArrayAdapter.
- Adding tasks to the list and updating the adapter when the "Add Task" button is clicked.
- Removing tasks from the list and updating the adapter when the user clicks or long-presses on a task in the list.
- Displaying a toast message that shows the removed task when the user long-presses on a task.

The user can add a new task to the list by entering the task in the EditText and clicking the "Add Task" button. The task will be displayed in the ListView.

The user can remove a task from the list by clicking on it or long-pressing on it. When the user long-presses on a task, a toast message is displayed that shows the removed task.

This app is a great example for demonstrating how to build a simple to-do list application and how to use basic UI elements such as EditText, ListView, and Button in an Android app.