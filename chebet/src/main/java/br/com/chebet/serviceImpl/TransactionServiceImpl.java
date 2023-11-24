package br.com.chebet.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Transaction;
import br.com.chebet.model.TransactionType;
import br.com.chebet.repository.TransactionRepository;
import br.com.chebet.repository.UserRepository;
import br.com.chebet.service.TransactionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;

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
                return ChebetUtils.getResponseEntity("Usuário não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Transaction> findById(int id) {
        try {
            return new ResponseEntity(transactionRepository.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Transaction(), HttpStatus.INTERNAL_SERVER_ERROR);
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
<<<<<<< HEAD
    
=======
>>>>>>> 9714fa71418f1e3c0e9f58157895c2011660fa0e
}
