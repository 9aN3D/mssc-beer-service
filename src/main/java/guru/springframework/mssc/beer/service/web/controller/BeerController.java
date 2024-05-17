package guru.springframework.mssc.beer.service.web.controller;

import guru.springframework.mssc.beer.service.service.BeerService;
import guru.cfg.brewery.model.BeerSearchRequest;
import guru.cfg.brewery.model.BeerStyle;
import guru.cfg.brewery.model.BeerDto;
import guru.cfg.brewery.model.BeerPagedList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(CREATED)
    public void save(@Valid @NotNull @RequestBody BeerDto dto) {
        beerService.save(dto);
    }

    @PutMapping("/{beerId}")
    @ResponseStatus(NO_CONTENT)
    public void update(@NotNull @PathVariable("beerId") UUID id, @Valid @NotNull @RequestBody BeerDto dto) {
        beerService.update(id, dto);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getById(@NotNull @PathVariable("beerId") UUID id,
                                           @RequestParam(name = "showInventoryOnHand", required = false, defaultValue = "false") Boolean showInventoryOnHand) {
        return new ResponseEntity<>(beerService.getById(id, showInventoryOnHand), OK);
    }

    @GetMapping("/upc/{upc}")
    public ResponseEntity<BeerDto> getById(@NotNull @PathVariable("upc") String upc) {
        return new ResponseEntity<>(beerService.getByUpc(upc), OK);
    }

    @GetMapping
    public ResponseEntity<BeerPagedList> find(@RequestParam(name = "name", required = false) String name,
                                              @RequestParam(name = "style", required = false) BeerStyle style,
                                              @RequestParam(name = "upc", required = false) String upc,
                                              @RequestParam(name = "showInventoryOnHand", required = false, defaultValue = "false") Boolean showInventoryOnHand,
                                              Pageable pageable) {
        BeerSearchRequest searchRequest = BeerSearchRequest.builder()
                .name(name)
                .style(style)
                .upc(upc)
                .showInventoryOnHand(showInventoryOnHand)
                .build();
        Page<BeerDto> page = beerService.find(searchRequest, pageable);
        return new ResponseEntity<>(new BeerPagedList(page.getContent(), pageable, page.getTotalElements()), OK);
    }

}
