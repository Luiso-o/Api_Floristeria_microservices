package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Dto.ProductDto;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.DecorationMaterials;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {
    Flux<ProductDto> getAllProducts();
    Mono<ProductDto> getProductById(String id);
    Mono<ProductDto> createTree(Double altura, Double precio, String idTienda);
    Mono<ProductDto> createFlower(String color, Double precio, String idTienda);
    Mono<ProductDto> createDecor(DecorationMaterials material, Double precio, String idTienda);
    Mono<ProductDto> updateTree(String idTree, Double altura, Double precio);
    Mono<ProductDto> updateFlower(String idFlower, String color, Double precio);
    Mono<ProductDto> updateDecor(String idDecor, DecorationMaterials material, Double precio);
    Mono<Void> deleteProduct(String idProduct);
}
