# Prerequisites

To effectively use this book, you should have a strong understanding of object-oriented programming concepts, specifically in Java. However, having a solid grasp of Kotlin, including classes and objects, interfaces, listeners, packages, inner classes, object expressions, and generic classes, is preferred.

If these topics are not familiar to you, it is recommended to start with an introductory Kotlin book before proceeding with this book. This will ensure a smoother understanding of the content as you progress. It is also recommended to have a Kotlin reference on hand for additional support as you read through the book.

## Kotlin or Java?

In 2017, Google officially announced support for Kotlin in Android development, after a rising number of developers had already been utilizing it unofficially. Today, Kotlin is the favored language for most Android developers, although Java is still used. For this course, Java will be used while introducing the use of Kotlin from time to time. However, it's always an advantage to have knowledge of Kotlin if one plans to be a serious Android application developer, particularly due to its unique features like Jetpack Compose that are only available with Kotlin. Despite the original Java implementation of the Android framework and many Android classes, Kotlin's interoperability with Java ensures a smooth transition for developers. As the Android platform evolves, Kotlin is becoming increasingly vital to its future, and both Google and the Android developer community are investing in making Kotlin development easier and more valuable for Android. Join this growing community and reap the benefits of Kotlin in Android development.

For those curious to know more about Kotlin, you can visit:
- Kotlin official website: https://kotlinlang.org/
- Kotlin documentation: https://kotlinlang.org/docs/reference/
- Kotlin on Android (Google's official page): https://developer.android.com/kotlin/first

## Getting Started: Required Tools

This book requires the use of Android Studio for its examples and exercises. Android Studio is an integrated development environment designed for Android development and built on the IntelliJ IDEA platform.

When you install Android Studio, you will have access to the following tools:
- The latest version of the Android SDK
- Android SDK tools and platform tools for debugging and testing your apps
- A system image for the Android emulator to test your apps on different virtual devices

Please note that Android Studio is continuously evolving, so there may be differences between your version and what is displayed in this book.

## Installing Android Studio

To start developing Android apps, you will need to download and install Android Studio. You can find it on the Android Developer website at [developer.android.com/studio](developer.android.com/studio). This software provides all the necessary tools and resources to build and run Android applications. It comes with a built-in Java Development Kit, making it easier to get started.

It is important to note that the latest version of the Android Gradle plugin, which is used for building and compiling Android apps, requires Java 11. However, newer versions of the Java Development Kit should also work. For any additional information or help, you can refer back to the Android Developer website at [developer.android.com/studio](developer.android.com/studio).

To install Android Studio in China, users can follow these steps:
1. Download the Android Studio installation package from a reliable source, such as a mirror site or a local software repository.
2. Install Android Studio following the on-screen instructions.
3. If you encounter any issues during the installation process, consult the Android Studio documentation or reach out to the Android Studio community for support.

Note: It's important to make sure that the source of the Android Studio installation package is trustworthy and free of malware or other security threats.

## Android Emulator

The Android emulator is a virtual device that runs on your computer, allowing you to test and debug your Android applications without the need for a physical device. With the Android emulator, you will have the ability to test your applications in various scenarios and configurations, making the development process easier and more efficient.
In order to work withthe Android Emulator, you will need to download and install the necessary tools, including Android Studio and the Android SDK. Once these tools are installed, you can then create and run virtual devices, which allow you to simulate different scenarios and configurations.

## Using a Physical Android Device

While the emulator is useful for testing purposes, it cannot accurately measure the performance of your app like a physical Android device can. For the best experience, it is recommended to use a physical Android device during certain parts of this book. The benefits and utilization of physical devices will be discussed further in the book.


# Your First Android Application
Welcome to the world of Android Development! This first chapter is a great starting point for learning the new concepts and moving parts involved in building an Android application. Do not worry if you do not fully understand everything by the end of this chapter, as we will be revisiting these ideas in greater detail as we progress through the book. 

The application we will be creating is called GlobeTrotter Quiz. This application will test your knowledge of geography. The user will be prompted to press either TRUE or FALSE to answer a question displayed on the screen, and GlobeTrotter Quiz will provide instant feedback. Get ready to dive in and start building your first Android application!

| Question | Answer |
| --- | --- |
| Is Australia a continent? | Yes |
| Does Russia share a border with Kazakhstan? | Yes |
| Is the Equator located in the Northern Hemisphere? | No |
| Is the highest mountain in the world located in the Himalayas? | Yes |
| Does the Amazon River flow through Brazil? | Yes |
| Is the Red Sea located between Africa and Asia? | Yes |
| Is the capital of France Paris? | Yes |
| Does the River Nile flow into the Mediterranean Sea? | Yes |
| Is the Great Barrier Reef located off the coast of Australia? | Yes |
| Is the Arctic Ocean located in the Southern Hemisphere? | No |
| Is the Dead Sea located between Jordan and Israel? | Yes |
| Does the Amazon Rainforest span over multiple countries? | Yes |
| Is Mount Everest the highest mountain in the world? | Yes |
| Is the Sahara Desert located in North Africa? | Yes |
| Is the largest ocean in the world the Pacific Ocean? | Yes |




