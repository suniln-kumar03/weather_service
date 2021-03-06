package com.weather.service.demo.service;


import com.weather.service.demo.exception.WeatherServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.management.timer.Timer;

@Service
public class WeatherService {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplate restTemplate;

    @Value("${weather.report.url}")
    private String weatherRepoUrl;

    @Value("${weather.report.api.key}")
    private String apiKey;


    @Cacheable("getWeathr")
    public ResponseEntity getWeatherByCity(String city) throws Exception {

        LOG.info("Getting weather by City " +
                "" +
                "" +
                "");

        String resourceUrl
                = weatherRepoUrl + "weather?q=" + city + "&appid=" + apiKey;
        ResponseEntity<Object> response;
        try {
            response
                    = restTemplate.getForEntity(resourceUrl, Object.class);
        } catch (Exception e) {
            throw new WeatherServiceException("Exception while calling External service");
        }
        return response;
    }


    @Cacheable("getWeathrByLatLongi")
    public ResponseEntity getWeatherByLatAndLong(String lat, String longi) throws WeatherServiceException {

        LOG.info("Getting weather info by Latitude and longitude");
        String resourceUrl
                = weatherRepoUrl + "weather?lat=" + lat + "&lon=" + longi + "&appid=" + apiKey;
        ResponseEntity<Object> response;
        try {
            response
                    = restTemplate.getForEntity(resourceUrl, Object.class);
        } catch (Exception e) {
            throw new WeatherServiceException("Exception while calling External service");
        }
        return response;
    }

    @Scheduled(fixedRate = Timer.ONE_HOUR * 2)
    @CacheEvict(value = {"getWeathr", "getWeathrByLatLongi"},
            allEntries = true)
    public void clearCache() {
        LOG.info("cache cleared");

    }


}
