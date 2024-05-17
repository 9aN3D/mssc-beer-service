package guru.springframework.mssc.beer.service.web.model;

import guru.cfg.brewery.model.BeerDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static guru.cfg.brewery.model.BeerStyle.ALE;

public class BaseTest {

    BeerDto getDto() {
        return BeerDto.builder()
                .id(UUID.randomUUID())
                .name("Beer name")
                .style(ALE)
                .createdTime(OffsetDateTime.now())
                .lastModifiedTime(OffsetDateTime.now())
                .price(new BigDecimal("4.33"))
                .upc("0631234200036")
                .build();
    }

}
