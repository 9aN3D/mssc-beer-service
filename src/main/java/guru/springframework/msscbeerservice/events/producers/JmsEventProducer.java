package guru.springframework.msscbeerservice.events.producers;

import guru.springframework.msscbeerservice.events.BeerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JmsEventProducer implements EventProducer {

    private final JmsTemplate jmsTemplate;

    @Override
    public void produce(String destination, BeerEvent event) {
        jmsTemplate.convertAndSend(destination, event);
    }

}
