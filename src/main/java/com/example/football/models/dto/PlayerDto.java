package com.example.football.models.dto;


import com.example.football.models.entity.en.Position;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerDto {

    @XmlElement(name = "first-name")
    private String firstName;
    @XmlElement(name = "last-name")
    private String lastName;
    @XmlElement
    private String email;
    @XmlElement(name = "birth-date")
    private String birthDate;
    @XmlElement
    private Position position;
    @XmlElement(name = "stat")
    private StatXmlDto stat;
    @XmlElement
    private TownXmlDto town;
    @XmlElement
    private TeamXmlDto team;

    @Size(min = 2)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Size(min = 2)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Enumerated(EnumType.STRING)
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public StatXmlDto getStat() {
        return stat;
    }

    public void setStat(StatXmlDto stat) {
        this.stat = stat;
    }

    public TownXmlDto getTown() {
        return town;
    }

    public void setTown(TownXmlDto town) {
        this.town = town;
    }

    public TeamXmlDto getTeam() {
        return team;
    }

    public void setTeam(TeamXmlDto team) {
        this.team = team;
    }
}
