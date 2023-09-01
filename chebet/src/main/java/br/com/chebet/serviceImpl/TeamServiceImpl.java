package br.com.chebet.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Team;
import br.com.chebet.repository.TeamRepository;
import br.com.chebet.service.TeamService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService{

    @Autowired
    TeamRepository teamRepository;

    @Override
    public ResponseEntity<List<Team>> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ResponseEntity<Team> findById(int id) {
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
    public boolean isTeamRepositoryWorking() {
        try {    
            Team team = new Team();
            team.setName("Time do pedro");
            System.out.println("Sets OK");
            teamRepository.save(team);
            System.out.println("Salvo OK");
            team.setName("Time do Pedrão");
            teamRepository.save(team);  
            System.out.println("Atualizado OK");
            teamRepository.delete(team);
            System.out.println("Apagado OK");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;    
    }
    
}
