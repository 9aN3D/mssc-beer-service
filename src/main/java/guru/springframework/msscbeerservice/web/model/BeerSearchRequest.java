package guru.springframework.msscbeerservice.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static java.lang.Boolean.TRUE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerSearchRequest implements Serializable {

    private static final long serialVersionUID = -5467535489600090814L;

    private String name;

    private BeerStyle style;

    private String upc;

    private Boolean showInventoryOnHand;

    @JsonIgnore
    public boolean isShowInventoryOnHand() {
        return TRUE.equals(showInventoryOnHand);
    }

}
