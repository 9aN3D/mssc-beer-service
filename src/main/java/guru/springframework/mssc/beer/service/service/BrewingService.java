package guru.springframework.mssc.beer.service.service;

import guru.springframework.mssc.beer.service.domain.Beer;
import guru.springframework.mssc.beer.service.events.producers.EventProducer;
import guru.springframework.mssc.common.events.BrewBeerEvent;
import guru.springframework.mssc.beer.service.repository.BeerRepository;
import guru.springframework.mssc.beer.service.service.inventory.BeerInventoryService;
import guru.springframework.mssc.beer.service.web.mapper.BeerMapper;
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
                eventProducer.produceToBrewingRequestQueue(new BrewBeerEvent(beerMapper.beerToBeerDtoWithoutQuantityOnHand(beer)));
            }
        }
        log.info("Ended check for low inventory");
    }

}
