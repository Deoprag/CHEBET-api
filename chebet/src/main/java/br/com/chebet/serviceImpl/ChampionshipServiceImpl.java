package br.com.chebet.serviceImpl;

import java.text.ParseException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.chebet.model.Bet;
import br.com.chebet.model.BetType;
import br.com.chebet.model.Championship;
import br.com.chebet.model.Pilot;
import br.com.chebet.model.Race;
import br.com.chebet.model.Transaction;
import br.com.chebet.model.TransactionType;
import br.com.chebet.model.User;
import br.com.chebet.repository.BetRepository;
import br.com.chebet.repository.ChampionshipRepository;
import br.com.chebet.repository.PilotRepository;
import br.com.chebet.repository.RaceRepository;
import br.com.chebet.repository.RankingRepository;
import br.com.chebet.repository.TransactionRepository;
import br.com.chebet.service.ChampionshipService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChampionshipServiceImpl implements ChampionshipService{

    @Autowired
    ChampionshipRepository championshipRepository;
    
    @Autowired
    PilotRepository pilotRepository;
    
    @Autowired
    RaceRepository raceRepository;
    
    @Autowired
    RankingRepository rankingRepository;
    
    @Autowired
    BetRepository betRepository;
    
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<String> register(Map<String, String> requestMap) {
        log.info("Inside register {}", requestMap);
        try {
            if (validateRegisterFields(requestMap)) {
                try {
                    Championship championship = championshipRepository.findByName(requestMap.get("name"));
                    if(Objects.isNull(championship)) {
                        championship = getChampionshipFromMap(requestMap);
                        if(championship.getPilots().size() % 2 == 0) {
                            if(championship.isFinished()) {
                                Optional<Championship> champ = championshipRepository.findById(championship.getId());
                                if(champ.isPresent()) {
                                    List<Race> races = raceRepository.findAllByChampionship(champ.get());
                                    boolean save = true;
                                    for (Race race : races) {
                                        if (race.getPilot1Time() == null || race.getPilot2Time() == null) {
                                            save = false;
                                        }
                                    }
                                    if(save) {
                                        championshipRepository.save(championship);
                                        rankingRepository.generateRanking(championship.getId());
                                        calculateWinners(championship);
                                        return ChebetUtils.getResponseEntity("Registrado com sucesso!", HttpStatus.OK);
                                    } else {
                                        return ChebetUtils.getResponseEntity("Você precisa finalizar todas as corridas antes de encerrar um campeonato!",    
                                        HttpStatus.BAD_REQUEST);
                                    }
                                }
                            } else {
                                championshipRepository.generateRaceData(championship.getId());
                                championshipRepository.save(championship);
                                return ChebetUtils.getResponseEntity("Registrado com sucesso!", HttpStatus.OK);
                            }
                        } else {
                            return ChebetUtils.getResponseEntity("Quantidade de pilotos não aceita. Selecione uma quantidade par!",    
                            HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        return ChebetUtils.getResponseEntity("O nome informado para o campeonato já está em uso!",
                        HttpStatus.BAD_REQUEST);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return ChebetUtils.getResponseEntity("Erro ao registrar campeonato!",
                HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.INVALID_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public boolean validateRegisterFields(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("date") && requestMap.containsKey("endDate") && requestMap.containsKey("pilots") && requestMap.containsKey("finished")) {
            return true;
        }
        return false;
    }
    
    public Championship getChampionshipFromMap(Map<String, String> requestMap) throws ParseException, JsonMappingException, JsonProcessingException {
        Championship championship = new Championship();
        championship.setName(requestMap.get("name"));
        championship.setDate(ChebetUtils.stringToLocalDateTime(requestMap.get("date")));
        if (requestMap.get("endDate") == null) {
            championship.setEndDate(null);
        } else {
            championship.setEndDate(ChebetUtils.stringToLocalDateTime(requestMap.get("endDate")));
        }
        championship.setPilots(getPilotList(requestMap.get("pilots")));
        if (requestMap.containsKey("finished")) championship.setFinished(Boolean.parseBoolean(requestMap.get("finished")));
        return championship;
    }

    public List<Pilot> getPilotList(String pilotsIds) {
        try {
            List<Pilot> pilots = new ArrayList<>();
            String[] ids = pilotsIds.split(", ");
            for (String id : ids) {
                int pilotId = Integer.parseInt(id.trim());
                Optional<Pilot> pilot = pilotRepository.findById(pilotId);
                if (pilot.isPresent()) {
                    pilots.add(pilot.get());
                }
            }
            return pilots;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public ResponseEntity<List<Championship>> findAll() {
        try {
            return new ResponseEntity<>(championshipRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Championship> findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public ResponseEntity<String> delete(int id) {
        log.info("Inside delete {}", id);
        try {
            Optional<Championship> optChampionship = championshipRepository.findById(id);
            if (optChampionship.isPresent()) {
                championshipRepository.delete(optChampionship.get());
                return ChebetUtils.getResponseEntity("Apagado com sucesso!", HttpStatus.OK);
            } else {
                return ChebetUtils.getResponseEntity("Campeonato não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            return ChebetUtils.getResponseEntity("Não é possível excluir este campeonato, pois ele está associado a outros dados no sistema.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        log.info("Inside update {}", requestMap);
        try {
            Optional<Championship> optChampionship = championshipRepository.findById(Integer.parseInt(requestMap.get("id")));
            if (optChampionship.isPresent()) {
                Championship championship = championshipRepository.findByName(requestMap.get("name"));
                if (optChampionship.get().equals(championship) || championship == null) {
                    championship = updateChampionshipFromMap(optChampionship.get(), requestMap);
                    if(championship.getPilots().size() % 2 == 0) {
                        if(championship.isFinished()) {
                            Optional<Championship> champ = championshipRepository.findById(championship.getId());
                            if(champ.isPresent()) {
                                List<Race> races = raceRepository.findAllByChampionship(champ.get());
                                boolean save = true;
                                for (Race race : races) {
                                    if (race.getPilot1Time() == null || race.getPilot2Time() == null) {
                                        save = false;
                                    }
                                }
                                if(save) {
                                    championshipRepository.save(championship);
                                    rankingRepository.generateRanking(championship.getId());
                                    calculateWinners(championship);
                                    return ChebetUtils.getResponseEntity("Atualizado com sucesso!", HttpStatus.OK);
                                } else {
                                    return ChebetUtils.getResponseEntity("Você precisa finalizar todas as corridas antes de encerrar um campeonato!",    
                                    HttpStatus.BAD_REQUEST);
                                }
                            }
                        } else {
                            championshipRepository.generateRaceData(championship.getId());
                            championshipRepository.save(championship);
                            return ChebetUtils.getResponseEntity("Atualizado com sucesso!", HttpStatus.OK);
                        }
                    } else {
                        return ChebetUtils.getResponseEntity("Quantidade de pilotos não aceita. Selecione uma quantidade par!",    
                        HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return ChebetUtils.getResponseEntity("O nome de campeonato já está em uso.", HttpStatus.NOT_FOUND);
                }
            } else {
                return ChebetUtils.getResponseEntity("Campeonato não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Championship updateChampionshipFromMap(Championship championship, Map<String, String> requestMap) throws ParseException {
        if (requestMap.containsKey("name")) championship.setName(requestMap.get("name"));
        if (requestMap.containsKey("date")) championship.setDate(ChebetUtils.stringToLocalDateTime(requestMap.get("date")));
        if (requestMap.containsKey("endDate")) {
            if (requestMap.get("endDate") == null) {
                championship.setEndDate(null);
            } else {
                championship.setEndDate(ChebetUtils.stringToLocalDateTime(requestMap.get("endDate")));
            }
        }
        if (requestMap.containsKey("pilots")) championship.setPilots(getPilotList(requestMap.get("pilots"))); 
        if (requestMap.containsKey("finished")) championship.setFinished(Boolean.parseBoolean(requestMap.get("finished")));
        return championship;
    }

    public void calculateWinners(Championship championship) {
        calculateSimpleVictory(championship);
    }

    public void calculateSimpleVictory(Championship championship) {
        // LISTA TODAS AS APOSTAS QUE SAO SimpleVictory E SEPARA ELAS NO championshipBets
        List<Bet> simpleVictoryList = betRepository.findAllByBetType(BetType.SimpleVictory);
        List<Bet> championshipBets = new ArrayList<>();
        for (Bet bet : simpleVictoryList) {
            if(bet.getChampionship().equals(championship)) {
                championshipBets.add(bet);
            }
        }
        
        // ENCONTRA O VENCEDOR DO CAMPEONATO, SETA AS VARIAVEIS
        Optional<Pilot> pilot = pilotRepository.findById(rankingRepository.getWinner(championship.getId()));
        Pilot winner = pilot.get();
        float moneyAmount = 0;
        float betAmount = simpleVictoryList.size();
        float winnerAmount = 0;
        List<User> winners = new ArrayList<>();

        // ADICIONA O VALOR DE TODAS AS APOSTAS EM betAmout
        // ADICIONA O VALOR DE TODAS AS APOSTAS VENCEDORAS EM winnerAmount
        for (Bet betSimpleVictory : simpleVictoryList) {
            moneyAmount += betSimpleVictory.getTransaction().getValue();
            if(betSimpleVictory.getSimpleVictory().getPilot() == winner) {
                winnerAmount += betSimpleVictory.getTransaction().getValue();
            }
        }

        // CALCULA QUANTO CADA VENCEDORA GANHARÁ E SALVA A TRANSACAO
        // O CALCULO É FEITO DA SEGUINTE MANEIRA: ((dinheiroTotalApostas / 100) / ((valorDaAposta / dinheiroApostasVencedoras) * 100))
        for (Bet betSimpleVictory : simpleVictoryList) {
            System.out.println(betSimpleVictory);
            if(betSimpleVictory.getSimpleVictory().getPilot() == winner) {
                Transaction transaction = new Transaction();
                transaction.setTransactionType(TransactionType.Pagamento);
                transaction.setUser(betSimpleVictory.getTransaction().getUser());
                transaction.setValue((moneyAmount / 100) / ((betSimpleVictory.getTransaction().getValue() / winnerAmount) * (100)));

                System.out.println("VENCEU: " + betSimpleVictory);

                transactionRepository.save(transaction);
            }
        }
    }
}
