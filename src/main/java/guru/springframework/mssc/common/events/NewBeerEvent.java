package guru.springframework.mssc.common.events;

import guru.springframework.mssc.beer.service.web.model.BeerDto;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class NewBeerEvent extends BeerEvent implements Serializable {

    private static final long serialVersionUID = 103745828558417299L;

    public NewBeerEvent(BeerDto beer) {
        super(beer);
    }

}
