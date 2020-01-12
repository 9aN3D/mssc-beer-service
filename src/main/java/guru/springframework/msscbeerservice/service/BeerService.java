package guru.springframework.msscbeerservice.service;

import guru.springframework.msscbeerservice.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {

    void save(BeerDto dto);

    void update(UUID id, BeerDto dto);

    BeerDto getById(UUID id);

}
