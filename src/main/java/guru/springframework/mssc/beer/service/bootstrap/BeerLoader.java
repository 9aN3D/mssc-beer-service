package guru.springframework.mssc.beer.service.bootstrap;

import guru.springframework.mssc.beer.service.domain.Beer;
import guru.springframework.mssc.beer.service.repository.BeerRepository;
import guru.springframework.mssc.beer.service.web.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;

@Component
@RequiredArgsConstructor
public class BeerLoader implements CommandLineRunner {

    private static final String BEER_1_UPC = "0631234200036";
    private static final String BEER_2_UPC = "0631234300019";
    private static final String BEER_3_UPC = "0083783375213";

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        if (beerRepository.count() == 0) {
            loadBeers();
        }
    }

    private void loadBeers() {
        beerRepository.saveAll(beers());
    }

    private List<Beer> beers() {
        Beer beer1 = Beer.builder()
                .name("Mango Bobs")
                .style(BeerStyle.DUNKEL)
                .minOnHand(5)
                .quantityToBrew(200)
                .price(new BigDecimal("9.78"))
                .upc(BEER_1_UPC)
                .build();

        Beer beer2 = Beer.builder()
                .name("Galaxy Cat")
                .style(BeerStyle.ALE)
                .minOnHand(12)
                .quantityToBrew(100)
                .price(new BigDecimal("12.95"))
                .upc(BEER_2_UPC)
                .build();

        Beer beer3 = Beer.builder()
                .name("Pinball Porter")
                .style(BeerStyle.PORTER)
                .minOnHand(8)
                .quantityToBrew(240)
                .price(new BigDecimal("10.95"))
                .upc(BEER_3_UPC)
                .build();

        return asList(beer1, beer2, beer3);
    }

}
