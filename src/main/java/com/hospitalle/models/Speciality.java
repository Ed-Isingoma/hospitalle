package com.hospitalle.models;

import javax.persistence.*;

@Entity
@Table(name="Specialities")
public class Speciality {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private int hourly_price;

    @ManyToOne @JoinColumn(name="doctor")
    private Auth doctor;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHourlyPrice() {
        return hourly_price;
    }

    public Auth getDoctor() {
        return doctor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHourlyPrice(int hourly_price) {
        this.hourly_price = hourly_price;
    }

    public void setDoctor(Auth doctor) {
        this.doctor = doctor;
    }
}
