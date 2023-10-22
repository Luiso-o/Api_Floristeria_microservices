package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Documents.ProductoDocument;
import ProyectoFloristeria.Api.Floristeria.Documents.TiendaDocument;
import ProyectoFloristeria.Api.Floristeria.Dto.ProductoDto;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.MaterialesDecoracion;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.TipoProducto;
import ProyectoFloristeria.Api.Floristeria.excepciones.ProductCreationException;
import ProyectoFloristeria.Api.Floristeria.excepciones.StoreNotFoundException;
import ProyectoFloristeria.Api.Floristeria.helper.Converter;
import ProyectoFloristeria.Api.Floristeria.repositories.ProductoRepository;
import ProyectoFloristeria.Api.Floristeria.repositories.TiendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService{
    private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private TiendaRepository tiendaRepository;
    @Autowired
    private Converter converter;

    @Override
    public Flux<ProductoDto> getAllProducts() {
        return null;
    }

    @Override
    public Mono<ProductoDto> getProductById(String id) {
        return null;
    }

    @Override
    public Mono<ProductoDto> createTree(Double altura, Double precio, String idTienda) {
        String idVerificado = converter.verificaId(idTienda);
        TiendaDocument miTienda = tiendaRepository.findById(idVerificado).block();

        ProductoDocument newTree = ProductoDocument.builder()
                .tipoProducto(TipoProducto.ARBOL)
                .parametroGenerico(altura)
                .precio(precio)
                .build();
        log.info("Arbol creado con éxito " + newTree);

        if(miTienda != null){
            miTienda.getMisProductos().add(newTree);
            log.info("El nuevo arbol se a guardado correctamente en la tienda " + miTienda.getNombre());
        }else{
            log.error("No se encontró la tienda con id " + idVerificado + " no se puede guardar el arbol");
            return Mono.error(new StoreNotFoundException("La tienda a la que querías agregar el nueva arbol no existe"));
        }
        return Mono.fromCallable(() -> productoRepository.save(newTree))
                .flatMap(savedTree -> converter.formProductDocumentToProductDto(savedTree))
                .log("Arbol guardado con éxito en la base de datos " + newTree)
                .onErrorResume(throwable -> {
                    log.error("Error al guardar el árbol", throwable);
                    return Mono.error(new ProductCreationException("No se pudo guardar el árbol"));
                });
    }

    @Override
    public Mono<ProductoDto> createFlower(String color, Double precio, String idTienda) {
        return null;
    }

    @Override
    public Mono<ProductoDto> createDecor(MaterialesDecoracion material, Double precio, String idTienda) {
        return null;
    }

    @Override
    public Mono<ProductoDto> updateProduct(String idProducto, TipoProducto tipo, Object parametro, Double precio, String idTienda) {
        return null;
    }

    @Override
    public Mono<Void> deleteTree(String idArbol) {
        return null;
    }

    @Override
    public Mono<Void> deleteFlower(String idFlor) {
        return null;
    }

    @Override
    public Mono<Void> deleteDecor(String idDecor) {
        return null;
    }
}
