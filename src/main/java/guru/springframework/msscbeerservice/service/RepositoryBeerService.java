package guru.springframework.msscbeerservice.service;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.exception.BeerNotFoundException;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.web.mapper.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.lang.String.format;

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

    @Override
    public BeerDto getById(UUID id) {
        log.trace("Getting beer {id: {}}", id);
        BeerDto beerDto = mapper.beerToBeerDto(getByIdOrThrow(id));
        log.info("Got beer {dto: {}}", beerDto);
        return beerDto;
    }

    @Override
    public Page<BeerDto> find(Pageable pageable) {
        log.trace("Searching beers {pageable: {}}", pageable);

        Page<BeerDto> result = repository.findAll(pageable)
                .map(mapper::beerToBeerDto);

        log.info("Searched beers {pageable: {}, pageTotalElements: {}, pageNumberOfElements: {}}", pageable, result.getTotalElements(), result.getNumberOfElements());
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
                .orElseThrow(() -> new BeerNotFoundException(format("Beer not found id %s", id.toString())));
    }

}
