package guru.springframework.mssc.beer.service.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.mssc.beer.service.service.BeerService;
import guru.cfg.brewery.model.BeerDto;
import guru.cfg.brewery.model.BeerPagedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static guru.cfg.brewery.model.BeerStyle.PILSNER;
import static java.lang.Boolean.FALSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.guru", uriPort = 80)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    BeerService beerService;

    private BeerDto validBeer;

    @BeforeEach
    void setUp() {
        validBeer = BeerDto.builder()
                .name("Test service beer")
                .style(PILSNER)
                .price(new BigDecimal("3.56"))
                .upc("0631234200036")
                .build();
    }

    @Test
    void getById() throws Exception {
        given(beerService.getById(any(), anyBoolean())).willReturn(BeerDto.builder().build());

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(
                        get("/api/v1/beers/{beerId}", UUID.randomUUID().toString())
                                .param("showInventoryOnHand", FALSE.toString())
                                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        requestParameters(
                                parameterWithName("showInventoryOnHand").description("Show beer with quantity on hand")
                        ),
                        responseFields(
                                fields.withPath("id").description("Id of beer"),
                                fields.withPath("version").description("Version number"),
                                fields.withPath("createdTime").description("Time created"),
                                fields.withPath("lastModifiedTime").description("Time updated"),
                                fields.withPath("name").description("Beer name"),
                                fields.withPath("style").description("Beer style"),
                                fields.withPath("upc").description("UPC of beer"),
                                fields.withPath("price").description("Price"),
                                fields.withPath("quantityOnHand").description("Quantity on hand")
                        )));
    }

    @Test
    void getByUpc() throws Exception {
        given(beerService.getByUpc(any())).willReturn(BeerDto.builder().build());

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(
                        get("/api/v1/beers/upc/{upc}", validBeer.getUpc())
                                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-upc-get",
                        pathParameters(
                                parameterWithName("upc").description("UPC of desired beer to get.")
                        ),
                        responseFields(
                                fields.withPath("id").description("Id of beer"),
                                fields.withPath("version").description("Version number"),
                                fields.withPath("createdTime").description("Time created"),
                                fields.withPath("lastModifiedTime").description("Time updated"),
                                fields.withPath("name").description("Beer name"),
                                fields.withPath("style").description("Beer style"),
                                fields.withPath("upc").description("UPC of beer"),
                                fields.withPath("price").description("Price"),
                                fields.withPath("quantityOnHand").description("Quantity on hand")
                        )));
    }

    @Test
    void save() throws Exception {
        String beerJson = mapper.writeValueAsString(validBeer);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(
                        post("/api/v1/beers/")
                                .contentType(APPLICATION_JSON)
                                .content(beerJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-new",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdTime").ignored(),
                                fields.withPath("lastModifiedTime").ignored(),
                                fields.withPath("name").description("Beer name"),
                                fields.withPath("style").description("Beer style"),
                                fields.withPath("upc").description("UPC of beer"),
                                fields.withPath("price").description("Price"),
                                fields.withPath("quantityOnHand").ignored()
                        )));
    }

    @Test
    void update() throws Exception {
        String beerJson = mapper.writeValueAsString(validBeer);

        mockMvc.perform(
                        put("/api/v1/beers/{beerId}", UUID.randomUUID().toString())
                                .contentType(APPLICATION_JSON)
                                .content(beerJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void find() throws Exception {
        given(beerService.find(any(), any())).willReturn(new BeerPagedList(new ArrayList<>()));

        mockMvc.perform(
                        get("/api/v1/beers")
                                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }

    }

}
