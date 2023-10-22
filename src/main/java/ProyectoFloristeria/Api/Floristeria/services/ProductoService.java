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
    Mono<ProductoDto> updateProduct(String idProducto, TipoProducto tipo, Object parametro, Double precio, String idTienda);
    Mono<Void> deleteTree(String idArbol);
    Mono<Void> deleteFlower(String idFlor);
    Mono<Void> deleteDecor(String idDecor);
}
