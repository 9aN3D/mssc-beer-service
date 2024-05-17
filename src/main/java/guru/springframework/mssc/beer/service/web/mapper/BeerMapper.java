package guru.springframework.mssc.beer.service.web.mapper;

import guru.springframework.mssc.beer.service.domain.Beer;
import guru.cfg.brewery.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DataMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    BeerDto beerToBeerDtoWithoutQuantityOnHand(Beer beer);

    Beer beerDtoToBeer(BeerDto dto);

}
