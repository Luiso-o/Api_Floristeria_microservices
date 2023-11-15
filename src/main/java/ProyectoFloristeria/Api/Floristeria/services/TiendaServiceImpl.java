package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Documents.ProductDocument;
import ProyectoFloristeria.Api.Floristeria.Documents.TicketDocument;
import ProyectoFloristeria.Api.Floristeria.Dto.ProductDto;
import ProyectoFloristeria.Api.Floristeria.Dto.TicketDto;
import ProyectoFloristeria.Api.Floristeria.Dto.StoreDto;
import ProyectoFloristeria.Api.Floristeria.Documents.StoreDocument;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.BranchCountries;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.ProductType;
import ProyectoFloristeria.Api.Floristeria.exceptions.StoreCreationException;
import ProyectoFloristeria.Api.Floristeria.exceptions.StoreNotFoundException;
import ProyectoFloristeria.Api.Floristeria.helper.DocumentToDtoConverter;
import ProyectoFloristeria.Api.Floristeria.repositories.ProductRepository;
import ProyectoFloristeria.Api.Floristeria.repositories.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TiendaServiceImpl implements TiendaService {
    private static final Logger log = LoggerFactory.getLogger(TiendaServiceImpl.class);
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private DocumentToDtoConverter converter;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Flux<StoreDto> getAllStores() {
        return storeRepository.findAll()
                .flatMap(tienda -> converter.convertStoreDocumentToDto(Mono.just(tienda)))
                .log("Lista de tiendas obtenida con éxito");
    }

    @Override
    public Mono<StoreDto> getStoreById(String id) {
        String idVerificado = converter.verificaId(id);
        return storeRepository.findById(idVerificado)
                .flatMap(store -> {
                    if (store != null) {
                        log.info("Floristería encontrada con éxito: {}", store);
                        StoreDto dto = converter.toTiendaDto(store);
                        return Mono.just(dto);
                    } else {
                        log.warn("No se encontró la floristería con el ID: {}", id);
                        return Mono.empty();
                    }
                })
                .switchIfEmpty(Mono.error(new StoreNotFoundException("No se encontró la floristería con el ID: " + id)));
    }

    @Override
    public Mono<StoreDto> createStore(String nombre, BranchCountries pais) {

        StoreDocument newStore = StoreDocument.builder()
                .fechaApertura(LocalDate.now())
                .nombre(nombre)
                .pais(pais)
                .misProductos(new ArrayList<>())
                .tickets(new ArrayList<>())
                .build();
        log.info("Tienda creada con éxito " + newStore);

        return Mono.fromCallable(() -> storeRepository.save(newStore))
                .flatMap(savedStore -> converter.convertStoreDocumentToDto(savedStore))
                .log("Tienda guardada con éxito " + newStore)
                .onErrorResume(throwable -> {
                    log.error("Error al guardar la floristería", throwable);
                    return Mono.error(new StoreCreationException("No se pudo guardar la floristería."));
                });
    }

    @Override
    public Mono<StoreDto> updateStore(String id, String nombre, BranchCountries pais) {
        String idVerificado = converter.verificaId(id);
        String nombreVerificado = nombre.isEmpty() || nombre.isBlank() ? "Mi floristería" : nombre;

        return storeRepository.findById(idVerificado)
                .flatMap(tiendaModificada -> {
                    if(tiendaModificada != null){
                        tiendaModificada.setNombre(nombreVerificado);
                        tiendaModificada.setPais(pais);
                        log.info("Tienda modificada con éxito");

                        return storeRepository.save(tiendaModificada)
                                .log("Tienda Guardada con éxito ")
                                .flatMap(savedStore ->
                                        converter.convertStoreDocumentToDto(Mono.just(savedStore)));
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

        return storeRepository.findById(idVerificado)
                .flatMap(tiendaAEliminar ->{
                    if(tiendaAEliminar != null){
                        return storeRepository.delete(tiendaAEliminar)
                                .log("Tienda eliminada con éxito")
                                .then();
                    }else{
                        log.warn("No se encontró la floristería con el ID: {}", idVerificado);
                        return Mono.empty();
                    }
                });
    }

    @Override
    public Flux<ProductDto> findAllProductsOfTheStore(String idStore) {
        String idVerificado = converter.verificaId(idStore);
        Mono<StoreDocument> tienda = storeRepository.findById(idVerificado);

        return tienda.flatMapMany(tiendaDocument -> {
            List<ProductDocument> productos = tiendaDocument.getMisProductos();
            List<Mono<ProductDto>> monoProductDtos = productos.stream()
                    .map(productoDocument -> converter.convertProductDocumentToProductDto(Mono.just(productoDocument)))
                    .collect(Collectors.toList());

            return Flux.concat(monoProductDtos);
        });
    }

    @Override
    public Flux<TicketDto> findAllTicketsOfTheStore(String idStore) {
        String idVerificado = converter.verificaId(idStore);
        Mono<StoreDocument> tienda = storeRepository.findById(idVerificado);

        return tienda.flatMapMany(store -> {
            List<TicketDocument> tickets = store.getTickets();
            List<Mono<TicketDto>> monoTickets = tickets.stream()
                   .map(ticketsDocument -> converter.convertTicketDocumentToTicketDto(Mono.just(ticketsDocument)))
                           .collect(Collectors.toList());

           return  Flux.concat(monoTickets);
        });
    }

    @Override
    public Mono<Void> deleteProductOfTheStore(String idProduct) {
        String idVerificado = converter.verificaId(idProduct);

        return productRepository.findById(idVerificado)
                .flatMap(producto -> {
                    if(producto != null){
                        StoreDocument tienda = producto.getTienda();
                        if(tienda != null){
                            tienda.getMisProductos().remove(producto);
                            return storeRepository.save(tienda)
                                    .then(Mono.empty());
                        }else{
                            return Mono.error(new StoreNotFoundException("El producto no está asociado a ninguna tienda"));
                        }
                    }else {
                        return Mono.empty();
                    }
                });
    }

    @Override
    public Mono<Map<String, Integer>> showStockOfTheStore(String idStore) {
        String idVerificado = converter.verificaId(idStore);

        return storeRepository.findById(idVerificado)
                .flatMap(store -> {
                    if(store != null){
                        List<ProductDocument> products = store.getMisProductos();
                        int arboles = 0, flores = 0, decoraciones = 0;

                        for (ProductDocument product : products){
                            if(product.getTipoProducto() == ProductType.ARBOL){
                                arboles ++;
                            } else if (product.getTipoProducto() == ProductType.FLOR) {
                                flores ++;
                            }else{
                                decoraciones ++;
                            }
                        }

                        Map<String, Integer> stockMap = new HashMap<>();
                        stockMap.put("Cantidad de Árboles ", arboles);
                        stockMap.put("Cantidad de Flores ", flores);
                        stockMap.put("Cantidad de Decoraciones ", decoraciones);

                        return Mono.just(stockMap);
                    }else{
                        log.error("No se encontró ninguna tienda con el id " + idStore);
                        return  Mono.error(new StoreNotFoundException("La tienda no se encuentra"));
                    }
                });
    }

    public Mono<Double> watchTheStorePrice(String idStore){
        String idVerificado = converter.verificaId(idStore);
        return storeRepository.findById(idVerificado)
                .flatMap(store -> {
                    if(store != null){
                        List<ProductDocument> products = store.getMisProductos();
                        double price = products.stream().mapToDouble(ProductDocument::getPrecio).sum();
                        return Mono.just(price);
                    }else{
            log.error("No se encontró ninguna tienda con el id " + idStore);
            return  Mono.error(new StoreNotFoundException("La tienda no se encuentra"));
            }
        });
    }

    public Mono<Double> seeStoreProfits(String idStore){
        String idVerificado = converter.verificaId(idStore);
        return storeRepository.findById(idVerificado)
                .flatMap(store -> {
                    if(store != null){
                        List<TicketDocument> tickets = store.getTickets();
                        double total = tickets.stream().mapToDouble(TicketDocument::getTotal).sum();
                        return Mono.just(total);
                    }else{
                        log.error("No se encontró ninguna tienda con el id " + idStore);
                        return  Mono.error(new StoreNotFoundException("La tienda no se encuentra"));
                    }
                });
    }


}
