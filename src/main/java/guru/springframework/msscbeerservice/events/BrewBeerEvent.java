package guru.springframework.msscbeerservice.events;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent implements Serializable {

    private static final long serialVersionUID = 2021611001580925592L;

    public BrewBeerEvent(BeerDto beer) {
        super(beer);
    }

}
