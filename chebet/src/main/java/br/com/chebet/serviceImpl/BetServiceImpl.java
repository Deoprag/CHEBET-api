package br.com.chebet.serviceImpl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Bet;
import br.com.chebet.model.BetType;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
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

    @Override
    public boolean isBetRepositoryWorking() {
        try {    
            Bet bet = new Bet();
            bet.setBetType(BetType.Simple_Victory);
            Optional<Transaction> obj = transactionRepository.findById(1);
            if (!Objects.isNull(obj)) {
                bet.setTransaction(obj.get());
            }
            bet.setChampionship(championshipRepository.findByName("Campeonato 1"));
            System.out.println("Sets OK");
            betRepository.save(bet);
            System.out.println("Salvo OK");
            bet.setBetType(BetType.Less_Time);
            betRepository.save(bet);
            System.out.println("Atualizado OK");
            betRepository.delete(bet);
            System.out.println("Apagado OK");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
}
