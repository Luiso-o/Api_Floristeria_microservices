package ProyectoFloristeria.Api.Floristeria.model.repositories;


import ProyectoFloristeria.Api.Floristeria.model.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity,Long> {
    List<ProductoEntity>findAllByFloristeria_IdFloristeria(Long idTienda);
}
