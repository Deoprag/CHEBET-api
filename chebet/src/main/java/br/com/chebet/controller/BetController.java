package br.com.chebet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.chebet.service.BetService;

@RestController
@RequestMapping("/api/bet")
public class BetController {

    @Autowired
    BetService betService;

}
