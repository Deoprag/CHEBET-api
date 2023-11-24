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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "TB_Car")
public class Car implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nickname", length = 50, nullable = false)
    private String nickname;

    @Column(name = "year", nullable = false)
    private short year;

    @Column(name = "model", nullable = false, length = 150)
    private String model;

    @Enumerated
    @Column(name = "color", nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "pilot_id", nullable = false, unique = true)
    private Pilot pilot;

    @ManyToOne
    @JoinColumn(name = "preparer_id")
    private Preparer preparer;
}
