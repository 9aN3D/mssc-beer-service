package guru.springframework.mssc.beer.service.service.inventory;

import guru.springframework.mssc.beer.service.service.inventory.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Profile("local-discovery")
@Slf4j
@Component
@RequiredArgsConstructor
public class FeignClientBeerInventoryService implements BeerInventoryService {

    private final BeerInventoryServiceFeignClient beerInventoryServiceFeignClient;

    @Override
    public Integer getOnHandInventory(UUID beerId) {
        log.trace("Getting on hand inventory {beerId: {}} - (FeignClient)", beerId);

        Integer result = beerInventoryServiceFeignClient.getOnHandInventory(beerId)
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();

        log.info("Got on hand inventory {beerId: {}, result: {}} - (FeignClient)", beerId, result);
        return result;
    }

}
