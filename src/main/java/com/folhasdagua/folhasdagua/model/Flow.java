package com.folhasdagua.folhasdagua.model;

import lombok.Data;

import javax.annotation.sql.DataSourceDefinitions;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Flow {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Integer id;
    @Column(
            nullable = false
    )
    private double amount;
    @Column(
            nullable = false
    )
    private String date;
    @Column(
            nullable = false
    )
    private String hour;
    @Column(
            nullable = false
    )
    private boolean status;
    @Column(
            nullable = false
    )
    private double duration;
    @ManyToOne
    @JoinColumn(
            name = "idSensor"
    )
    private Sensor sensor;

    public Flow() {
    }

}
