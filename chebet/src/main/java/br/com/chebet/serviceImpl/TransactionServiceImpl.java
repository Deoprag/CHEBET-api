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
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<List<Transaction>> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ResponseEntity<Transaction> findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public ResponseEntity<String> delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean isTransactionRepositoryWorking() {
        try {    
            Transaction transaction = new Transaction();
            transaction.setTransactionType(TransactionType.Bet);
            transaction.setValue(50);
            transaction.setUser(userRepository.findByCpf("14848328683"));
            System.out.println("Sets OK");
            transactionRepository.save(transaction);
            System.out.println("Salvo OK");
            transaction.setValue(500);
            transactionRepository.save(transaction);
            System.out.println("Atualizado OK");
            transactionRepository.delete(transaction);
            System.out.println("Apagado OK");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;    
    }
    
}
