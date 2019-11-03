package guru.springframework.msscbeerservice.web.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BaseTest {

    BeerDto getDto() {
        return BeerDto.builder()
                .id(UUID.randomUUID())
                .name("Beer name")
                .style("Ale")
                .createTime(OffsetDateTime.now())
                .lastModifiedTime(OffsetDateTime.now())
                .price(new BigDecimal("4.33"))
                .upc(123123123123L)
                .build();
    }

}
