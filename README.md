# WeatherApp
### Description
**WeatherApp** - is a mobile application for **Android OC**. It's main purpose - obtain weather information. Functions that are currently done:
* Search and add cities in favorite list;
* Get weather by your GPS coordinates;

Now working on:
* Window with hourly weather forecast for the day;

It is planned to add the following features:
* Weekly weather forecast;
* Analysis of "extreme" weather conditions such as rain, fog, storm and other for the day. Warn user about them through main application window and/or a by the notification.

---

### How it's look now

![Pish1](http://haa.su/HMt/)
![Pish2](http://haa.su/HMt/)
![Pish3](http://haa.su/HMt/)

---

### What was used

The Application was developed on Java with using the following means:
* **Android Studio** IDE and Gradle;
* To create a weather icons and other visual elements used **Adobe Photoshop** CS5;
* For conversion images to vector images **Vector Magic** was used;
* For weather data I used API of **[OpenWeatherMap.org](https://openweathermap.org/api)**;
* **Retrofit** and **GSON** for connection with server and parsing incoming data;

---

### Structure
#### Adapter
CityAdapter - creating a list with favorite cities in CitiesFragment;

DailyWeatherAdapter - creating a list with daily forecast in DayForecastFragment;



#### [View](https://github.com/IstrajI/WeatherApp/tree/master/WeatherAppTest3/app/src/main/java/nikitin/weatherapp/com/weatherapptest3/Fragments)
**CitiesFragment** - first window of application with favorite cities;

**MainWindowFragmet** - second window of application with current weather data;

**DayForecastFragment** - third window of application with daily forecast;

**TabsPagerAdapter** - work with this three fragments

#### Rest
**ApiClient** - creating retrofit object;
**APIWeatherInterface** - for retrofit's request methods;
**OpenWeatherMapAPI** - class for request data from OpenWeatherMap.org server;

#### Presenters - classes with presenters for each view;
#### Model - pojo classes which used by retrofit and PreferencesAPI

CitySuggectionProvider - for getting cities search suggestion
PreferencesAPI - saving favorite cities in preferences file;
