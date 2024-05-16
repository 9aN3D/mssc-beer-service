package guru.springframework.mssc.beer.service.web.mapper;

import guru.springframework.mssc.beer.service.domain.Beer;
import guru.springframework.mssc.beer.service.service.inventory.BeerInventoryService;
import guru.springframework.mssc.beer.service.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BeerMapperDecorator implements BeerMapper {

    private BeerInventoryService beerInventoryService;
    private BeerMapper beerMapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setBeerMapper(@Qualifier("delegate") BeerMapper beerMapper) {
        this.beerMapper = beerMapper;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        BeerDto result = beerMapper.beerToBeerDto(beer);
        result.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
        return result;
    }

    @Override
    public BeerDto beerToBeerDtoWithoutQuantityOnHand(Beer beer) {
        return beerMapper.beerToBeerDto(beer);
    }

    @Override
    public Beer beerDtoToBeer(BeerDto dto) {
        return beerMapper.beerDtoToBeer(dto);
    }

}
