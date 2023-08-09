package com.deopraglabs.chebet.model;

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

    @Column(name = "plate", length = 7, nullable = false)
    private String plate;

    @Column(name = "year", nullable = false)
    private short year;
    
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(name = "model", nullable = false, length = 150)
    private String model;

    @Enumerated
    @Column(name = "color", nullable = false)
    private Color color;

    @Column(name = "renavam", length = 11)
    private String renavam;

    @Column(name = "chassi", length = 17)
    private String chassi;
}
