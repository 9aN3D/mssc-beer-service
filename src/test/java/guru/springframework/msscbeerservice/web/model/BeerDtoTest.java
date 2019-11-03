package guru.springframework.msscbeerservice.web.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@Slf4j
@JsonTest
class BeerDtoTest extends BaseTest {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testSerializeDto() throws JsonProcessingException {
      BeerDto beerDto = getDto();

      String jsonString = objectMapper.writeValueAsString(beerDto);

      log.info("JSON - {}", jsonString);
    }

    @Test
    void testDeserialize() throws JsonProcessingException {
        String json = "{\"id\":\"6fb243ed-8c20-419c-8026-0b1a2e96dfaa\",\"version\":null,\"createTime\":\"2019-11-03T16:29:25.501179+01:00\",\"lastModifiedTime\":\"2019-11-03T16:29:25.502344+01:00\",\"name\":\"Beer name\",\"style\":\"Ale\",\"upc\":123123123123,\"price\":4.33,\"quantityOnHand\":null}";

        BeerDto beerDto = objectMapper.readValue(json, BeerDto.class);

        log.info("Beer DTO - {}", beerDto);
    }

}
