package guru.springframework.msscbeerservice.bootstrap;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static guru.springframework.msscbeerservice.web.model.BeerStyle.DUNKEL;
import static guru.springframework.msscbeerservice.web.model.BeerStyle.ALE;
import static guru.springframework.msscbeerservice.web.model.BeerStyle.PORTER;
import static java.util.Arrays.asList;

@Component
@RequiredArgsConstructor
public class BeerLoader implements CommandLineRunner {

    private static final Long BEER_1_UPC = 163123420003L;
    private static final Long BEER_2_UPC = 163123430001L;
    private static final Long BEER_3_UPC = 108378337521L;

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
                .style(DUNKEL)
                .minOnHand(5)
                .quantityToBrew(200)
                .price(new BigDecimal("9.78"))
                .upc(BEER_1_UPC)
                .build();

        Beer beer2 = Beer.builder()
                .name("Galaxy Cat")
                .style(ALE)
                .minOnHand(12)
                .quantityToBrew(100)
                .price(new BigDecimal("12.95"))
                .upc(BEER_2_UPC)
                .build();

        Beer beer3 = Beer.builder()
                .name("Pinball Porter")
                .style(PORTER)
                .minOnHand(8)
                .quantityToBrew(240)
                .price(new BigDecimal("10.95"))
                .upc(BEER_3_UPC)
                .build();

        return asList(beer1, beer2, beer3);
    }

}