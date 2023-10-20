package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Dto.FloristeriaDto;
import ProyectoFloristeria.Api.Floristeria.entity.FloristeriaEntity;
import reactor.core.publisher.*;

public interface FloristeriaService {
    Flux<FloristeriaDto> getAllFloristerias();
    Mono<FloristeriaDto> getFloristeriaById(Long id);
    Mono<FloristeriaDto> createFloristeria(String nombre, String pais);
    Mono<FloristeriaDto> updateFloristeria(Long id, String nombre, String pais);
    Mono<Void> deleteFloristeria(Long id);
}
