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

import br.com.chebet.model.AverageTime;
import br.com.chebet.model.Bet;
import br.com.chebet.model.BetType;
import br.com.chebet.model.BrokenCar;
import br.com.chebet.model.Championship;
import br.com.chebet.model.HeadToHead;
import br.com.chebet.model.Pilot;
import br.com.chebet.model.Preparer;
import br.com.chebet.model.Race;
import br.com.chebet.model.SimplePosition;
import br.com.chebet.model.SimpleVictory;
import br.com.chebet.model.Transaction;
import br.com.chebet.model.TransactionType;
import br.com.chebet.model.User;
import br.com.chebet.repository.BetRepository;
import br.com.chebet.repository.ChampionshipRepository;
import br.com.chebet.repository.PilotRepository;
import br.com.chebet.repository.RaceRepository;
import br.com.chebet.repository.TransactionRepository;
import br.com.chebet.repository.UserRepository;
import br.com.chebet.service.BetService;
import br.com.chebet.utils.ChebetUtils;
import br.com.chebet.utils.Constants;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    PilotRepository pilotRepository;

    @Autowired
    RaceRepository raceRepository;

    @Override
	public ResponseEntity<String> register(Map<String, String> requestMap) {
		log.info("Inside register {}", requestMap);
        try {
            if (validateRegisterFields(requestMap)) {
                try {
                    betRepository.save(getBetFromMap(requestMap));
                    return ChebetUtils.getResponseEntity("Registrado com sucesso!", HttpStatus.OK);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ChebetUtils.getResponseEntity("Erro ao registrar aposta!",
                    HttpStatus.BAD_REQUEST);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ChebetUtils.getResponseEntity(Constants.INVALID_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
	}

    public boolean validateRegisterFields(Map<String, String> requestMap) {
        if (requestMap.containsKey("value") && requestMap.containsKey("user_id") && requestMap.containsKey("championship_id") && requestMap.containsKey("bet_type") && requestMap.containsKey("pilot_id") && requestMap.containsKey("car_position") && requestMap.containsKey("average_time1") && requestMap.containsKey("average_time2") && requestMap.containsKey("race_id") && requestMap.containsKey("winner_id") && requestMap.containsKey("loser_id")) {
            return true;
        }
        return false;
    }

    public Bet getBetFromMap(Map<String, String> requestMap) throws ParseException {
        Bet bet = new Bet();
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.Aposta);
        transaction.setValue(Float.parseFloat(requestMap.get("value")));
        Optional<User> optUser = userRepository.findById(Integer.parseInt(requestMap.get("user_id")));
        if(optUser.isPresent()) {
            transaction.setUser(optUser.get());
            bet.setUser(optUser.get());
        }
        bet.setTransaction(transaction);
        Optional<Championship> optChampionship = championshipRepository.findById(Integer.parseInt(requestMap.get("championship_id")));
        if(optChampionship.isPresent()) {
            bet.setChampionship(optChampionship.get());
        }

        switch (requestMap.get("bet_type")) {
            case "SimpleVictory":
                bet.setBetType(BetType.SimpleVictory);
                SimpleVictory simpleVictory = new SimpleVictory();
                if (optChampionship.isPresent()) {
                    simpleVictory.setChampionship(optChampionship.get());
                }
                Optional<Pilot> optPilotSV = pilotRepository.findById(Integer.parseInt(requestMap.get("pilot_id")));
                if (optPilotSV.isPresent()) {
                    simpleVictory.setPilot(optPilotSV.get());
                }
                bet.setSimpleVictory(simpleVictory);

                System.out.println(simpleVictory);
                break;
            case "BrokenCar":
                bet.setBetType(BetType.BrokenCar);
                BrokenCar brokenCar = new BrokenCar();
                if (optChampionship.isPresent()) {
                    brokenCar.setChampionship(optChampionship.get());
                }
                Optional<Pilot> optPilotBC = pilotRepository.findById(Integer.parseInt(requestMap.get("pilot_id")));
                if (optPilotBC.isPresent()) {
                    brokenCar.setPilot(optPilotBC.get());
                }
                bet.setBrokenCar(brokenCar);
                
                System.out.println(brokenCar);
                break;
            case "SimplePosition":
                bet.setBetType(BetType.SimplePosition);
                SimplePosition simplePosition = new SimplePosition();
                if (optChampionship.isPresent()) {
                    simplePosition.setChampionship(optChampionship.get());
                }
                Optional<Pilot> optPilotSP = pilotRepository.findById(Integer.parseInt(requestMap.get("pilot_id")));
                if (optPilotSP.isPresent()) {
                    simplePosition.setPilot(optPilotSP.get());
                }
                simplePosition.setPosition(Short.parseShort(requestMap.get("car_position")));
                bet.setSimplePosition(simplePosition);

                System.out.println(simplePosition);
                break;
            case "AverageTime":
                bet.setBetType(BetType.AverageTime);
                AverageTime averageTime = new AverageTime();
                if (optChampionship.isPresent()) {
                    averageTime.setChampionship(optChampionship.get());
                }
                averageTime.setAverageTime1(ChebetUtils.stringToLocalTime(requestMap.get("average_time1").concat(".000")));
                averageTime.setAverageTime2(ChebetUtils.stringToLocalTime(requestMap.get("average_time2").concat(".000")));
                bet.setAverageTime(averageTime);

                System.out.println(averageTime);
                break;
            case "HeadToHead":
                bet.setBetType(BetType.HeadToHead);
                HeadToHead headToHead = new HeadToHead();
                if (optChampionship.isPresent()) {
                    headToHead.setChampionship(optChampionship.get());
                }
                Optional<Race> optRace = raceRepository.findById(Integer.parseInt(requestMap.get("race_id")));
                if(optRace.isPresent()) {
                    headToHead.setRace(optRace.get());
                }
                Optional<Pilot> winner = pilotRepository.findById(Integer.parseInt(requestMap.get("winner_id")));
                if(winner.isPresent()) {
                    headToHead.setWinner(winner.get());
                }
                Optional<Pilot> loser = pilotRepository.findById(Integer.parseInt(requestMap.get("loser_id")));
                if(loser.isPresent()) {
                    headToHead.setLoser(loser.get());
                }
                bet.setHeadToHead(headToHead);

                System.out.println(headToHead);
                break;
            }
        return bet;
    }

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
    public ResponseEntity<List<Bet>> findAllByUser(int userId) {
        try {
            Optional<User> user = userRepository.findById(userId);
            if(!Objects.isNull(user)) {
                return new ResponseEntity<>(betRepository.findAllByUser(user.get()), HttpStatus.OK);
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
        log.info("Inside delete {}", id);
        try {
            Optional<Bet> optBet = betRepository.findById(id);
            if (optBet.isPresent()) {
                betRepository.delete(optBet.get());
                return ChebetUtils.getResponseEntity("Apagado com sucesso!", HttpStatus.OK);
            } else {
                return ChebetUtils.getResponseEntity("Aposta não encontrada.", HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            return ChebetUtils.getResponseEntity("Teste.", HttpStatus.CONFLICT);
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

    
}
