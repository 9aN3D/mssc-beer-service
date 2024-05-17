package guru.cfg.brewery.model.messages;

import guru.cfg.brewery.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewBeerEvent extends BeerEvent implements Message {

    private static final long serialVersionUID = 103745828558417299L;

    public NewBeerEvent(BeerDto beer) {
        super(beer);
    }

}
