package ProyectoFloristeria.Api.Floristeria.repositories;

import ProyectoFloristeria.Api.Floristeria.Documents.ProductoDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductoRepository extends ReactiveMongoRepository<ProductoDocument,String> {
}
