package br.com.chebet.serviceImpl;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.BetType;
import br.com.chebet.model.Championship;
import br.com.chebet.repository.ChampionshipRepository;
import br.com.chebet.service.ChampionshipService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChampionshipServiceImpl implements ChampionshipService{

    @Autowired
    ChampionshipRepository championshipRepository;

    @Override
    public ResponseEntity<List<Championship>> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ResponseEntity<Championship> findById(int id) {
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
    public boolean isChampionshipRepositoryWorking() {
        try {    
            Championship championship = new Championship();
            championship.setName("Chebet Arrancadas 2023");
            championship.setDateTime(LocalDateTime.of(2023, 8, 15, 18, 0, 0, 0));
            System.out.println("Sets OK");
            championshipRepository.save(championship);
            System.out.println("Salvo OK");
            championship.setEndDateTime(LocalDateTime.of(2023, 8, 18, 18, 0, 0, 0));
            championshipRepository.save(championship);
            System.out.println("Atualizado OK");
            championshipRepository.delete(championship);
            System.out.println("Apagado OK");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
}
