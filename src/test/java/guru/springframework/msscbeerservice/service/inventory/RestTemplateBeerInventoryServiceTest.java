package guru.springframework.msscbeerservice.service.inventory;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled // utility for manual testing
@SpringBootTest
class RestTemplateBeerInventoryServiceTest {

    @Autowired
    BeerInventoryService beerInventoryService;

    @Test
    void getOnHandInventory() {

        //todo evolve to use UPC
        Integer qoh = beerInventoryService.getOnHandInventory(UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb"));

        assertNotNull("Null qoh returned", qoh);
        assertEquals(qoh, 150);
    }

}
