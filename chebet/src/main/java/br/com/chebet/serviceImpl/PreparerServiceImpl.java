package br.com.chebet.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Preparer;
import br.com.chebet.repository.PreparerRepository;
import br.com.chebet.service.PreparerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PreparerServiceImpl implements PreparerService {
  
    @Autowired
    PreparerRepository preparerRepository;
  
    @Override
    public ResponseEntity<List<Preparer>> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ResponseEntity<Preparer> findById(int id) {
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
    public boolean isPreparerRepositoryWorking() {
        try {    
            Preparer preparer = new Preparer();
            preparer.setName("Pedro Preparador da Silva");
            preparer.setNickname("Pedrao");
            System.out.println("Sets OK");
            preparerRepository.save(preparer);
            System.out.println("Salvo OK");
            preparer.setNickname("Pedrão");
            preparerRepository.save(preparer);  
            System.out.println("Atualizado OK");
            preparerRepository.delete(preparer);
            System.out.println("Apagado OK");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;    
    }
}
