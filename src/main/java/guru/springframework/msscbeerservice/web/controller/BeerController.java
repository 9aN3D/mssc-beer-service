package guru.springframework.msscbeerservice.web.controller;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.exception.BeerNotFoundException;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.web.mapper.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerMapper mapper;
    private final BeerRepository repository;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getById(@NotNull @PathVariable("beerId") UUID id) {
        return new ResponseEntity<>(mapper.BeerToBeerDto(getByIdOrThrow(id)), OK);
    }

    @PostMapping
    public ResponseEntity save(@Valid @NotNull @RequestBody BeerDto dto) {
        repository.save(mapper.BeerDtoToBeer(dto));
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity update(@NotNull @PathVariable("beerId") UUID id, @Valid @NotNull @RequestBody BeerDto dto) {
        return updateOrThrow(id, dto);
    }

    private Beer getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BeerNotFoundException(format("Beer not found id %s", id.toString())));
    }

    private ResponseEntity updateOrThrow(UUID id, BeerDto dto) {
        Beer beer = getByIdOrThrow(id);
        beer.setName(dto.getName());
        beer.setStyle(dto.getStyle());
        beer.setPrice(dto.getPrice());
        beer.setUpc(dto.getUpc());

        repository.save(beer);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
