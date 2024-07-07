package guru.springframework.mssc.beer.service.service.inventory;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
class InventoryFailoverFeignClientFallbackFactory implements FallbackFactory<InventoryFailoverFeignClient> {

    @Override
    public InventoryFailoverFeignClient create(Throwable throwable) {
        log.warn("Getting on hand inventory from inventory-failover-service - (FeignClient fallback)", throwable);
        return beerId -> new ArrayList<>();
    }

}
