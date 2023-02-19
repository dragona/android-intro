# LoadImage Code Documentation

This code demonstrates how to use OpenCV and Tesseract in an Android app to extract license plate information from an image.

## Prerequisites
- OpenCV 4.6.0
- Tesseract
- Leptonica
- A device running Android 7.0 or later

## How it works
1. The user selects an image using the `selectImage` method, which opens a file picker dialog.
2. The selected image is converted to grayscale using OpenCV.
3. Gaussian blur and adaptive thresholding are applied to the grayscale image to improve the accuracy of text recognition.
4. The largest contour in the image is identified as the license plate region.
5. The license plate region is cropped and displayed in an ImageView.
6. The cropped license plate region is converted to a byte array and passed to Tesseract for text recognition.
7. The recognized license plate text is displayed to the user.

## Methods
- `onCreate()`: Initializes the activity when it is created.
- `selectImage()`: Opens the file picker dialog and allows the user to select an image.
- `onActivityResult()`: Processes the selected image and applies OpenCV and Tesseract to extract the license plate information.
- `onResume()`: Initializes OpenCV and checks if it has been loaded successfully.
- `mLoaderCallback()`: Handles the status of the OpenCV loader.

## Libraries Used
- OpenCV: Used to process the image and apply image filters.
- Tesseract: Used for optical character recognition (OCR).
- Leptonica: Used for image processing.

