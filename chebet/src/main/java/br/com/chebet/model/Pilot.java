package br.com.chebet.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "TB_Pilot")
public class Pilot implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "nickname", length = 50, nullable = false, unique = true)
    private String nickname;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "active", nullable = false, columnDefinition = "bit(1) default 0")
    private boolean active = false;
}
