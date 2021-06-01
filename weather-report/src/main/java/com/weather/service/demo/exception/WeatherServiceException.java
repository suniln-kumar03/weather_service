package com.weather.service.demo.exception;

public class WeatherServiceException extends Exception{


    private static final long serialVersionUID = 8975394985068095778L;

    public WeatherServiceException(String message) {
        super(message);
    }
}
