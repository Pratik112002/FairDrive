# FairDrive - Ride Booking App

FairDrive is an Android ride-booking application that allows users to search for locations, book rides, and track ride history using Firebase Firestore and Google Maps API. The app also features real-time location updates, secure Firebase Authentication, and distance calculations between current and destination locations.

## Features

- **Location Search:** Search and display locations using Google Maps API.
- **Ride Booking:** Book a ride by providing the destination, and the current location is tracked automatically.
- **Distance Calculation:** Calculates the distance between the current location and the destination.
- **Firebase Integration:**
  - Firebase Authentication for secure login.
  - Firebase Firestore for saving ride history and user data.
- **Ride History:** View previously booked rides including details such as time, destination, and distance.
- **Google Maps Integration:** Displays routes and locations on an interactive map.

## Tech Stack

- **Kotlin** for Android development.
- **Google Maps API** for location tracking and mapping.
- **Firebase Firestore** for cloud data storage (ride history, user information).
- **Firebase Authentication** for secure user login.
- **Coroutines** for background tasks like geolocation and Firestore data fetching.


