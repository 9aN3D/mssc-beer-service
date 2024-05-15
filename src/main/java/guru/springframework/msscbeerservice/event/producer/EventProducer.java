package guru.springframework.msscbeerservice.event.producer;

import guru.springframework.msscbeerservice.events.BeerEvent;

public interface EventProducer {

    void produce(String destination, BeerEvent event);

}
