package ProyectoFloristeria.Api.Floristeria.repositories;

import ProyectoFloristeria.Api.Floristeria.entity.FloristeriaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloristeriaRepository extends ReactiveCrudRepository< FloristeriaEntity, Long> {
}
