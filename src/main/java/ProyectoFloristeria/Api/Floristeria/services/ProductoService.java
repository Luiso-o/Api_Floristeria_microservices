package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Dto.ProductoDto;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.MaterialesDecoracion;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.TipoProducto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {
    Flux<ProductoDto> getAllProducts();
    Mono<ProductoDto> getProductById(String id);
    Mono<ProductoDto> createTree(Double altura,Double precio, String idTienda);
    Mono<ProductoDto> createFlower(String color,Double precio, String idTienda);
    Mono<ProductoDto> createDecor(MaterialesDecoracion material, Double precio, String idTienda);
    Mono<ProductoDto> updateTree(String idTree,Double altura, Double precio);
    Mono<ProductoDto> updateFlower(String idFlower,String color, Double precio);
    Mono<ProductoDto> updateDecor(String idDecor,MaterialesDecoracion material, Double precio);
    Mono<Void> deleteProduct(String idProduct);
}
