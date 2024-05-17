package guru.springframework.mssc.beer.service.service;

import guru.cfg.brewery.model.BeerDto;
import guru.cfg.brewery.model.BeerSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BeerService {

    void save(BeerDto dto);

    void update(UUID id, BeerDto dto);

    BeerDto getById(UUID id, boolean showInventoryOnHand);

    BeerDto getByUpc(String upc);

    Page<BeerDto> find(BeerSearchRequest searchRequest, Pageable pageable);

}
