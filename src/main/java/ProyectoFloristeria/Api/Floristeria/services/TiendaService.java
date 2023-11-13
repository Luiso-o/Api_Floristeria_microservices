package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Dto.ProductoDto;
import ProyectoFloristeria.Api.Floristeria.Dto.TicketDto;
import ProyectoFloristeria.Api.Floristeria.Dto.TiendaDto;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.PaisesSucursales;
import reactor.core.publisher.*;

import java.util.Map;

public interface TiendaService {
    Flux<TiendaDto> getAllStores();
    Mono<TiendaDto> getStoreById(String id);
    Mono<TiendaDto> createStore(String nombre, PaisesSucursales pais);
    Mono<TiendaDto> updateStore(String id, String nombre,PaisesSucursales pais);
    Mono<Void> deleteStore(String id);
    Flux<ProductoDto>findAllProductsOfTheStore(String idStore);
    Mono<Void> deleteProductOfTheStore(String idProduct);
    Mono<Map<String,Integer>>showStockOfTheStore(String idStore);
    Flux<TicketDto>findAllTicketsOfTheStore(String idStore);
    Mono<Double> watchTheStorePrice(String idStore);
    Mono<Double> seeStoreProfits(String idStore);
}
