package ProyectoFloristeria.Api.Floristeria.repositories;

import ProyectoFloristeria.Api.Floristeria.Documents.ProductoDocument;
import ProyectoFloristeria.Api.Floristeria.Documents.TiendaDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TiendaRepository extends ReactiveMongoRepository<TiendaDocument, String> {
}
