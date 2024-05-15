package guru.springframework.msscbeerservice.repository;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.domain.Beer_;
import guru.springframework.msscbeerservice.web.model.BeerStyle;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
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
