# Project 2 - *Flixter*

**Flixter** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **14** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies** from the Movie Database API
* [x] Display a nice default [placeholder graphic](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
* [x] For each movie displayed, user can see the following details:
  * [x] Title, Poster Image, Overview (Portrait mode)
  * [x] Title, Backdrop Image, Overview (Landscape mode)
* [x] Allow user to view details of the movie including ratings within a separate activity

The following **stretch** features are implemented:

* [x] Improved the user interface by experimenting with styling and coloring.
* [x] Apply rounded corners for the poster or background images using [Glide transformations](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#transformations)
* [x] Apply the popular [View Binding annotation library](http://guides.codepath.org/android/Reducing-View-Boilerplate-with-ViewBinding) to reduce boilerplate code.
* [x] Allow video trailers to be played in current orientation (and full-screen) using the YouTubePlayerView from the details screen.

The following **additional** features are implemented:

* [x] Allow the user to see the movie's number of votes, release date, backdrop image, and trailer availability from the details screen.
* [x] Allow users to see how many movies are loaded at the top of the main screen
* [x] Allow users to view movies coming soon to the United States by clicking the "Coming Soon" button  

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/evelynhasama/FlixterAndroid/blob/master/vertical_walkthrough.gif' title='Vertical Video Walkthrough' width='' alt='Vertical Video Walkthrough' />

<img src='https://github.com/evelynhasama/FlixterAndroid/blob/master/horizontal_walkthrough.gif' title='Horizontal Video Walkthrough' width='' alt='Horizontal Video Walkthrough' />

GIF created with [Kap](https://getkap.co/).

## Notes

- Incorprating the Youtube player view was initially challenging, as it was unlike any of the views/activities we had already implemented. However, I enjoyed this challenge and using the videos endpoint of the Movie Database API to get the Youtube Video key.
- I also found adding Coming Soon movies really interesting because I was not sure how best to approach switching between Now Playing and Coming Soon movies with the single Recycler View. I ended up using two buttons and the Apache Commons Library to save which type of movies the user is viewing (so users can return to the same list of movies).


## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
