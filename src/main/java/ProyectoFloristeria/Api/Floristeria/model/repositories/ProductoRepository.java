package ProyectoFloristeria.Api.Floristeria.model.repositories;


import ProyectoFloristeria.Api.Floristeria.model.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity,Long> {
}