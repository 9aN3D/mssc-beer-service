package guru.springframework.mssc.beer.service.configuration;

import guru.cfg.brewery.model.messages.BrewBeerEvent;
import guru.cfg.brewery.model.messages.ValidateOrderRequest;
import guru.springframework.mssc.beer.service.infrastructure.MessageDispatcher;
import guru.springframework.mssc.beer.service.service.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MessageHandlerRegistration {

    private final MessageDispatcher messageDispatcher;
    private final MessageHandler messageHandler;

    @PostConstruct
    public void init() {
        messageDispatcher.registerHandler(BrewBeerEvent.class, messageHandler::handle);
        messageDispatcher.registerHandler(ValidateOrderRequest.class, messageHandler::handle);

        log.info("Registered message handlers");
    }

}
