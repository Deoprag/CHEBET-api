package br.com.chebet.serviceImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.chebet.model.Team;
import br.com.chebet.repository.TeamRepository;
import br.com.chebet.service.TeamService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService{

    @Autowired
    TeamRepository teamRepository;

    @Override
    public ResponseEntity<String> registerTeam(Map<String, String> requestMap) {
        log.info("Inside register team {}", requestMap);
        try {
            if (requestMap.containsKey("name")) {
                Team team = teamRepository.findByName(requestMap.get("name"));
                if (Objects.isNull(team)) {
                    teamRepository.save(getTeamFromMap(requestMap));
                    return ChebetUtils.getResponseEntity("Registrado com sucesso!", HttpStatus.OK);
                } else {
                    return ChebetUtils.getResponseEntity("O nome informado já existe!",
                                    HttpStatus.BAD_REQUEST);
                }
            } else {
                return ChebetUtils.getResponseEntity(Constants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Team getTeamFromMap(Map<String, String> requestMap) throws ParseException {
        Team team = new Team();
        team.setName(requestMap.get("name"));
        return team;
    }

    @Override
    public ResponseEntity<List<Team>> findAll() {
        try {
            return new ResponseEntity<>(teamRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Team> findById(int id) {
        try {
            Optional<Team> team = teamRepository.findById(id);
            if (team.isPresent()) {
                return new ResponseEntity<Team>(team.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<Team>(new Team(), HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<Team>(new Team(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Team>(new Team(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> delete(int id) {
        log.info("Inside delete {}", id);
        try {
            Optional<Team> optTeam = teamRepository.findById(id);
            if (optTeam.isPresent()) {
                teamRepository.delete(optTeam.get());
                return ChebetUtils.getResponseEntity("Apagado com sucesso!", HttpStatus.OK);
            } else {
                return ChebetUtils.getResponseEntity("Equipe não encontrada.", HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            return ChebetUtils.getResponseEntity("Não é possível excluir esta equipe, pois ela está associada a outros dados no sistema.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        log.info("Inside update {}", requestMap);
        try {
            Optional<Team> optTeam = teamRepository.findById(Integer.parseInt(requestMap.get("id")));
            if (optTeam.isPresent()) {
                teamRepository.save(updateTeamFromMap(optTeam.get(), requestMap));
                return ChebetUtils.getResponseEntity("Atualizado com sucesso!", HttpStatus.OK);
            } else {
                return ChebetUtils.getResponseEntity("Equipe não encontrada.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Team updateTeamFromMap(Team team, Map<String, String> requestMap) throws ParseException {
        if (requestMap.containsKey("name")) {
            team.setName(requestMap.get("name"));
        }
        return team;
    }

}
