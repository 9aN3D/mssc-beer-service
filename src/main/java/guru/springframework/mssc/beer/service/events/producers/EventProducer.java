package guru.springframework.mssc.beer.service.events.producers;

import guru.cfg.brewery.model.messages.BrewBeerEvent;
import guru.cfg.brewery.model.messages.Message;
import guru.cfg.brewery.model.messages.NewBeerEvent;
import guru.cfg.brewery.model.messages.ValidateOrderResult;
import guru.springframework.mssc.beer.service.configuration.JmsConfiguration;

public interface EventProducer {

    void produce(String destination, Message event);

    default void produceToBrewingRequestQueue(BrewBeerEvent event) {
        produce(JmsConfiguration.BREWING_REQUEST_QUEUE, event);
    }

    default void produceToNewInventoryQueue(NewBeerEvent event) {
        produce(JmsConfiguration.NEW_INVENTORY_QUEUE, event);
    }

    default void produceToValidatingOrderResultQueue(ValidateOrderResult result) {
        produce(JmsConfiguration.VALIDATING_ORDER_RESULT_QUEUE, result);
    }

}
