package guru.springframework.mssc.beer.service.service.inventory;

import guru.springframework.mssc.beer.service.service.inventory.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class FeignClientInventoryFailoverService implements InventoryFailoverService {

    private final InventoryFailoverFeignClient inventoryFailoverFeignClient;

    @Override
    public List<BeerInventoryDto> getOnHandInventory(UUID beerId) {
        return inventoryFailoverFeignClient.getOnHandInventory(beerId);
    }

}
