package guru.springframework.mssc.beer.service.events.producers;

import guru.cfg.brewery.model.messages.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JmsEventProducer implements EventProducer {

    private final JmsTemplate jmsTemplate;

    @Override
    public void produce(String destination, Message event) {
        jmsTemplate.convertAndSend(destination, event);
    }

}
