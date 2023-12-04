package br.com.chebet.model;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "TB_Bet")
public class Bet implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated
    @Column(name = "bet_type")
    private BetType betType;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;
    
    @ManyToOne
    @JoinColumn(name = "championship_id", nullable = false)
    private Championship championship;
    
    @ManyToOne
    @JoinColumn(name = "simple_victory_id", nullable = false)
    private SimpleVictory simpleVictory;
    
    @ManyToOne
    @JoinColumn(name = "broken_car_id", nullable = false)
    private BrokenCar brokenCar;
    
    @ManyToOne
    @JoinColumn(name = "simple_position_id", nullable = false)
    private SimplePosition simplePosition;
    
    @ManyToOne
    @JoinColumn(name = "average_time_id", nullable = false)
    private AverageTime averageTime;
    
    @ManyToOne
    @JoinColumn(name = "head_to_head_id", nullable = false)
    private HeadToHead headToHead;


}
