package br.com.chebet.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Bet;
import br.com.chebet.model.BetType;
import br.com.chebet.model.Championship;
import br.com.chebet.model.Transaction;
import br.com.chebet.repository.BetRepository;
import br.com.chebet.repository.ChampionshipRepository;
import br.com.chebet.repository.TransactionRepository;
import br.com.chebet.service.BetService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BetServiceImpl implements BetService {

    @Autowired
    BetRepository betRepository;
    @Autowired
    ChampionshipRepository championshipRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<List<Bet>> findAll() {
        try {
            return new ResponseEntity<>(betRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bet>> findAllByChampionship(int championshipId) {
        try {
            Optional<Championship> championship = championshipRepository.findById(championshipId);
            if(!Objects.isNull(championship)) {
                return new ResponseEntity<>(betRepository.findAllByChampionship(championship.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Bet> findById(int id) {
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
    
}
