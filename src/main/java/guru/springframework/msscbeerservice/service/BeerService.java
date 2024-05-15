package guru.springframework.msscbeerservice.service;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerSearchRequest;
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
