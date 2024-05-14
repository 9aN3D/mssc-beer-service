package guru.springframework.msscbeerservice.web.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static guru.springframework.msscbeerservice.web.model.BeerStyle.ALE;

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
