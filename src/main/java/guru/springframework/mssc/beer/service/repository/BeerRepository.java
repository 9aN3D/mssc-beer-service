package guru.springframework.mssc.beer.service.repository;

import guru.springframework.mssc.beer.service.domain.Beer;
import guru.springframework.mssc.beer.service.domain.Beer_;
import guru.cfg.brewery.model.BeerStyle;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BeerRepository extends JpaRepository<Beer, UUID>, JpaSpecificationExecutor<Beer> {

    Optional<Beer> findFirstByUpc(String upc);

    interface Specs {

        static Specification<Beer> byName(String name) {
            return (root, query, builder) -> builder.equal(root.get(Beer_.name), name);
        }

        static Specification<Beer> byStyle(BeerStyle style) {
            return (root, query, builder) -> builder.equal(root.get(Beer_.style), style);
        }

        static Specification<Beer> byUpc(String upc) {
            return (root, query, builder) -> builder.equal(root.get(Beer_.upc), upc);
        }

    }

}
