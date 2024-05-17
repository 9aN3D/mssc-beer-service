package guru.springframework.mssc.beer.service.events.listeners;

import guru.springframework.mssc.beer.service.configuration.JmsConfiguration;
import guru.cfg.brewery.model.events.BrewBeerEvent;
import guru.springframework.mssc.beer.service.service.BrewBeerHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BrewBeerListener {

    private final BrewBeerHandler brewBeerHandler;

    @JmsListener(destination = JmsConfiguration.BREWING_REQUEST_QUEUE)
    public void on(BrewBeerEvent event) {
        brewBeerHandler.handle(event);
    }

}
