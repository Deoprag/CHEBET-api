package com.deopraglabs.chebet.utils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ChebetUtils {
    
    private ChebetUtils() {

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\""+ responseMessage +"\"}", httpStatus);
    }

    public static LocalDate stringToLocalDate(String stringLocalDate) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(stringLocalDate, formatter);
        return localDate;
    }
}
