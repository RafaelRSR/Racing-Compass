package rafael.rocha.mshistory.race.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rafael.rocha.mshistory.race.document.RaceDocument;
import rafael.rocha.mshistory.race.repository.RaceRepository;

import java.util.Date;

@Component
public class RaceMessageConsumer {

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "ms-races-queue")
    public void receiveRaceMessage(String raceData) throws JsonProcessingException {
        RaceDocument race = objectMapper.readValue(raceData, RaceDocument.class);

        race.setRaceDate(new Date());
        raceRepository.save(race);
    }
}
