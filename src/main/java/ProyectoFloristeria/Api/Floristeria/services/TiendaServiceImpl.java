package ProyectoFloristeria.Api.Floristeria.services;
import ProyectoFloristeria.Api.Floristeria.Dto.TiendaDto;
import ProyectoFloristeria.Api.Floristeria.Documents.TiendaDocument;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.PaisesSucursales;
import ProyectoFloristeria.Api.Floristeria.excepciones.StoreCreationException;
import ProyectoFloristeria.Api.Floristeria.excepciones.StoreNotFoundException;
import ProyectoFloristeria.Api.Floristeria.helper.Converter;
import ProyectoFloristeria.Api.Floristeria.repositories.TiendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class TiendaServiceImpl implements TiendaService {
    private static final Logger log = LoggerFactory.getLogger(TiendaServiceImpl.class);
    @Autowired
    private TiendaRepository tiendaRepository;
    @Autowired
    private Converter converter;

    @Override
    public Flux<TiendaDto> getAllFloristerias() {
        return null;
    }

    @Override
    public Mono<TiendaDto> getFloristeriaById(String id) {
        String idVerificado = converter.verificaId(id);
        return tiendaRepository.findById(idVerificado)
                .flatMap(store -> {
                    if (store != null) {
                        log.info("Floristería encontrada con éxito: {}", store);
                        TiendaDto dto = converter.floristeriaDto(store);
                        return Mono.just(dto);
                    } else {
                        log.warn("No se encontró la floristería con el ID: {}", id);
                        return Mono.empty();
                    }
                })
                .switchIfEmpty(Mono.error(new StoreNotFoundException("No se encontró la floristería con el ID: " + id)));
    }

    @Override
    public Mono<TiendaDto> createStore(String nombre, PaisesSucursales pais) {
        String nombreVerificado = nombre.isEmpty() || nombre.isBlank() ? "Mi floristería" : nombre;

        TiendaDocument newStore = TiendaDocument.builder()
                .fechaApertura(LocalDate.now())
                .nombre(nombreVerificado)
                .pais(pais)
                .misProductos(new ArrayList<>())
                .tickets(new ArrayList<>())
                .build();

        return Mono.fromCallable(() -> tiendaRepository.save(newStore))
                .flatMap(savedStore -> converter.fromFloristeriaEntityToDto(savedStore))
                .log("Tienda guardada con éxito " + newStore)
                .onErrorResume(throwable -> {
                    log.error("Error al crear una floristería", throwable);
                    return Mono.error(new StoreCreationException("No se pudo crear la floristería."));
                });
    }

    @Override
    public Mono<TiendaDto> updateFloristeria(String id, String nombre, String pais) {
        return null;
    }

    @Override
    public Mono<Void> deleteFloristeria(String id) {
        return null;
    }
}
