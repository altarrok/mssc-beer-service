package spring.altarok.msscbeerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.altarok.msscbeerservice.domain.Beer;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
