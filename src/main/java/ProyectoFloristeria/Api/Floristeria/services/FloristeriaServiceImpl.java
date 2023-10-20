package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Dto.FloristeriaDto;
import ProyectoFloristeria.Api.Floristeria.excepciones.StoreNotFoundException;
import ProyectoFloristeria.Api.Floristeria.helper.Converter;
import ProyectoFloristeria.Api.Floristeria.repositories.FloristeriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.*;

@Service
public class FloristeriaServiceImpl implements FloristeriaService {
    private static final Logger log = LoggerFactory.getLogger(FloristeriaServiceImpl.class);
    @Autowired
    private FloristeriaRepository floristeriaRepository;
    @Autowired
    private Converter converter;

    @Override
    public Flux<FloristeriaDto> getAllFloristerias() {
        return null;
    }

    @Override
    public Mono<FloristeriaDto> getFloristeriaById(Long id) {
        converter.verificaId(id);
        return floristeriaRepository.findById(id)
                .flatMap(store -> {
                    if (store != null) {
                        log.info("Floristería encontrada con éxito: {}", store);
                        FloristeriaDto dto = converter.floristeriaDto(store);
                        return Mono.just(dto);
                    } else {
                        log.warn("No se encontró la floristería con el ID: {}", id);
                        return Mono.empty();
                    }
                })
                .switchIfEmpty(Mono.error(new StoreNotFoundException("No se encontró la floristería con el ID: " + id)));
    }

    @Override
    public Mono<FloristeriaDto> createFloristeria(String nombre, String pais) {

        return null;
    }

    @Override
    public Mono<FloristeriaDto> updateFloristeria(Long id, String nombre, String pais) {
        return null;
    }

    @Override
    public Mono<Void> deleteFloristeria(Long id) {
        return null;
    }
}
