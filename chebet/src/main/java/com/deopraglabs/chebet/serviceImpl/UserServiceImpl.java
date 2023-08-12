package com.deopraglabs.chebet.serviceImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deopraglabs.chebet.model.Gender;
import com.deopraglabs.chebet.model.Role;
import com.deopraglabs.chebet.model.User;
import com.deopraglabs.chebet.repository.UserRepository;
import com.deopraglabs.chebet.service.UserService;
import com.deopraglabs.chebet.utils.ChebetUtils;
import com.deopraglabs.chebet.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpFields(requestMap)) {
                User user = userRepository.findByEmail(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    user = userRepository.findByCpf(requestMap.get("cpf"));
                    if (Objects.isNull(user)) {
                        user = userRepository.findByPhoneNumber(requestMap.get("phoneNumber"));
                        if (Objects.isNull(user)) {
                            userRepository.save(getUserFromMap(requestMap));
                            return ChebetUtils.getResponseEntity("Successfully registered!", HttpStatus.OK);
                        } else {
                            return ChebetUtils.getResponseEntity("Phone number already exists.",
                            HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        return ChebetUtils.getResponseEntity("CPF already exists.", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return ChebetUtils.getResponseEntity("Email already exists.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return ChebetUtils.getResponseEntity(Constants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private boolean validateSignUpFields(Map<String, String> requestMap) {
        if (requestMap.containsKey("firstName") && requestMap.containsKey("lastName") && requestMap.containsKey("email")
        && requestMap.containsKey("birthDate") && requestMap.containsKey("cpf")
        && requestMap.containsKey("gender") && requestMap.containsKey("phoneNumber")
        && requestMap.containsKey("password")) {
            return true;
        }
        return false;
    }
    
    private User getUserFromMap(Map<String, String> requestMap) throws ParseException {
        User user = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setFirstName(requestMap.get("firstName"));
        user.setLastName(requestMap.get("lastName"));
        user.setEmail(requestMap.get("email"));
        user.setBirthDate(ChebetUtils.stringToLocalDate(requestMap.get("birthDate")));
        user.setCpf(requestMap.get("cpf"));
        user.setGender(Gender.valueOf(requestMap.get("gender")));
        user.setPhoneNumber(requestMap.get("phoneNumber"));
        user.setPassword(passwordEncoder.encode(requestMap.get("password")));
        user.setRole(Role.valueOf(requestMap.get("role")));
        return user;
    }
    
    @Override
    public ResponseEntity<List<User>> findAll() {
        try {
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @Override
    public ResponseEntity<User> findById(int id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                return new ResponseEntity<User>(user.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<User>(new User(), HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<User>(new User(), HttpStatus.NOT_FOUND);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<User>(new User(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @Override
    public ResponseEntity<String> delete(int id) {
        log.info("Inside delete {}", id);
        try {
            Optional<User> optUser = userRepository.findById(id);
            if (optUser.isPresent()) {
                userRepository.delete(optUser.get());
                return new ResponseEntity<String>("User deleted successfully.", HttpStatus.OK);
            } else {
                return ChebetUtils.getResponseEntity("User not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        log.info("Inside update {}", requestMap);
        try {
            Optional<User> optUser = userRepository.findById(Integer.parseInt(requestMap.get("id")));
            if (optUser.isPresent()) {
                userRepository.save(updateUserFromMap(optUser.get(), requestMap));
                return new ResponseEntity<String>("User updated successfully.", HttpStatus.OK);
            } else {
                return ChebetUtils.getResponseEntity("User not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public User updateUserFromMap(User user, Map<String, String> requestMap) throws ParseException {
        if(requestMap.containsKey("firstName")) {
            user.setFirstName(requestMap.get("firstName"));
        }
        if (requestMap.containsKey("lastName")) {
            user.setLastName(requestMap.get("lastName"));
        }
        if (requestMap.containsKey("email")) {
            user.setEmail(requestMap.get("email"));
        }
        if(requestMap.containsKey("birthDate")) {
            user.setBirthDate(ChebetUtils.stringToLocalDate(requestMap.get("birthDate")));
        }
        if(requestMap.containsKey("gender")) {
            user.setGender(Gender.valueOf(requestMap.get("gender")));
        }
        if(requestMap.containsKey("phoneNumber")) {
            user.setPhoneNumber(requestMap.get("phoneNumber"));
        }
        if(requestMap.containsKey("role")) {
            user.setRole(Role.valueOf(requestMap.get("role")));
        }
        if(requestMap.containsKey("active")) {
            user.setActive(Boolean.parseBoolean(requestMap.get("active")));
        }
        return user;
    }
}
