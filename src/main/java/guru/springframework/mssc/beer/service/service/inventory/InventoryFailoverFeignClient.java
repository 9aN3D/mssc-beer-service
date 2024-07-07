package guru.springframework.mssc.beer.service.service.inventory;

import guru.springframework.mssc.beer.service.service.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "inventory-failover-service", fallbackFactory = InventoryFailoverFeignClientFallbackFactory.class)
public interface InventoryFailoverFeignClient {

    @RequestMapping(method = GET, value = "/inventory-failover")
    List<BeerInventoryDto> getOnHandInventory(UUID beerId);

}
