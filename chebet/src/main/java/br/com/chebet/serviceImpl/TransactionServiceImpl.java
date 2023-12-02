package br.com.chebet.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.protobuf.Option;

import br.com.chebet.model.Pilot;
import br.com.chebet.model.Transaction;
import br.com.chebet.model.User;
import br.com.chebet.repository.TransactionRepository;
import br.com.chebet.repository.UserRepository;
import br.com.chebet.service.TransactionService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<String> register(Map<String, String> requestMap) {
        log.info("Inside register {}", requestMap);
        try {
            if (validateRegisterFields(requestMap)) {
                try {
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    return ChebetUtils.getResponseEntity("Erro ao registrar transação!",
                    HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.INVALID_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public boolean validateRegisterFields(Map<String, String> requestMap) {
        if (requestMap.containsKey("transaction_type") && requestMap.containsKey("value") && requestMap.containsKey("user_id")) {
            return true;
        }
        return true;
    }

    @Override
    public ResponseEntity<List<Transaction>> findAll() {
        try {
            return new ResponseEntity<>(transactionRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Transaction>> findAllByUser(int id) {
        try {
            Optional<User> optUser = userRepository.findById(id);
            if (optUser.isPresent()) {
                return new ResponseEntity<>(transactionRepository.findAllByUser(optUser.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @Override
    public ResponseEntity<Transaction> findById(int id) {
        try {
            Optional<Transaction> optTransaction = transactionRepository.findById(id);
            if (optTransaction.isPresent()) {
                return new ResponseEntity<Transaction>(optTransaction.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<Transaction>(new Transaction(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Transaction>(new Transaction(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> delete(int id) {
        log.info("Inside delete {}", id);
        try {
            Optional<Transaction> optTransaction = transactionRepository.findById(id);
            if (optTransaction.isPresent()) {
                transactionRepository.delete(optTransaction.get());
                return ChebetUtils.getResponseEntity("Apagada com sucesso!", HttpStatus.OK);
            } else {
                return ChebetUtils.getResponseEntity("Transação não encontrada.", HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            return ChebetUtils.getResponseEntity("Não é possível excluir esta transação, pois ela está associada a outros dados no sistema.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
