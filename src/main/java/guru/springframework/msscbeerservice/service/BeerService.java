package guru.springframework.msscbeerservice.service;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BeerService {

    void save(BeerDto dto);

    void update(UUID id, BeerDto dto);

    BeerDto getById(UUID id);

    Page<BeerDto> find(Pageable pageable);

}
