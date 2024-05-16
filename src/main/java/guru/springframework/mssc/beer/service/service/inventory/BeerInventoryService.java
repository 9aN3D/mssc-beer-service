package guru.springframework.mssc.beer.service.service.inventory;

import java.util.UUID;

public interface BeerInventoryService {

    Integer getOnHandInventory(UUID beerId);

}
