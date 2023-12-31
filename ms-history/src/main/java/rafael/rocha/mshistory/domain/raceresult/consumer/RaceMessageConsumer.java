package rafael.rocha.mshistory.domain.raceresult.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rafael.rocha.mshistory.domain.raceresult.document.RaceResultDocument;
import rafael.rocha.mshistory.domain.raceresult.repository.RaceResultRepository;

import java.util.Date;

@Component
public class RaceMessageConsumer {

    @Autowired
    private RaceResultRepository raceResultRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "raceResultQueue")
    public void receiveRaceMessage(String raceData) throws JsonProcessingException {
        RaceResultDocument raceResultDocument = objectMapper.readValue(raceData, RaceResultDocument.class);

        raceResultDocument.setRaceDate(new Date());

        raceResultRepository.save(raceResultDocument);
    }
}
