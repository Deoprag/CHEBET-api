package br.com.chebet.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Car;

@Service
public interface CarService {
    public ResponseEntity<List<Car>> findAll();

    public ResponseEntity<Car> findById(int id);

    public ResponseEntity<String> delete(int id);

    public ResponseEntity<String> update(Map<String, String> requestMap);

    public boolean isCarRepositoryWorking();
}
