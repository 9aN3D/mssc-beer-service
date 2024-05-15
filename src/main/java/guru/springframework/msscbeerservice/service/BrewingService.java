package guru.springframework.msscbeerservice.service;

import guru.springframework.msscbeerservice.configuration.JmsConfiguration;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.event.producer.EventProducer;
import guru.springframework.msscbeerservice.events.BrewBeerEvent;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.service.inventory.BeerInventoryService;
import guru.springframework.msscbeerservice.web.mapper.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final EventProducer eventProducer;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {
        log.trace("Start check for low inventory");
        List<Beer> beers = beerRepository.findAll();

        for (Beer beer : beers) {
            Integer onHandInventory = beerInventoryService.getOnHandInventory(beer.getId());

            if (beer.getMinOnHand() >= onHandInventory) {
                eventProducer.produce(JmsConfiguration.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDtoWithoutQuantityOnHand(beer)));
            }
        }
        log.info("Ended check for low inventory");
    }

}
