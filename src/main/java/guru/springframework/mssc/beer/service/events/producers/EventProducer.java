package guru.springframework.mssc.beer.service.events.producers;

import guru.springframework.mssc.beer.service.configuration.JmsConfiguration;
import guru.springframework.mssc.common.events.BrewBeerEvent;
import guru.springframework.mssc.common.events.NewBeerEvent;
import guru.springframework.mssc.common.events.BeerEvent;

public interface EventProducer {

    void produce(String destination, BeerEvent event);

    default void produceToBrewingRequestQueue(BrewBeerEvent event) {
        produce(JmsConfiguration.BREWING_REQUEST_QUEUE, event);
    }

    default void produceToNewInventoryQueue(NewBeerEvent event) {
        produce(JmsConfiguration.NEW_INVENTORY_QUEUE, event);
    }

}
