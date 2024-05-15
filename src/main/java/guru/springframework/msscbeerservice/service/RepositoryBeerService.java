package guru.springframework.msscbeerservice.service;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.exception.BeerNotFoundException;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.web.mapper.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RepositoryBeerService implements BeerService {

    private final BeerMapper mapper;
    private final BeerRepository repository;

    @Transactional
    @Override
    public void save(BeerDto dto) {
        log.trace("Saving beer: {}", dto);
        Beer savedBeer = repository.save(mapper.beerDtoToBeer(dto));
        log.info("Saved beer: {}", savedBeer);
    }

    @Transactional
    @Override
    public void update(UUID id, BeerDto dto) {
        log.trace("Updating beer {id: {}, dto: {}}", id, dto);
        Beer beer = updateOrThrow(id, dto);
        log.info("Updated beer: {}", beer);
    }

    @Cacheable(cacheNames = "beerCache", key = "#id", condition = "#showInventoryOnHand == false")
    @Override
    public BeerDto getById(UUID id, boolean showInventoryOnHand) {
        log.trace("Getting beer {id: {}, showInventoryOnHand: {}}", id, showInventoryOnHand);
        BeerDto beerDto = getBeerToBeerDtoFunction(showInventoryOnHand).apply(getByIdOrThrow(id));
        log.info("Got beer {dto: {}}", beerDto);
        return beerDto;
    }

    @Cacheable(cacheNames = "beerListCache", condition = "#searchRequest.showInventoryOnHand == false")
    @Override
    public Page<BeerDto> find(BeerSearchRequest searchRequest, Pageable pageable) {
        log.trace("Searching beers {searchRequest: {}, pageable: {}}", searchRequest, pageable);

        List<Specification<Beer>> specs = new LinkedList<>();
        ofNullable(searchRequest.getName()).map(BeerRepository.Specs::byName).ifPresent(specs::add);
        ofNullable(searchRequest.getStyle()).map(BeerRepository.Specs::byStyle).ifPresent(specs::add);

        Optional<Specification<Beer>> specification = specs.stream()
                .reduce(Specification::and);

        Page<BeerDto> result = specification.map(spec -> repository.findAll(spec, pageable))
                .orElseGet(() -> repository.findAll(pageable))
                .map(getBeerToBeerDtoFunction(searchRequest.isShowInventoryOnHand()));

        log.info("Searched beers {searchRequest: {}, pageable: {}, pageTotalElements: {}, pageNumberOfElements: {}}", searchRequest, pageable, result.getTotalElements(), result.getNumberOfElements());
        return result;
    }

    private Beer updateOrThrow(UUID id, BeerDto dto) {
        Beer beer = getByIdOrThrow(id);

        beer.setName(dto.getName());
        beer.setStyle(dto.getStyle());
        beer.setPrice(dto.getPrice());
        beer.setUpc(dto.getUpc());

        return beer;
    }

    private Beer getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BeerNotFoundException(format("Beer not found id %s", id)));
    }

    private Function<Beer, BeerDto> getBeerToBeerDtoFunction(boolean showInventoryOnHand) {
        return showInventoryOnHand
                ? mapper::beerToBeerDto
                : mapper::beerToBeerDtoWithoutQuantityOnHand;
    }

}
