package br.com.chebet.model;

import java.io.Serializable;
import java.time.LocalTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "TB_Head_To_Head")
public class HeadToHead implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "championship_id", nullable = false)
    private Championship championship;
    
    @ManyToOne
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;
    
    @ManyToOne
    @JoinColumn(name = "winner_id", nullable = false)
    private Pilot winner;
    
    @ManyToOne
    @JoinColumn(name = "loser_id", nullable = false)
    private Pilot loser;

}
