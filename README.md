# WAGBA MOBILE APPLICATION

A food delivery mobile application aimed at my college campus in Ain Shams University. The application has a database of restaurants located around the college campus. The meals are still dummy and not representative of the corresponding restaurants, however. I have also hosted a simple website on Firebase to manage orders.


## Demo:

...


## Development Stack:

- The mobile application frontend was mainly developed using Java
- XML was used for the user interface
- Firebase was used for user authentication.
- Firebase Realtime Database was used for the backend database. (holds restaurant, meal, and order information)
- Room Database was used as a local database. (holds cart information)
- JS and HTML used for the order management website that was later hosted on Firebase.


## Functionality:

- Each restaurant should has its own cart.
- Upon adding a meal, a new cart for the respective restaurant is created automatically if it it did not already exist.
- Upon removing all meals, the restaurant cart is deleted.
- Carts are stored locally, so deleting the application should remoe any carts previously created by the user.
- Users can only place order for two time periods which mark the two breaks of the college day: 12:00 PM and 3:00 PM.
- Delivery locations are gate 3 and 4.
- Users cannot place orders for 12:00 PM after 10:00 AM nor place orders for 3:00 PM after 1:00 PM.
- Orders can be pending, cancelled, confirmed, delivered, or in delivery.
- payment can either be paid or due. It is assumed that payments are done in cash upon delivery.


## Order Management Website: https://wagba-73673.web.app/
![image](https://user-images.githubusercontent.com/61950995/211913533-137f42ee-1f9c-4860-95f9-1cba3b4f009b.png)


## Mobile Application Test Credentials:

E-mail: test@email.com

Password: 123456


