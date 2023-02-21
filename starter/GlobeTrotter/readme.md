# GlobeTrotter Quiz App

The GlobeTrotter Quiz app is a simple quiz application that presents users with questions and evaluates their answers. 

## The app has the following UI elements:

* LinearLayout
* TextViews 
* Buttons

## Features
The main features of this Android application are:

1. Quiz game functionality: The app presents the user with a series of true/false questions and scores the user based on their answers.
2. User interface: The app features a simple, easy-to-use interface with two buttons for true/false answers, a TextView to display the question, and a timer.
3. Timer: The app includes a countdown timer to limit the amount of time the user has to answer each question.
4. Question data: The app stores the question data as an array of Question objects, which include a question text and an answer flag.
5. Answer checking: The app checks the user's answer against the correct answer stored in the Question object, updates the score, and displays a message to indicate if the answer is correct or not.
6. Activity lifecycle: The app handles the Activity lifecycle, ensuring that the app runs smoothly and doesn't crash.
7. Code modularity: The app demonstrates good code modularity with separate methods to update the question, check the answer, start and cancel the timer, and handle the Question data.

Overall, the application serves as a basic quiz game that is simple and easy to use, with features such as a timer and score tracking to make it more engaging for the user.

The user can answer a question by clicking the True or False button. 
The answer will be evaluated and the score will be updated accordingly. 
If the user runs out of time before answering the question, a toast message will be displayed indicating the time is up, and the question will be skipped.

This app is a great example of how to build a simple quiz application and how to use basic UI elements such as TextView, Buttons, and Toasts in an Android app.
