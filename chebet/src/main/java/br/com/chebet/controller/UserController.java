package br.com.chebet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.chebet.model.User;
import br.com.chebet.service.UserService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        } catch (Exception e) {
            return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            return userService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<User>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        try {
            return userService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<User>(new User(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/findByCpf/{cpf}")
    public ResponseEntity<User> getByCpf(@PathVariable String cpf) {
        try {
            return userService.findByCpf(cpf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<User>(new User(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            return userService.delete(id);
        } catch (Exception e) {
            return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @PutMapping("/")
    public ResponseEntity<String> updateUser(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return userService.update(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/isWorking")
    public ResponseEntity<String> isUserRepositoryWorking() {
        if(userService.isUserRepositoryWorking()) {
            return ChebetUtils.getResponseEntity("Everything working OK", HttpStatus.OK);
        } else {
            return ChebetUtils.getResponseEntity("Not Working", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
