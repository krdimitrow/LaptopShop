package com.example.football.models.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatXmlDto {

    @XmlElement

    private long id;

    @NotNull
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
