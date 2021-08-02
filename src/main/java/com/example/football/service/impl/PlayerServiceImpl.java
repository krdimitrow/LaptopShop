package com.example.football.service.impl;

import com.example.football.models.dto.PlayerDto;
import com.example.football.models.dto.PlayerRootDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.repository.PlayerRepository;
import com.example.football.service.PlayerService;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String PLAYER_FILE_PATH = "src/main/resources/files/xml/players.xml";

    private final PlayerRepository playerRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final TownService townService;
    private final TeamService teamService;
    private final StatService statService;


    public PlayerServiceImpl(PlayerRepository playerRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser, TownService townService, TeamService teamService, StatService statService) {
        this.playerRepository = playerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.townService = townService;
        this.teamService = teamService;
        this.statService = statService;
    }


    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYER_FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {

        StringBuilder sb = new StringBuilder();


        xmlParser.fromFile(PLAYER_FILE_PATH, PlayerRootDto.class).getPlayers()
                .stream()
                .filter(playerDto -> {
                    boolean isValid = validationUtil.isValid(playerDto)
                            && !isExist(playerDto.getEmail())
                            && (statService.findById(playerDto.getStat().getId()) != null);

                    sb
                            .append(isValid ? String.format("Successfully imported Player %s %s - %s",
                                    playerDto.getFirstName(), playerDto.getLastName(), playerDto.getPosition().toString())
                                    : "Invalid Player").append(System.lineSeparator());


                    return isValid;
                })
                .map(playerDto -> {
                    Player player = modelMapper.map(playerDto, Player.class);
                    player.setTown(townService.findTownByName(playerDto.getTown().getName()));
                    player.setTeam(teamService.findByName(playerDto.getTeam().getName()));
                    player.setStat(statService.findById(playerDto.getStat().getId()));

                    return player;

                })
                .map(player -> modelMapper.map(player, Player.class))
                .forEach(playerRepository::save);


        return sb.toString();
    }

    private boolean isExist(String email) {
        return findPlayer(email) != null;
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder sb = new StringBuilder();

        playerRepository.findTheBestPlayersOrderedByShootingThenPassingThenEnduranceThenLastName()
                .forEach(player -> {

                    sb
                            .append(String.format("Player - %s %s\n" +
                                            "\tPosition - %s\n" +
                                            "\tTeam - %s\n" +
                                            "\tStadium - %s", player.getFirstName(),
                                    player.getLastName(),
                                    player.getPosition().toString(),
                                    player.getTeam().getName(),
                                    player.getTeam().getStadiumName()))
                            .append(System.lineSeparator());


                });


        return sb.toString();
    }

    @Override
    public Player findPlayer(String email) {
        return playerRepository.findByEmail(email);
    }


}
