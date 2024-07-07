package guru.springframework.mssc.beer.service.service.inventory;

import guru.springframework.mssc.beer.service.service.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "beer-inventory-service", fallback = FeignClientInventoryFailoverService.class)
public interface BeerInventoryServiceFeignClient {

    @RequestMapping(method = GET, value = "${sfg.brewery.beer-inventory-service-path}")
    List<BeerInventoryDto> getOnHandInventory(@PathVariable("beerId") UUID beerId);

}
