package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatRootDto {

    @XmlElement(name = "stat")
    List<StatDto> stats;

    public List<StatDto> getStats() {
        return stats;
    }

    public void setStats(List<StatDto> stats) {
        this.stats = stats;
    }
}
