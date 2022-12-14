# :earth_africa: Geolocator App
Technical task for Eurofins Genomics

### Tech Stack
- Java 17
- SpringBoot
- Gradle 7.6
- MongoDB
- Docker
- jUnit5
-------

# APIs

## Location by IP
```
GET /api/ip/{ip}
```
Returns location data for the given IP address. 
- Given invalid IP adress will result in a 418
- Empty parameter will result in a 404

### Usage example: 

 *Resquest:*
 ```
 http://localhost:8080/api/location/1.1.1.1
 ```
 
 *Response:*
 ```
 {
    "ip": "1.1.1.1",
    "city": "Los Angeles",
    "region": "California",
    "country": "United States",
    "loc": "34.0522,-118.2437",
    "org": "AS13335 Cloudflare, Inc.",
    "postal": "90076",
    "timezone": "America/Los_Angeles"
}
 ```
---------
## List of universities by country
 ```
 GET /api/universities?country={optionl: country name}
 ```
Returns a list of universities by Copuntry Name. 
- Given an empty parameter will return a list of all available universities
- Given a country not in the country list - will return an empty list

### Usage example: 

*Request:*
```
http://localhost:8080/api/universities?country=Luxembourg
```
*Response:*
```
[
    {
        "country": "Luxembourg",
        "domains": [
            "iuil.lu"
        ],
        "webPages": null,
        "alphaTwoCode": null,
        "name": "International University Institute of Luxembourg",
        "stateProvince": null
    },
    {
        "country": "Luxembourg",
        "domains": [
            "uni.lu"
        ],
        "webPages": null,
        "alphaTwoCode": null,
        "name": "University of Luxemburg",
        "stateProvince": null
    },
    {
        "country": "Luxembourg",
        "domains": [
            "iuil.lu"
        ],
        "webPages": null,
        "alphaTwoCode": null,
        "name": "International University Institute of Luxembourg",
        "stateProvince": null
    },
    {
        "country": "Luxembourg",
        "domains": [
            "uni.lu"
        ],
        "webPages": null,
        "alphaTwoCode": null,
        "name": "University of Luxemburg",
        "stateProvince": null
    }
]
```

# Data Storage
MongoDB is used to store IP address alongside information about location.

```locatordb``` database contains two collections:
- ```ip_addresses``` - contains ip address and a reference to the related location
- ```location``` - contains location information

<img src="/assets/locatordb.png" width="400">

This was done to avoid duplication of data, since several IP addresses can belong to the same location.

# Running application

First jar needs to be built using
```
> ./gradlew bootJar 
```

To start the application run following from the **docker** folder 
``` 
> docker-compose up --build 
```

After the inital container is built ```docker-compose up -d``` will be enough to spin up the applicaiton.

- The application will start on ```8080``` port
- The database will be available at ```27017``` port

# Notes
Disclaimer: I've used this homework to explore some new (to me) concepts: 
- I've used two different approaches to test the controllers - Mockito with @InjectMocks and @WebMvcTest alongside SpringExtension.class
- I've chosen MongoDb for this task, because I haven't really worked with it as developer.
- I don't have experience with containerizing an application, so I also wanted to try that

