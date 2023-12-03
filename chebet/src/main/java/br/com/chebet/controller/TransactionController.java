package br.com.chebet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.chebet.model.Preparer;
import br.com.chebet.model.Transaction;
import br.com.chebet.service.TransactionService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/")
    public ResponseEntity<String> registerTransaction(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return transactionService.register(requestMap);
        } catch (Exception e) {
            return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Transaction>> findAllByUser(@PathVariable int id) {
        try {
            return transactionService.findAllByUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Transaction>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
