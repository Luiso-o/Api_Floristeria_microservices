package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Dto.ProductDto;
import ProyectoFloristeria.Api.Floristeria.Dto.TicketDto;
import ProyectoFloristeria.Api.Floristeria.Dto.StoreDto;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.BranchCountries;
import reactor.core.publisher.*;

import java.util.Map;

public interface TiendaService {
    Flux<StoreDto> getAllStores();
    Mono<StoreDto> getStoreById(String id);
    Mono<StoreDto> createStore(String nombre, BranchCountries pais);
    Mono<StoreDto> updateStore(String id, String nombre, BranchCountries pais);
    Mono<Void> deleteStore(String id);
    Flux<ProductDto>findAllProductsOfTheStore(String idStore);
    Mono<Void> deleteProductOfTheStore(String idProduct);
    Mono<Map<String,Integer>>showStockOfTheStore(String idStore);
    Flux<TicketDto>findAllTicketsOfTheStore(String idStore);
    Mono<Double> watchTheStorePrice(String idStore);
    Mono<Double> seeStoreProfits(String idStore);
}
