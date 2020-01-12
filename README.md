# Introduction

Hello future colleagues!

# About the project

It contains three gradle modules called 'list', 'detail', 'favorites' and 'data'. 'List' module is written in Java and 'detail', 'favorites' and 'data' are modules written in Kotlin.

# Notes for the Challenges
1. The Wrong State - we took care of fetching the data remotely from the api. So the app is supposed to show a list of posters, but instead, it still displays the progress bar. Can you find where the problem is and fix it? And to make sure we won't make the same mistake twice write a simple unit test.
* **DONE** - Added SearchResult.success to set liveData variable.

2. The Lost Event - when the user clicks on an item from the movies list, the app is supposed to display some information about the selected movie (this feature is located in the detail module). Currently, the app doesn't respond to clicks, can you please fix it?
* **DONE** - Added implicit intent to navigate to DetailsActivity with imdbID as a parameter
3. The Lost State - the app comes with a search bar to help users find their favorite movies. Unfortunately, there is a bug. When we rotate the screen, the app clears the text we just typed. Can you provide a solution to prevent this state loss from happening on rotation.
* **DONE** - Added on saveInstanceState text from query
4. Some refreshments - we made sure that this app handles networking errors. But we didn't implement any mechanism to reload the data, without quitting the app. Can you provide a way of refreshing the list of movies?
* **DONE** - Added SwipeRefreshLayout
5. The chosen ones - the favorites screen should show a list of the user's favorite movies. Try to implement this feature. Remember that the list of favorite movies should be available even after killing the app.
* **DONE** - Created data module to aggregate all database related classes. Created favorite layout getting from the database. Favorite items are stored in the database marking the movie with a star, in the Details screen.
6. The Shrink - first start by obfuscating the application using Proguard. Now you should have an empty details view in the app, your mission is to fix these issues. Now the apk is smaller, but we know it can be even smaller, use the apk analyzer to find out how to do so.
* **PARTIAL** - After code obfuscation, the size of the application reduced but details view was working. The apk size reduced after checking that there was a unused resource. Such resource was deleted.

### Bonus:

1. Memory leaks - * **DONE** - QueryProvider object was creating memory leak, instead I deleted and pass parameter directly in the method.
2. Java to Kotlin conversion - convert 'list' module from Java to Kotlin.
* **POSSIBLE**
3. List loading indicator - The app loads gradually the list of movies. Add a progress bar to indicate that the next page is loading.
* **UNKNOW STATE**
