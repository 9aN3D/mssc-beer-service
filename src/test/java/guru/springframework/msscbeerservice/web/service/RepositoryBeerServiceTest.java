package guru.springframework.msscbeerservice.web.service;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.exception.BeerNotFoundException;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.service.RepositoryBeerService;
import guru.springframework.msscbeerservice.web.mapper.BeerMapperImpl;
import guru.springframework.msscbeerservice.web.mapper.DataMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static guru.springframework.msscbeerservice.web.model.BeerStyle.PILSNER;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RepositoryBeerServiceTest {

    RepositoryBeerService beerService;

    @Mock
    BeerRepository repository;

    @InjectMocks
    BeerMapperImpl mapper;

    @Mock
    DataMapper dataMapper;

    private Beer beer;

    private final UUID BEER_ID = UUID.fromString("aa89a818-5494-4592-8edf-7c3c629eb43e");

    @BeforeEach
    void setUp() {
        beerService = new RepositoryBeerService(mapper, repository);

        beer = buildBeer();
    }

    @Test
    void shouldGetBeerByIdWhenIdIsCorrect() {
        when(repository.findById(any(UUID.class))).thenReturn(of(beer));

        BeerDto beerDtoReturned = beerService.getById(BEER_ID);

        assertEquals(beerDtoReturned.getId(), beer.getId());
        assertEquals(beerDtoReturned.getName(), beer.getName());
        assertEquals(beerDtoReturned.getStyle(), beer.getStyle());
        assertEquals(beerDtoReturned.getPrice(), beer.getPrice());
        assertEquals(beerDtoReturned.getUpc(), beer.getUpc());
        assertNotNull("Null beer dto returned", beerDtoReturned);

        verify(repository, times(1)).findById(any(UUID.class));
        verify(repository, never()).findAll();
    }

    @Test
    void shouldGetBeers() {
        List<Beer> beers = List.of(beer);
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(beers));

        Page<BeerDto> pageReturned = beerService.find(PageRequest.of(0, 1));

        assertEquals(pageReturned.getTotalElements(), beers.size());
        assertEquals(pageReturned.getContent().get(0).getId(), beer.getId());
        assertNotNull("Null page returned", pageReturned);

        verify(repository, times(1)).findAll(any(Pageable.class));
        verify(repository, never()).findAll();
    }

    @Test
    void shouldThrowExceptionWhenIdIsNotCorrect() {
        when(repository.findById(any(UUID.class))).thenReturn(empty());

        assertThrows(BeerNotFoundException.class, () ->
                beerService.getById(UUID.fromString("11111111-5494-4592-8edf-7c3c629eb43e")));
    }

    private Beer buildBeer() {
        return Beer.builder()
                .id(BEER_ID)
                .name("Test service beer")
                .style(PILSNER)
                .price(new BigDecimal("3.56"))
                .upc("0631234200036")
                .build();
    }

}
