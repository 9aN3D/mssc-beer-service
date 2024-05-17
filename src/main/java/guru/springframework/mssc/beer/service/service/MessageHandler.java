package guru.springframework.mssc.beer.service.service;

import guru.cfg.brewery.model.BeerDto;
import guru.cfg.brewery.model.BeerOrderDto;
import guru.cfg.brewery.model.BeerOrderLineDto;
import guru.cfg.brewery.model.messages.BrewBeerEvent;
import guru.cfg.brewery.model.messages.Message;
import guru.cfg.brewery.model.messages.NewBeerEvent;
import guru.cfg.brewery.model.messages.ValidateOrderRequest;
import guru.cfg.brewery.model.messages.ValidateOrderResult;
import guru.springframework.mssc.beer.service.domain.Beer;
import guru.springframework.mssc.beer.service.events.producers.EventProducer;
import guru.springframework.mssc.beer.service.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

public interface MessageHandler {

    void handle(BrewBeerEvent event);

    void handle(ValidateOrderRequest request);

    interface MessageHandlerMethod<T extends Message> {

        void handle(T event);

    }

    @Slf4j
    @Service
    @RequiredArgsConstructor
    class BeerMessageHandler implements MessageHandler {

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

        @Override
        public void handle(ValidateOrderRequest request) {
            log.trace("Handling ValidateOrderRequest {}", request);

            BeerOrderDto beerOrder = requireNonNull(request.getBeerOrder());
            Set<String> upcBeerOrderLines = requireNonNull(beerOrder.getBeerOrderLines()).stream()
                    .map(BeerOrderLineDto::getUpc)
                    .collect(toSet());

            ValidateOrderResult result = ValidateOrderResult.builder()
                    .orderId(beerOrder.getId())
                    .isValid(isValidOrderUpcIds(upcBeerOrderLines))
                    .build();

            eventProducer.produceToValidatingOrderResultQueue(result);

            log.info("Handled ValidateOrderRequest {result: {}}", result);
        }

        private boolean isValidOrderUpcIds(Set<String> upcIds) {
            if (upcIds.isEmpty()) {
                return false;
            }
            return beerRepository.findByUpcIn(upcIds)
                    .stream()
                    .map(Beer::getUpc)
                    .collect(toSet())
                    .containsAll(upcIds);
        }

    }

}
