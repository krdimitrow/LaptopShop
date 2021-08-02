package com.example.football.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;

public class TownNameDto {



    @Expose
    private String name;

    @Size(min = 2)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
