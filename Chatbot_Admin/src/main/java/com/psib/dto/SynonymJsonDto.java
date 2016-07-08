package com.psib.dto;

import com.psib.model.Synonym;

public class SynonymJsonDto {

    public SynonymJsonDto(int number, Synonym synonym) {
        this.number = number;
        this.id = synonym.getId();
        this.name = synonym.getName();
    }

    private long number;

    private int id;

    private String name;

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
