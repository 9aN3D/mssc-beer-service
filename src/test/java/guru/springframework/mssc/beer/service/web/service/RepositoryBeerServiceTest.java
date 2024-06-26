package guru.springframework.mssc.beer.service.web.service;

import guru.springframework.mssc.beer.service.domain.Beer;
import guru.springframework.mssc.beer.service.exception.BeerNotFoundException;
import guru.springframework.mssc.beer.service.repository.BeerRepository;
import guru.springframework.mssc.beer.service.service.RepositoryBeerService;
import guru.springframework.mssc.beer.service.service.inventory.BeerInventoryService;
import guru.springframework.mssc.beer.service.web.mapper.BeerMapperImpl;
import guru.springframework.mssc.beer.service.web.mapper.BeerMapperImpl_;
import guru.springframework.mssc.beer.service.web.mapper.DataMapper;
import guru.cfg.brewery.model.BeerDto;
import guru.cfg.brewery.model.BeerSearchRequest;
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
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static guru.cfg.brewery.model.BeerStyle.PILSNER;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class RepositoryBeerServiceTest {

    RepositoryBeerService beerService;

    BeerMapperImpl beerMapper;

    @Mock
    BeerRepository repository;

    @InjectMocks
    BeerMapperImpl_ mapper;

    @Mock
    DataMapper dataMapper;

    @Mock
    BeerInventoryService beerInventoryService;

    private Beer beer;

    private final UUID BEER_ID = UUID.fromString("aa89a818-5494-4592-8edf-7c3c629eb43e");
    private final String BEER_UPC = "0631234200036";
    private final Integer QUANTITY_ON_HAND = 10;

    @BeforeEach
    void setUp() {
        beerMapper = new BeerMapperImpl();
        beerMapper.setBeerMapper(mapper);
        beerMapper.setBeerInventoryService(beerInventoryService);
        beerService = new RepositoryBeerService(beerMapper, repository);

        beer = buildBeer();
    }

    @Test
    void shouldGetBeerByIdWhenIdIsCorrectAndShowInventoryOnHandIsTrue() {
        when(repository.findById(any(UUID.class))).thenReturn(of(beer));
        when(beerInventoryService.getOnHandInventory(any(UUID.class))).thenReturn(QUANTITY_ON_HAND);

        BeerDto beerDtoReturned = beerService.getById(BEER_ID, true);

        assertNotNull("Null beer dto returned", beerDtoReturned);
        assertEquals(beerDtoReturned.getId(), beer.getId());
        assertEquals(beerDtoReturned.getName(), beer.getName());
        assertEquals(beerDtoReturned.getStyle(), beer.getStyle());
        assertEquals(beerDtoReturned.getPrice(), beer.getPrice());
        assertEquals(beerDtoReturned.getUpc(), beer.getUpc());
        assertEquals(beerDtoReturned.getQuantityOnHand(), QUANTITY_ON_HAND);

        verify(repository, times(1)).findById(any(UUID.class));
        verify(beerInventoryService, times(1)).getOnHandInventory(any(UUID.class));
        verify(repository, never()).findAll();
    }

    @Test
    void shouldGetBeerByIdWhenIdIsCorrectAndShowInventoryOnHandIsFalse() {
        when(repository.findById(any(UUID.class))).thenReturn(of(beer));

        BeerDto beerDtoReturned = beerService.getById(BEER_ID, false);

        assertNotNull("Null beer dto returned", beerDtoReturned);
        assertEquals(beerDtoReturned.getId(), beer.getId());
        assertEquals(beerDtoReturned.getName(), beer.getName());
        assertEquals(beerDtoReturned.getStyle(), beer.getStyle());
        assertEquals(beerDtoReturned.getPrice(), beer.getPrice());
        assertEquals(beerDtoReturned.getUpc(), beer.getUpc());
        assertNull(beerDtoReturned.getQuantityOnHand());

        verify(repository, times(1)).findById(any(UUID.class));
        verify(beerInventoryService, never()).getOnHandInventory(any(UUID.class));
        verify(repository, never()).findAll();
    }

    @Test
    void shouldGetBeers() {
        List<Beer> beers = List.of(beer);
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(beers));

        Page<BeerDto> pageReturned = beerService.find(new BeerSearchRequest(), PageRequest.of(0, 1));

        assertNotNull("Null page returned", pageReturned);
        assertEquals(pageReturned.getTotalElements(), beers.size());
        assertEquals(pageReturned.getContent().get(0).getId(), beer.getId());
        assertNull(pageReturned.getContent().get(0).getQuantityOnHand());

        verify(repository, times(1)).findAll(any(Pageable.class));
        verify(beerInventoryService, never()).getOnHandInventory(any(UUID.class));
        verify(repository, never()).findAll();
    }

    @Test
    void shouldGetBeersWhenSearchRequestHasName() {
        List<Beer> beers = List.of(beer);
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(new PageImpl<>(beers));

        Page<BeerDto> pageReturned = beerService.find(BeerSearchRequest.builder().name(beer.getName()).build(), PageRequest.of(0, 1));

        assertNotNull("Null page returned", pageReturned);
        assertEquals(pageReturned.getTotalElements(), beers.size());
        assertEquals(pageReturned.getContent().get(0).getId(), beer.getId());
        assertNull(pageReturned.getContent().get(0).getQuantityOnHand());

        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(repository, never()).findAll();
        verify(beerInventoryService, never()).getOnHandInventory(any(UUID.class));
    }

    @Test
    void shouldGetBeersWhenSearchRequestHasStyle() {
        List<Beer> beers = List.of(beer);
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(new PageImpl<>(beers));

        Page<BeerDto> pageReturned = beerService.find(BeerSearchRequest.builder().style(beer.getStyle()).build(), PageRequest.of(0, 1));

        assertNotNull("Null page returned", pageReturned);
        assertEquals(pageReturned.getTotalElements(), beers.size());
        assertEquals(pageReturned.getContent().get(0).getId(), beer.getId());
        assertNull(pageReturned.getContent().get(0).getQuantityOnHand());

        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(repository, never()).findAll();
        verify(beerInventoryService, never()).getOnHandInventory(any(UUID.class));
    }

    @Test
    void shouldGetBeersWhenSearchRequestWithShowInventoryOnHandIsTrue() {
        List<Beer> beers = List.of(beer);
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(beers));
        when(beerInventoryService.getOnHandInventory(any(UUID.class))).thenReturn(QUANTITY_ON_HAND);

        Page<BeerDto> pageReturned = beerService.find(BeerSearchRequest.builder().showInventoryOnHand(true).build(), PageRequest.of(0, 1));

        assertNotNull("Null page returned", pageReturned);
        assertEquals(pageReturned.getTotalElements(), beers.size());
        assertEquals(pageReturned.getContent().get(0).getId(), beer.getId());
        assertEquals(pageReturned.getContent().get(0).getQuantityOnHand(), QUANTITY_ON_HAND);

        verify(repository, times(1)).findAll(any(Pageable.class));
        verify(repository, never()).findAll();
        verify(beerInventoryService, times(1)).getOnHandInventory(any(UUID.class));
    }

    @Test
    void shouldThrowExceptionWhenIdIsNotCorrect() {
        when(repository.findById(any(UUID.class))).thenReturn(empty());

        assertThrows(BeerNotFoundException.class, () ->
                beerService.getById(UUID.fromString("11111111-5494-4592-8edf-7c3c629eb43e"), false));
    }

    @Test
    void shouldThrowBeerNotFoundExceptionWhenUpcIsNotCorrect() {
        when(repository.findFirstByUpc(any(String.class))).thenReturn(empty());

        assertThrows(BeerNotFoundException.class, () -> beerService.getByUpc(""));
    }

    @Test
    void shouldGetBeerByUpcWhenIdIsCorrect() {
        when(repository.findFirstByUpc(any(String.class))).thenReturn(of(beer));

        BeerDto beerDtoReturned = beerService.getByUpc(BEER_UPC);

        assertNotNull("Null beer dto returned", beerDtoReturned);
        assertEquals(beerDtoReturned.getId(), beer.getId());
        assertEquals(beerDtoReturned.getName(), beer.getName());
        assertEquals(beerDtoReturned.getStyle(), beer.getStyle());
        assertEquals(beerDtoReturned.getPrice(), beer.getPrice());
        assertEquals(beerDtoReturned.getUpc(), beer.getUpc());
        assertNull(beerDtoReturned.getQuantityOnHand());

        verify(repository, times(1)).findFirstByUpc(any(String.class));
        verify(repository, never()).findById(any(UUID.class));
        verify(repository, never()).findAll();
        verify(beerInventoryService, never()).getOnHandInventory(any(UUID.class));
    }

    private Beer buildBeer() {
        return Beer.builder()
                .id(BEER_ID)
                .name("Test service beer")
                .style(PILSNER)
                .price(new BigDecimal("3.56"))
                .upc(BEER_UPC)
                .build();
    }

}
