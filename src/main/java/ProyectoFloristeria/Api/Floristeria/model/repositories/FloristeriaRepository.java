package ProyectoFloristeria.Api.Floristeria.model.repositories;

import ProyectoFloristeria.Api.Floristeria.model.entity.FloristeriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloristeriaRepository extends JpaRepository< FloristeriaEntity, Long> {
}
