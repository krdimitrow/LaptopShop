package com.example.football.service.impl;

import com.example.football.models.dto.StatRootDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StatServiceImpl implements StatService {

    private static final String STATS_FILE_PATH = "src/main/resources/files/xml/stats.xml";

    private final StatRepository statRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public StatServiceImpl(StatRepository statRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.statRepository = statRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STATS_FILE_PATH));
    }

    @Override
    public String importStats() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();




        xmlParser.fromFile(STATS_FILE_PATH, StatRootDto.class).getStats()
                .stream()
                .filter(statDto -> {
                    boolean isValid = validationUtil.isValid(statDto);

                    sb
                            .append(isValid ? String.format("Successfully imported Stat %.2f - %.2f - %.2f",
                                    statDto.getShooting(),  statDto.getPassing(),  statDto.getEndurance())
                                    : "Invalid Stat")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(statDto -> modelMapper.map(statDto, Stat.class))
                .forEach(statRepository::save);


        return sb.toString();
    }

    @Override
    public Stat findById(Long id) {
        return statRepository.findById(id).orElse(null);
    }
}
