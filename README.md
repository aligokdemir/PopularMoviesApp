## Popular Movies App

An application developed for learning purposes during Android Nanodegree Program. This application makes use of [TMDB API](https://www.themoviedb.org/documentation/api) and shows the user two categories of movies for now. 

1. Most popular movies on TMDB
2. Top rated movies on TMDB

While displaying this content, app uses [Retrofit2](http://square.github.io/retrofit/) to make request and uses "GsonConverterFactory" in order to model the data. In the opening screen of the app, you can see a Grid View applied in a Recycler View consisting of movies' posters and when clicked, it takes you to the detail activity about the movie. The movies' posters are fetched using [Picasso](http://square.github.io/picasso/) library which is a powerful tool to retrieve and cache images. When displaying the options that user can choose to see either Popular or Top Rated movies, "Navigation View" and "Drawer Layout" is used to make transition and user experience better.

### Future work to be implemented:
1. Retrieve trailer and also comments from the API and display them in MovieDetailsActivity.
2. Shared element transition between activities
3. Search feature for users to look for the movies they like
4. Collapsing toolbar for a better UI
5. Implement "favorites" feature that allows users to save movies using SharedPreferences and SQLite

###### To use the application, insert your API KEY inside the NetworkUtils class in the variable called TMDB_API_KEY.
