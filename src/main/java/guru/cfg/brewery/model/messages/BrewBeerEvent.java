package guru.cfg.brewery.model.messages;

import guru.cfg.brewery.model.BeerDto;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent implements Message {

    private static final long serialVersionUID = 2021611001580925592L;

    public BrewBeerEvent(BeerDto beer) {
        super(beer);
    }

}
