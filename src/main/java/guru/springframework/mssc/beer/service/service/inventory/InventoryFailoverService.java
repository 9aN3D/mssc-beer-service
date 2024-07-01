package guru.springframework.mssc.beer.service.service.inventory;

import guru.springframework.mssc.beer.service.service.inventory.model.BeerInventoryDto;

import java.util.List;
import java.util.UUID;

public interface InventoryFailoverService {

    List<BeerInventoryDto> getOnHandInventory(UUID beerId);

}
