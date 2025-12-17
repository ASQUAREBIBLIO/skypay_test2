## Skypay - Hotel Reservation System
---
### Requirements
● A User can book a room for a specific period if he has enoughvbalance for the specified period and the room is free on that period. If the booking is successful, the user balance is updated.

● The function setRoom(...) should not impact the previously created bookings.

● The function setRoom(...) creates a room if it does not already exist.

● The function setUser(...) creates a user if it does not already exist.

● The function printAll(...) should print all rooms data and bookings data both from the latest created to the oldest created. The booking data should contain all the information about the room and user when the booking was done.

● The function printAllUsers(...) prints all user data from the latest created to the oldest created.

● Do not use repositories, update the ArrayLists.

● In Date checkIn, Date checkOut, consider only the year, month and day.

● Handle Exceptions whenever needed (invalid inputs,...).

### Simplified Class Digram
<img src="https://github.com/ASQUAREBIBLIO/skypay_test2/blob/main/class_diagram.png" />

### Test result
<img src="https://github.com/ASQUAREBIBLIO/skypay_test2/blob/main/resultat.png" />

### Question 1
It's not recommended to put all the fuctions inside one single service, as this violates one of *SOLID* principales, the Single Responsibility Principal (SPR). This way, we can ensure that each class should have one reason to change.

### Question 2
The issue here comes whenever we want to change a room price, because it would affect previous bookings. So instead, we can store room price with the booking instance, it can be usefull for the case of one room of the same type could have 2 different prices.
