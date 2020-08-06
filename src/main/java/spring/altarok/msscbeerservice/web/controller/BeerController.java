package spring.altarok.msscbeerservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.altarok.msscbeerservice.services.BeerService;
import spring.altarok.msscbeerservice.web.model.BeerDto;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId) {
        BeerDto returnedBeer = beerService.getBeerById(beerId);
        return new ResponseEntity<>(returnedBeer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDto> saveNewBeer(@RequestBody @Validated BeerDto beerDto) {
        return new ResponseEntity<>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBeerById(@PathVariable UUID beerId, @RequestBody @Validated BeerDto beerDto) {
        beerService.updateBeer(beerId, beerDto);
    }

}
