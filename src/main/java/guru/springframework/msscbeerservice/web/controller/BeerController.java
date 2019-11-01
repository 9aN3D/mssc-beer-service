package guru.springframework.msscbeerservice.web.controller;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getById(@PathVariable("beerId") UUID id) {
        //TODO impl
        return new ResponseEntity<>(BeerDto.builder().build(), OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody BeerDto beer) {
        //TODO impl
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity saveNewBeer(@PathVariable("beerId") UUID id, @RequestBody BeerDto beer) {
        //TODO impl
        return new ResponseEntity<>(NO_CONTENT);
    }

}
