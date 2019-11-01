package guru.springframework.msscbeerservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDto {

    private UUID id;

    private Integer version;

    private OffsetDateTime createTime;

    private OffsetDateTime lastModifiedTime;

    private String beerName;

    private BeerStyle beerStyle;

    private Long upc;

    private BigDecimal price;

    private Integer quantityOnHand;

}
