package ProyectoFloristeria.Api.Floristeria.repositories;

import ProyectoFloristeria.Api.Floristeria.Documents.StoreDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends ReactiveMongoRepository<StoreDocument, String> {
}
