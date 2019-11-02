package guru.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "guru.springframework.msscbeerservice.web.mapper")
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    BeerRepository repository;

    private BeerDto validBeer;

    private final static String API_BEER_URL = "/api/v1/beer/";

    @BeforeEach
    void setUp() {
        validBeer = BeerDto.builder()
                .name("Test service beer")
                .style("PILSNER")
                .price(new BigDecimal("3.56"))
                .upc(123123123123L)
                .build();
    }

    @Test
    void getById() throws Exception {
        given(repository.findById(any())).willReturn(Optional.of(Beer.builder().build()));

        mockMvc.perform(
                get(API_BEER_URL+ UUID.randomUUID().toString())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void save() throws Exception {
        String beerJson = mapper.writeValueAsString(validBeer);

        mockMvc.perform(
                post(API_BEER_URL)
                        .contentType(APPLICATION_JSON)
                        .content(beerJson))
                .andExpect(status().isCreated());
    }

    @Test
    void update() throws Exception {
        given(repository.findById(any())).willReturn(Optional.of(Beer.builder().build()));
        String beerJson = mapper.writeValueAsString(validBeer);

        mockMvc.perform(
                put(API_BEER_URL + UUID.randomUUID().toString())
                        .contentType(APPLICATION_JSON)
                        .content(beerJson))
                .andExpect(status().isNoContent());
    }
}
