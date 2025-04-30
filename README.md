#  Shopping Cart App

A simple shopping cart app built with **Kotlin** and **Jetpack Compose** using the **MVI architecture** and **Clean Architecture** principles.

---

##  Features

- Add items to the cart
- Remove items from the cart
- Update item quantity
- View total price

---

##  Architecture

The app uses **Clean Architecture** with three layers:

- **Presentation Layer**: UI with Jetpack Compose and ViewModel
- **Domain Layer**: Use cases and repository interfaces
- **Data Layer**: In-memory data source and repository implementation

---

##  MVI Pattern

- **Intent**: Add, Remove, Update, Load Cart
- **State**: Loading, Success, Error
- **ViewModel**: Handles intents and updates state with `StateFlow`

---

##  Unit Testing

Includes tests for:
- Loading cart items
- Adding/removing items
- Updating quantities

---

##  How to Run

1. Clone the project
2. Open in Android Studio
3. Run on emulator or real device
