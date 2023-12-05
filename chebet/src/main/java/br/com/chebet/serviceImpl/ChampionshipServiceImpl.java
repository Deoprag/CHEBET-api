package br.com.chebet.serviceImpl;

import java.text.ParseException;
import java.time.LocalTime;
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
                                championshipRepository.save(championship);
                                championshipRepository.generateRaceData(championship.getId());
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
                            championshipRepository.save(championship); 
                            championshipRepository.generateRaceData(championship.getId());
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

    public void calculateWinners(Championship championship) throws ParseException {
        calculateSimpleVictory(championship);
        calculateBrokenCar(championship);
        calculateSimplePosition(championship);
        calculateAverageTime(championship);
        calculateHeadToHead(championship);
    }
    
    public void calculateSimpleVictory(Championship championship) {
        // ENCONTRA O VENCEDOR DO CAMPEONATO, SETA AS VARIAVEIS
        float moneyAmount = 0;
        float winnerAmount = 0;
        List<User> winners = new ArrayList<>();
        Optional<Pilot> pilot = pilotRepository.findById(rankingRepository.getWinner(championship.getId()));
        Pilot winner = pilot.get();

        // LISTA TODAS AS APOSTAS QUE SAO SimpleVictory E SEPARA ELAS NO championshipBets
        // ADICIONA O VALOR DE TODAS AS APOSTAS EM betAmout
        // ADICIONA O VALOR DE TODAS AS APOSTAS VENCEDORAS EM winnerAmount
        List<Bet> simpleVictoryList = betRepository.findAllByBetType(BetType.SimpleVictory);
        for (Bet bet : simpleVictoryList) {
            if(bet.getChampionship().equals(championship)) {
                moneyAmount += bet.getTransaction().getValue();
                if(bet.getSimpleVictory().getPilot() == winner) {
                    winnerAmount += bet.getTransaction().getValue();
                }
            }
        }
        
        // CALCULA QUANTO CADA VENCEDORA GANHARÁ E SALVA A TRANSACAO
        // O CALCULO É FEITO DA SEGUINTE MANEIRA: ((dinheiroTotalApostas / 100) / ((valorDaAposta / dinheiroApostasVencedoras) * 100))
        if(moneyAmount > 0 && winnerAmount > 0) {
            for (Bet betSimpleVictory : simpleVictoryList) {
                if(betSimpleVictory.getChampionship().equals(championship)) {
                    if(betSimpleVictory.getSimpleVictory().getPilot().equals(winner)) {
                        Transaction transaction = new Transaction();
                        transaction.setTransactionType(TransactionType.Pagamento);
                        transaction.setUser(betSimpleVictory.getTransaction().getUser());
                        float pagamento = Math.round(((moneyAmount / 100) * ((betSimpleVictory.getTransaction().getValue() * 100) / winnerAmount)));
                        transaction.setValue(pagamento);
                        
                        System.out.println(moneyAmount);
                        System.out.println(winnerAmount);
                        System.out.println(betSimpleVictory.getTransaction().getUser().getFirstName() + " VENCEU SIMPLEVICTORY E RECEBEU: " + pagamento + " COM A APOSTA DE ID: " + betSimpleVictory.getId() + " E VALOR R$" + betSimpleVictory.getTransaction().getValue());
                        transactionRepository.save(transaction);
                    }
                }
            }
        }
    }

    public void calculateBrokenCar(Championship championship) {
        float moneyAmount = 0;
        float winnerAmount = 0;
        List<User> winners = new ArrayList<>();
        List<Race> races = raceRepository.findAllByChampionship(championship);
        List<Pilot> broken = new ArrayList<>();
        
        // LISTA OS PILOTOS QUE TIVERAM O CARRO QUEBRADO
        for (Race race : races) {
            if(race.isPilot1Broke()) {
                broken.add(race.getPilot1());
            } else if (race.isPilot2Broke()) {
                broken.add(race.getPilot2());
            }
        }
        // LISTA TODAS AS APOSTAS QUE SAO BrokenCar E SEPARA ELAS NO championshipBets
        // ADICIONA O VALOR DE TODAS AS APOSTAS EM betAmout
        // ADICIONA O VALOR DE TODAS AS APOSTAS VENCEDORAS EM winnerAmount
        List<Bet> brokenCarList = betRepository.findAllByBetType(BetType.BrokenCar);
        for (Bet bet : brokenCarList) {
            if(bet.getChampionship().equals(championship)) {
                moneyAmount += bet.getTransaction().getValue();
                // QUANTIDADE DE DINHEIRO VENCEDORES
                if(broken.contains(bet.getBrokenCar().getPilot())) {
                    winnerAmount += bet.getTransaction().getValue();
                }
            }
        }

        // CALCULA QUANTO CADA VENCEDORA GANHARÁ E SALVA A TRANSACAO
        // O CALCULO É FEITO DA SEGUINTE MANEIRA: ((dinheiroTotalApostas / 100) / ((valorDaAposta / dinheiroApostasVencedoras) * 100))
        if(moneyAmount > 0 && winnerAmount > 0) {
            for (Bet betBrokenCar : brokenCarList) {
                // CONDICAO DE VITORIA
                if(betBrokenCar.getChampionship().equals(championship)) {
                    if(broken.contains(betBrokenCar.getBrokenCar().getPilot())) {
                        Transaction transaction = new Transaction();
                        transaction.setTransactionType(TransactionType.Pagamento);
                        transaction.setUser(betBrokenCar.getTransaction().getUser());
                        float pagamento = Math.round(((moneyAmount / 100) * ((betBrokenCar.getTransaction().getValue() * 100) / winnerAmount)));
                        transaction.setValue(pagamento);
                        
                        System.out.println(moneyAmount);
                        System.out.println(winnerAmount);
                        System.out.println(betBrokenCar.getTransaction().getUser().getFirstName() + " VENCEU BROKENCAR E RECEBEU: " + pagamento + " COM A APOSTA DE ID: " + betBrokenCar.getId() + " E VALOR R$" + betBrokenCar.getTransaction().getValue());
                        transactionRepository.save(transaction);
                    }
                }
            }
        }
    }
    
    public void calculateSimplePosition(Championship championship) {
        float moneyAmount = 0;
        float winnerAmount = 0;
        List<User> winners = new ArrayList<>();
        List<Race> races = raceRepository.findAllByChampionship(championship);
        List<Pilot> pilots = new ArrayList<>();
        // LISTA OS PILOTOS QUE PARTICIPARAM DO CAMPEONATO
        for (Race race : races) {
            pilots.add(race.getPilot1());
            pilots.add(race.getPilot2());
        }
        
        // LISTA TODAS AS APOSTAS QUE SAO SimplePosition E SEPARA ELAS NO championshipBets
        // ADICIONA O VALOR DE TODAS AS APOSTAS EM betAmout
        // ADICIONA O VALOR DE TODAS AS APOSTAS VENCEDORAS EM winnerAmount
        List<Bet> simplePositionList = betRepository.findAllByBetType(BetType.SimplePosition);
        for (Bet bet : simplePositionList) {
            if(bet.getChampionship().equals(championship)) {
                moneyAmount += bet.getTransaction().getValue();
                // QUANTIDADE DE DINHEIRO VENCEDORES
                short position = rankingRepository.getPosition(championship.getId(), bet.getSimplePosition().getPilot().getId());

                if(position == bet.getSimplePosition().getPosition()) {
                    winnerAmount += bet.getTransaction().getValue();
                }
            }
        }

        // CALCULA QUANTO CADA VENCEDORA GANHARÁ E SALVA A TRANSACAO
        // O CALCULO É FEITO DA SEGUINTE MANEIRA: ((dinheiroTotalApostas / 100) / ((valorDaAposta / dinheiroApostasVencedoras) * 100))
        if(moneyAmount > 0 && winnerAmount > 0) {
            for (Bet betSimplePosition : simplePositionList) {
                // CONDICAO DE VITORIA
                if(betSimplePosition.getChampionship().equals(championship)) {
                    short position = rankingRepository.getPosition(championship.getId(), betSimplePosition.getSimplePosition().getPilot().getId());
                    
                    if(position == betSimplePosition.getSimplePosition().getPosition()) {
                        Transaction transaction = new Transaction();
                        transaction.setTransactionType(TransactionType.Pagamento);
                        transaction.setUser(betSimplePosition.getTransaction().getUser());
                        float pagamento = Math.round(((moneyAmount / 100) * ((betSimplePosition.getTransaction().getValue() * 100) / winnerAmount)));
                        transaction.setValue(pagamento);
                        
                        System.out.println(moneyAmount);
                        System.out.println(winnerAmount);
                        System.out.println(betSimplePosition.getTransaction().getUser().getFirstName() + " VENCEU SIMPLEPOSITION E RECEBEU: " + pagamento + " COM A APOSTA DE ID: " + betSimplePosition.getId() + " E VALOR R$" + betSimplePosition.getTransaction().getValue());
                        transactionRepository.save(transaction);
                    }
                }
            }
        }
    }

    public void calculateAverageTime(Championship championship) throws ParseException {
        float moneyAmount = 0;
        float winnerAmount = 0;
        List<User> winners = new ArrayList<>();
        LocalTime avgTime = ChebetUtils.stringToLocalTime(rankingRepository.getAverageTime(championship.getId()).concat(".000"));
        // LISTA TODAS AS APOSTAS QUE SAO AverageTime E SEPARA ELAS NO championshipBets
        // ADICIONA O VALOR DE TODAS AS APOSTAS EM betAmout
        // ADICIONA O VALOR DE TODAS AS APOSTAS VENCEDORAS EM winnerAmount
        List<Bet> averageTimeList = betRepository.findAllByBetType(BetType.AverageTime);
        for (Bet bet : averageTimeList) {
            if(bet.getChampionship().equals(championship)) {
                moneyAmount += bet.getTransaction().getValue();
                if((bet.getAverageTime().getAverageTime1().isBefore(avgTime) || bet.getAverageTime().getAverageTime1().equals(avgTime)) && bet.getAverageTime().getAverageTime2().isAfter(avgTime) || bet.getAverageTime().getAverageTime2().equals(avgTime)) {
                    winnerAmount += bet.getTransaction().getValue();
                }
            }
        }

        // CALCULA QUANTO CADA VENCEDORA GANHARÁ E SALVA A TRANSACAO
        // O CALCULO É FEITO DA SEGUINTE MANEIRA: ((dinheiroTotalApostas / 100) / ((valorDaAposta / dinheiroApostasVencedoras) * 100))
        if(moneyAmount > 0 && winnerAmount > 0) {
            for (Bet betAverageTime : averageTimeList) {
                if(betAverageTime.getChampionship().equals(championship)) {
                    // CONDICAO DE VITORIA
                    if((betAverageTime.getAverageTime().getAverageTime1().isBefore(avgTime) || betAverageTime.getAverageTime().getAverageTime1().equals(avgTime)) && betAverageTime.getAverageTime().getAverageTime2().isAfter(avgTime) || betAverageTime.getAverageTime().getAverageTime2().equals(avgTime)) {
                        Transaction transaction = new Transaction();
                        transaction.setTransactionType(TransactionType.Pagamento);
                        transaction.setUser(betAverageTime.getTransaction().getUser());
                        float pagamento = Math.round(((moneyAmount / 100) * ((betAverageTime.getTransaction().getValue() * 100) / winnerAmount)));
                        transaction.setValue(pagamento);
                        
                        System.out.println(moneyAmount);
                        System.out.println(winnerAmount);
                        System.out.println(betAverageTime.getTransaction().getUser().getFirstName() + " VENCEU AVERAGETIME E RECEBEU: " + pagamento + " COM A APOSTA DE ID: " + betAverageTime.getId() + " E VALOR R$" + betAverageTime.getTransaction().getValue());
                        transactionRepository.save(transaction);
                    }
                }
            }
        }
    }

    public void calculateHeadToHead(Championship championship) throws ParseException {
        float moneyAmount = 0;
        float winnerAmount = 0;
        List<User> winners = new ArrayList<>();
        // LISTA TODAS AS APOSTAS QUE SAO HeadToHead E SEPARA ELAS NO championshipBets
        // ADICIONA O VALOR DE TODAS AS APOSTAS EM betAmout
        // ADICIONA O VALOR DE TODAS AS APOSTAS VENCEDORAS EM winnerAmount
        List<Bet> headToHeadList = betRepository.findAllByBetType(BetType.HeadToHead);
        for (Bet bet : headToHeadList) {
            if(bet.getChampionship().equals(championship)) {
                moneyAmount += bet.getTransaction().getValue();
                short position1 = rankingRepository.getPosition(championship.getId(), bet.getHeadToHead().getWinner().getId());
                short position2 = rankingRepository.getPosition(championship.getId(), bet.getHeadToHead().getLoser().getId());
                System.out.println("Winner: " + position1 + ' ' + "Loser:" + position2);
                if(position1 < position2) {
                    winnerAmount += bet.getTransaction().getValue();
                }
            }
        }

        // CALCULA QUANTO CADA VENCEDORA GANHARÁ E SALVA A TRANSACAO
        // O CALCULO É FEITO DA SEGUINTE MANEIRA: ((dinheiroTotalApostas / 100) / ((valorDaAposta / dinheiroApostasVencedoras) * 100))
        if(moneyAmount > 0 && winnerAmount > 0) {
            for (Bet betHeadToHead : headToHeadList) {
                if(betHeadToHead.getChampionship().equals(championship)) {
                    // CONDICAO DE VITORIA
                    short position1 = rankingRepository.getPosition(championship.getId(), betHeadToHead.getHeadToHead().getWinner().getId());
                    short position2 = rankingRepository.getPosition(championship.getId(), betHeadToHead.getHeadToHead().getLoser().getId());
                    if(position1 < position2) {
                        Transaction transaction = new Transaction();
                        transaction.setTransactionType(TransactionType.Pagamento);
                        transaction.setUser(betHeadToHead.getTransaction().getUser());
                        float pagamento = Math.round(((moneyAmount / 100) * ((betHeadToHead.getTransaction().getValue() * 100) / winnerAmount)));
                        transaction.setValue(pagamento);
                        
                        System.out.println(moneyAmount);
                        System.out.println(winnerAmount);
                        System.out.println(betHeadToHead.getTransaction().getUser().getFirstName() + " VENCEU HEADTOHEAD E RECEBEU: " + pagamento + " COM A APOSTA DE ID: " + betHeadToHead.getId() + " E VALOR R$" + betHeadToHead.getTransaction().getValue());
                        transactionRepository.save(transaction);
                    }
                }
            }
        }
    }
}
