package guru.springframework.msscbeerservice.service;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.events.BrewBeerEvent;
import guru.springframework.msscbeerservice.events.NewBeerEvent;
import guru.springframework.msscbeerservice.events.producers.EventProducer;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

public interface BrewBeerHandler {

    void handle(BrewBeerEvent event);


    @Slf4j
    @Service
    @RequiredArgsConstructor
    class DefaultBrewBeerHandler implements BrewBeerHandler {

        private final BeerRepository beerRepository;
        private final EventProducer eventProducer;

        @Override
        @Transactional
        public void handle(BrewBeerEvent event) {
            log.trace("Handling BrewBeerEvent {}", event);

            BeerDto beerDto = event.getBeer();

            Beer beer = beerRepository.getOne(beerDto.getId());

            beerDto.setQuantityOnHand(beer.getQuantityToBrew());

            NewBeerEvent newBeerEvent = new NewBeerEvent(beerDto);
            eventProducer.produceToNewInventoryQueue(newBeerEvent);

            log.info("Handled BrewBeerEvent {newBeerEvent: {}}", newBeerEvent);
        }

    }

}
