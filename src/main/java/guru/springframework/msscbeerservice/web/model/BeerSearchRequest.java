package guru.springframework.msscbeerservice.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.Boolean.TRUE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerSearchRequest {

    private String name;

    private BeerStyle style;

    private Boolean showInventoryOnHand;

    @JsonIgnore
    public boolean isShowInventoryOnHand() {
        return TRUE.equals(showInventoryOnHand);
    }

}
