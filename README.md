# Smart Grocery Android App

This is a simple grocery tracking app I built using **Jetpack Compose**, as part of my Mobile Computing course. It's mainly designed for students or anyone trying to keep an eye on their grocery budget. I personally got tired of forgetting what I bought, where I shopped, or how much I spent — so I made an app for it. 

[Watch my demo please :)](https://drive.google.com/file/d/1E0V9gYsV1hzfVOPh7RA22szyGfiXqtqw/view?usp=sharing)

---
### Features

* Create grocery lists 
* Keep a history of what you actually bought in stores
* Enter prices for each product and calculate the total
* Select the store you shopped at using a map
* Take a photo of your receipt (or choose one from your gallery)
* View all your past shoppings, receipts included

---
### How It’s Built

I followed the **MVVM** pattern and used **Room** for local storage. The app is split into clean layers:

* **Model** – Room entities and DAOs (`GroceryList`, `Product`, `ShoppingHistory`)
* **ViewModel** – Handles all the business logic and talks to the database
* **UI** – Jetpack Compose all the way. Super modular and reactive.

The app updates automatically when the data changes thanks to `Flow` + `collectAsState`. I also used coroutines so nothing blocks the main thread.

---
### Project Structure

```
data.local/
  ├─ dao/
  ├─ database/
  └─ entities/

ui/
  ├─ components/
  ├─ constants/
  ├─ navigation/
  └─ screens/

viewmodel/
```

---
### Cool Stuff I Added

#### Store Selection via Map

When you're logging a shopping trip, you can pick the store directly from a Google Map. I fetch nearby supermarkets using the **Overpass API**, and show them as red pins on the map. Tap one, confirm, done.

#### Receipt Photo

You can take a photo of your receipt or upload one from the gallery. I handle camera permissions and save the image internally so it stays safe and local.

---
### Tools & Libraries

* Kotlin
* Jetpack Compose
* Room Database
* Google Maps Compose
* Overpass API
* Coil (image loading)
* Coroutines + Flow

---
### Future Plans

* Add price comparisons between stores
* Show spending trends and analytics
* Optionally sync history with your email or cloud
