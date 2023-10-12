package br.com.chebet.utils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ChebetUtils {

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"}", httpStatus);
    }

    public static LocalDate stringToLocalDate(String stringLocalDate) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(stringLocalDate, formatter);
        return localDate;
    }

    public static boolean isCpf(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("^(\\d)\\1*$")) {
            return false;
        }
    
        int[] pesosDigito1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesosDigito2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cpf.charAt(i));
            soma += digito * pesosDigito1[i];
        }
    
        int resto = soma % 11;
        int digito1 = (resto < 2) ? 0 : (11 - resto);
    
        soma = 0;
        for (int i = 0; i < 10; i++) {
            int digito = Character.getNumericValue(cpf.charAt(i));
            soma += digito * pesosDigito2[i];
        }
    
        resto = soma % 11;
        int digito2 = (resto < 2) ? 0 : (11 - resto);
    
        return (digito1 == Character.getNumericValue(cpf.charAt(9)) && digito2 == Character.getNumericValue(cpf.charAt(10)));
    }    
}