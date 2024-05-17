package guru.cfg.brewery.model.messages;

import guru.cfg.brewery.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerEvent implements Message {

    private static final long serialVersionUID = 2315887405398232148L;

    private BeerDto beer;

}
