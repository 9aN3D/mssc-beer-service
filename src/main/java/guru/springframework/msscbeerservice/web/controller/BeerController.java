package guru.springframework.msscbeerservice.web.controller;

import guru.springframework.msscbeerservice.service.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerService beerService;

    @PostMapping
    public ResponseEntity save(@Valid @NotNull @RequestBody BeerDto dto) {
        beerService.save(dto);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity update(@NotNull @PathVariable("beerId") UUID id, @Valid @NotNull @RequestBody BeerDto dto) {
        beerService.update(id, dto);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getById(@NotNull @PathVariable("beerId") UUID id) {
        return new ResponseEntity<>(beerService.getById(id), OK);
    }

    @GetMapping
    public ResponseEntity<BeerPagedList> find(Pageable pageable) {
        Page<BeerDto> page = beerService.find(pageable);
        return new ResponseEntity<>(new BeerPagedList(page.getContent(), pageable, page.getTotalElements()), OK);
    }

}
