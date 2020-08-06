package spring.altarok.msscbeerservice.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.altarok.msscbeerservice.domain.Beer;
import spring.altarok.msscbeerservice.repositories.BeerRepository;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        if (beerRepository.count() == 0) {
            loadBeerObjects();
        }
    }

    private void loadBeerObjects() {
        beerRepository.save(Beer.builder()
                    .beerName("BeerHut")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(10)
                    .quantityOnHand(4)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("13.99"))
                    .build());

        beerRepository.save(Beer.builder()
                .beerName("McBeer")
                .beerStyle("IPA")
                .quantityToBrew(200)
                .quantityOnHand(24)
                .upc(BEER_1_UPC)
                .price(new BigDecimal("1.99"))
                .build());

        beerRepository.save(Beer.builder()
                .beerName("CocaBeer")
                .beerStyle("PALE_ALE")
                .quantityToBrew(12)
                .quantityOnHand(5)
                .upc(BEER_3_UPC)
                .price(new BigDecimal("11.99"))
                .build());

            log.info("Loaded Beers: " + beerRepository.count());
    }
}
