package guru.springframework.msscbeerservice.events.producers;

import guru.springframework.msscbeerservice.configuration.JmsConfiguration;
import guru.springframework.msscbeerservice.events.BeerEvent;
import guru.springframework.msscbeerservice.events.BrewBeerEvent;
import guru.springframework.msscbeerservice.events.NewBeerEvent;

public interface EventProducer {

    void produce(String destination, BeerEvent event);

    default void produceToBrewingRequestQueue(BrewBeerEvent event) {
        produce(JmsConfiguration.BREWING_REQUEST_QUEUE, event);
    }

    default void produceToNewInventoryQueue(NewBeerEvent event) {
        produce(JmsConfiguration.NEW_INVENTORY_QUEUE, event);
    }

}
