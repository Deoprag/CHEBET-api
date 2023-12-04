package br.com.chebet.model;

import java.io.Serializable;
import java.time.LocalTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "TB_Race")
public class Race implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Formula("(SELECT COALESCE(MAX(r.raceNumber), 0) + 1 FROM TB_Race r WHERE r.championship_id = championship_id)")
    @Column(name = "race_number", nullable = false)
    private Integer raceNumber;
    
    @ManyToOne
    @JoinColumn(name = "pilot1_id", nullable = false)
    private Pilot pilot1;

    @Column(name = "pilot1_time")
    private LocalTime pilot1Time;

    @Column(name = "pilot1_broke")
    private boolean pilot1Broke = false;
    
    @ManyToOne
    @JoinColumn(name = "pilot2_id", nullable = false)
    private Pilot pilot2;
    
    @Column(name = "pilot2_time")
    private LocalTime pilot2Time;
    
    @Column(name = "pilot2_broke")
    private boolean pilot2Broke = false;

    @ManyToOne
    @JoinColumn(name = "championship_id", nullable = false)
    private Championship championship;
}
