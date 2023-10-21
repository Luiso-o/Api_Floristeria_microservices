package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Dto.TiendaDto;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.PaisesSucursales;
import reactor.core.publisher.*;

public interface TiendaService {
    Flux<TiendaDto> getAllFloristerias();
    Mono<TiendaDto> getFloristeriaById(String id);
    Mono<TiendaDto> createStore(String nombre, PaisesSucursales pais);
    Mono<TiendaDto> updateFloristeria(String id, String nombre, String pais);
    Mono<Void> deleteFloristeria(String id);
}
