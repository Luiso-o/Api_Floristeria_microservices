package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Documents.ProductoDocument;
import ProyectoFloristeria.Api.Floristeria.Dto.TiendaDto;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.PaisesSucursales;
import reactor.core.publisher.*;

public interface TiendaService {
    Flux<TiendaDto> getAllStores();
    Mono<TiendaDto> getStoreById(String id);
    Mono<TiendaDto> createStore(String nombre, PaisesSucursales pais);
    Mono<TiendaDto> updateStore(String id, String nombre,PaisesSucursales pais);
    Mono<Void> deleteStore(String id);
    Flux<ProductoDocument>findAllProductsOfTheStore(String idStore);
}
