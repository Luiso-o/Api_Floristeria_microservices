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
    public Flux<TiendaDto> getAllStores() {
        return tiendaRepository.findAll()
                .flatMap(tienda -> converter.fromFloristeriaEntityToDto(Mono.just(tienda)))
                .log("Obtenidas todas las tiendas con éxito");
    }

    @Override
    public Mono<TiendaDto> getStoreById(String id) {
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
        log.info("Tienda creada con éxito");

        return Mono.fromCallable(() -> tiendaRepository.save(newStore))
                .flatMap(savedStore -> converter.fromFloristeriaEntityToDto(savedStore))
                .log("Tienda guardada con éxito " + newStore)
                .onErrorResume(throwable -> {
                    log.error("Error al crear una floristería", throwable);
                    return Mono.error(new StoreCreationException("No se pudo crear la floristería."));
                });
    }

    @Override
    public Mono<TiendaDto> updateStore(String id, String nombre, PaisesSucursales pais) {
        String idVerificado = converter.verificaId(id);
        String nombreVerificado = nombre.isEmpty() || nombre.isBlank() ? "Mi floristería" : nombre;

        return tiendaRepository.findById(idVerificado)
                .flatMap(tiendaModificada -> {
                    if(tiendaModificada != null){
                        tiendaModificada.setNombre(nombreVerificado);
                        tiendaModificada.setPais(pais);
                        log.info("Tienda modificada con éxito");

                        return tiendaRepository.save(tiendaModificada)
                                .log("Tienda Guardada con éxito ")
                                .flatMap(savedStore ->
                                        converter.fromFloristeriaEntityToDto(Mono.just(savedStore)));
                    }else{
                        log.warn("No se encontró la floristería con el ID: {}", idVerificado);
                        return Mono.empty();
                    }
                })
                .switchIfEmpty(Mono.error(new StoreNotFoundException("No se encontró la floristería con el ID: " + idVerificado)));
    }

    @Override
    public Mono<Void> deleteStore(String id) {
        String idVerificado = converter.verificaId(id);

        return tiendaRepository.findById(idVerificado)
                .flatMap(tiendaAEliminar ->{
                    if(tiendaAEliminar != null){
                        return tiendaRepository.delete(tiendaAEliminar)
                                .log("Tienda eliminada con éxito")
                                .then();
                    }else{
                        log.warn("No se encontró la floristería con el ID: {}", idVerificado);
                        return Mono.empty();
                    }
                });
    }
}
