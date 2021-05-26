package com.weather.service.demo.controller;

import com.weather.service.demo.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/weather")
public class WeatherController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

    WeatherService weatherService;

	@Value("${weather.report.url}")
	private String weatherRepoUrl;

	@Value("${weather.report.api.key}")
	private String apiKey;

	public WeatherController(WeatherService weatherService) {

		this.weatherService=weatherService;
	}



	@RequestMapping(value = "/getWeatherByCity/{city}", method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<Object> getWeatherByCity(@PathVariable String city) {
		LOG.info("Getting weather by City.");
		return weatherService.getWeatherByCity(city);
	}

    @RequestMapping(value = "/getWeatherByLatLong/{lat}/{longi}", method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity<Object> getWeatherByLatitudeAndLongitude(@PathVariable String lat,@PathVariable String longi) {
        LOG.info("Getting weather by latitude and longitude.");
        return weatherService.getWeatherByLatAndLong(lat,longi);
    }


}