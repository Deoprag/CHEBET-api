package br.com.chebet.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import br.com.chebet.model.User;

public interface UserService {
    public ResponseEntity<String> signUp(Map<String, String> requestMap);

    public ResponseEntity<List<User>> findAll();

    public ResponseEntity<User> findById(int id);

    public ResponseEntity<String> delete(int id);

    public ResponseEntity<String> update(Map<String, String> requestMap);

    public boolean isUserRepositoryWorking();

}
