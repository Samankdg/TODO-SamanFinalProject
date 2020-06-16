# TODO-SamanFinalProject

**Android Studio Version 3.6.0**

# To Do List

This app uses the Android Architecture Component libraries (`Room`, `ViewModel`, `LiveData` and `LifeCycle`), 
a RecyclerView and Java. The data will be stored in an SQLite database and supports insert, read, update and delete 
operations.Together, this whole structure constitues an `MVVM (Model-View-ViewModel)` architecture, which follows 
the single responsibility and separation of concerns principles.

# Features

<table>
<thead>
<tr>
<th align="center">SplashScreen</th>
<th align="center">SignUp</th>
<th align="center">SignIn</th>
<th align="left">Add Task</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center"> <img src = "gif/splashscreen.gif" width="200" height="360"></td>
<td align="center"><img src = "gif/signup.gif" width="200" height="360"></td>
<td align="center"> <img src = "gif/signin.gif" width="200" height="360"></td>
<td align="left"><img src = "gif/addtask.gif" width="200" height="360"></td>
</tr>
</tbody>
</table>

<table>
<thead>
<tr>
<th align="center">Edit Task</th>
<th align="center">Swipe To Delete</th>
<th align="center">Search Task</th>
<th align="left">Calendar</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center"><img src = "gif/edittask.gif" width="200" height="360"></td>
<td align="center"><img src = "gif/swipedelete.gif" width="200" height="360"></td>
<td align="center"><img src = "gif/search.gif" width="200" height="360"></td>
<td align="left"><img src = "gif/calendar.gif" width="200" height="360"></td>
</tr>
</tbody>
</table>

<table>
<thead>
<tr>
<th align="center">Delete All Task</th>
<th align="center">Logout</th>
<th align="center">Exit</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center"><img src = "gif/deletealltasks.gif" width="200" height="360"></td>
<td align="center"><img src = "gif/logout.gif" width="200" height="360"></td>
<td align="center"><img src = "gif/exit.gif" width="200" height="360"></td>
</tr>
</tbody>
</table>

## Why Android Architecture Component ?
Android architecture components are a collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.

* Manage your app's lifecycle with ease. New `lifecycle-aware components` help you manage your activity and fragment lifecycles. Survive configuration changes, avoid memory leaks and easily load data into your UI.
* Use `LiveData` to build data objects that notify views when the underlying database changes.
* `ViewModel` Stores UI-related data that isn't destroyed on app rotations.
* `Room` is an a SQLite object mapping library. Use it to Avoid boilerplate code and easily convert SQLite table data to Java objects. Room provides compile time checks of SQLite statements and can return RxJava, Flowable and LiveData observables.

## How Android Architecture Components work?

<img src ="gif/android_components.png">

**Entity**: When working with Architecture Components, this is an annotated class that describes a database table.

**SQLite database**: On the device, data is stored in an SQLite database. For simplicity, additional storage options, such as a web server, are omitted. The Room persistence library creates and maintains this database for you.

**DAO**: Data access object. A mapping of SQL queries to functions. You used to have to define these painstakingly in your SQLiteOpenHelper class. When you use a DAO, you call the methods, and Room takes care of the rest.

**Room database**: Database layer on top of SQLite database that takes care of mundane tasks that you used to handle with an SQLiteOpenHelper. Database holder that serves as an access point to the underlying SQLite database. The Room database uses the DAO to issue queries to the SQLite database.

**Repository**: A class that you create, for example using the WordRepository class. You use the Repository for managing multiple data sources.

**ViewModel**: Provides data to the UI. Acts as a communication center between the Repository and the UI. Hides where the data originates from the UI. ViewModel instances survive configuration changes.

**LiveData**: A data holder class that can be observed. Always holds/caches latest version of data. Notifies its observers when the data has changed. LiveData is lifecycle aware. UI components just observe relevant data and don't stop or resume observation. LiveData automatically manages all of this since it's aware of the relevant lifecycle status changes while observing.

