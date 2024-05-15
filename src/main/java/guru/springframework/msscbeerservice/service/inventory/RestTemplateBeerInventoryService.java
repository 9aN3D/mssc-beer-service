package guru.springframework.msscbeerservice.service.inventory;

import guru.springframework.msscbeerservice.service.inventory.model.BeerInventoryDto;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
public class RestTemplateBeerInventoryService implements BeerInventoryService {

    private final RestTemplate restTemplate;

    @Setter
    private String beerInventoryServicePath;

    public RestTemplateBeerInventoryService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Integer getOnHandInventory(UUID beerId) {
        log.trace("Getting on hand inventory {beerId: {}}", beerId);

        ResponseEntity<List<BeerInventoryDto>> response = restTemplate
                .exchange(beerInventoryServicePath, GET, null, new ParameterizedTypeReference<>() {
                }, beerId);

        Integer result = Objects.requireNonNull(response.getBody())
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();

        log.info("Got on hand inventory {beerId: {}, result: {}}", beerId, result);
        return result;
    }

}
