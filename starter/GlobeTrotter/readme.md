# GlobeTrotter Quiz App

The GlobeTrotter Quiz app is a simple quiz application that presents users with questions and evaluates their answers. 

## The app has the following UI elements:

* An EditText for entering new tasks
* A ListView for displaying the tasks
* A Button for adding new tasks

## Features
The MainActivity contains the following features:

* Retrieving and parsing user input for answers.
* Displaying questions using an array of Question objects.
* Updating the TextView to show the current question.
* Starting a timer of 10 seconds for each question.
* Evaluating the user's answers and updating the score accordingly.
* Displaying a toast message indicating if the answer is correct or incorrect.
* Disabling the ability to answer the question when the time is up.
* Moving on to the next question after a correct/incorrect answer is given.
* Displaying the final score when all questions have been answered.

The user can answer a question by clicking the True or False button. 
The answer will be evaluated and the score will be updated accordingly. 
If the user runs out of time before answering the question, a toast message will be displayed indicating the time is up, and the question will be skipped.

This app is a great example of how to build a simple quiz application and how to use basic UI elements such as TextView, Buttons, and Toasts in an Android app.
