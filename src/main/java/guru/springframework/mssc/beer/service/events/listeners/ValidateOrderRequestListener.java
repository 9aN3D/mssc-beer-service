package guru.springframework.mssc.beer.service.events.listeners;

import guru.cfg.brewery.model.messages.ValidateOrderRequest;
import guru.springframework.mssc.beer.service.infrastructure.MessageDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static guru.springframework.mssc.beer.service.configuration.JmsConfiguration.VALIDATING_ORDER_REQUEST_QUEUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateOrderRequestListener {

    private final MessageDispatcher messageDispatcher;

    @JmsListener(destination = VALIDATING_ORDER_REQUEST_QUEUE)
    public void on(ValidateOrderRequest request) {
        messageDispatcher.dispatch(request);
    }

}
