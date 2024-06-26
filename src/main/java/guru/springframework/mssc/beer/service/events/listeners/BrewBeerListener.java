package guru.springframework.mssc.beer.service.events.listeners;

import guru.cfg.brewery.model.messages.BrewBeerEvent;
import guru.springframework.mssc.beer.service.configuration.JmsConfiguration;
import guru.springframework.mssc.beer.service.infrastructure.MessageDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BrewBeerListener {

    private final MessageDispatcher messageDispatcher;

    @JmsListener(destination = JmsConfiguration.BREWING_REQUEST_QUEUE)
    public void on(BrewBeerEvent event) {
        messageDispatcher.dispatch(event);
    }

}
