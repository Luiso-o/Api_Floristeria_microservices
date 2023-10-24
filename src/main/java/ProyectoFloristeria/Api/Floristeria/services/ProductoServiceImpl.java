package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Documents.ProductoDocument;
import ProyectoFloristeria.Api.Floristeria.Dto.ProductoDto;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.MaterialesDecoracion;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.TipoProducto;
import ProyectoFloristeria.Api.Floristeria.excepciones.ProductCreationException;
import ProyectoFloristeria.Api.Floristeria.excepciones.ProductNotFountException;
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
        return productoRepository.findAll()
                .flatMap(productos -> converter.formProductDocumentToProductDto(Mono.just(productos)))
                .log("Lista de productos obtenidas con éxito");
    }

    @Override
    public Mono<ProductoDto> getProductById(String id) {
        String idVerificado = converter.verificaId(id);
        return productoRepository.findById(idVerificado)
                .flatMap(product -> {
                    if (product != null) {
                        log.info("Producto encontrado con éxito: {}", product);
                        ProductoDto dto = converter.toProductoDto(product);
                        return Mono.just(dto);
                    } else {
                        log.warn("No se encontró el producto con el ID: {}", id);
                        return Mono.empty();
                    }
                })
                .switchIfEmpty(Mono.error(new ProductNotFountException("No se encontró el producto con el ID: " + id)));
    }

    @Override
    public Mono<ProductoDto> createTree(Double altura, Double precio, String idTienda) {
        String idVerificado = converter.verificaId(idTienda);

        ProductoDocument newTree = ProductoDocument.builder()
                .tipoProducto(TipoProducto.ARBOL)
                .parametroGenerico(altura)
                .precio(precio)
                .build();

        return productoRepository.save(newTree)
                .flatMap(savedTree -> {
                    log.info("Nuevo árbol creado y guardado correctamente");

                    return tiendaRepository.findById(idVerificado)
                            .flatMap(miTienda -> {
                                if (miTienda != null) {
                                    miTienda.getMisProductos().add(savedTree);

                                    return tiendaRepository.save(miTienda)
                                            .flatMap(savedStore -> {
                                                log.info("Nuevo árbol guardado en la tienda correctamente");
                                                return converter.formProductDocumentToProductDto(Mono.just(savedTree));
                                            });
                                } else {
                                    log.error("No se encontró la tienda con id " + idVerificado + ". No se puede guardar el árbol en la tienda");
                                    return Mono.error(new StoreNotFoundException("La tienda a la que querías agregar el nuevo árbol no existe"));
                                }
                            });
                });
    }

    @Override
    public Mono<ProductoDto> createFlower(String color, Double precio, String idTienda) {
        String idVerificado = converter.verificaId(idTienda);

        ProductoDocument newFlower = ProductoDocument.builder()
                .tipoProducto(TipoProducto.FLOR)
                .parametroGenerico(color)
                .precio(precio)
                .build();

        return productoRepository.save(newFlower)
                .flatMap(savedFlower -> {
                    log.info("Nueva flor creada y guardada correctamente");

                    return tiendaRepository.findById(idVerificado)
                            .flatMap(miTienda -> {
                                if (miTienda != null) {
                                    miTienda.getMisProductos().add(savedFlower);

                                    return tiendaRepository.save(miTienda)
                                            .flatMap(savedStore -> {
                                                log.info("Nueva flor guardada en la tienda correctamente");
                                                return converter.formProductDocumentToProductDto(Mono.just(savedFlower));
                                            });
                                } else {
                                    log.error("No se encontró la tienda con id " + idVerificado + ". No se puede guardar la flor en la tienda");
                                    return Mono.error(new StoreNotFoundException("La tienda a la que querías agregar la flor no existe"));
                                }
                            });
                });
    }

    @Override
    public Mono<ProductoDto> createDecor(MaterialesDecoracion material, Double precio, String idTienda) {
        String idVerificado = converter.verificaId(idTienda);

        ProductoDocument newTree = ProductoDocument.builder()
                .tipoProducto(TipoProducto.DECORACION)
                .parametroGenerico(material)
                .precio(precio)
                .build();

        return productoRepository.save(newTree)
                .flatMap(savedDecor -> {
                    log.info("Nueva decoración creada y guardada correctamente");

                    return tiendaRepository.findById(idVerificado)
                            .flatMap(miTienda -> {
                                if (miTienda != null) {
                                    miTienda.getMisProductos().add(savedDecor);

                                    return tiendaRepository.save(miTienda)
                                            .flatMap(savedStore -> {
                                                log.info("Nueva decoración guardada en la tienda correctamente");
                                                return converter.formProductDocumentToProductDto(Mono.just(savedDecor));
                                            });
                                } else {
                                    log.error("No se encontró la tienda con id " + idVerificado + ". No se puede guardar la decoración en la tienda");
                                    return Mono.error(new StoreNotFoundException("La tienda a la que querías agregar la nueva decoración no existe"));
                                }
                            });
                });
    }

    @Override
    public Mono<ProductoDto> updateTree(String idTree, Double altura, Double precio) {
        String idValidado = converter.verificaId(idTree);

        return productoRepository.findById(idValidado)
                .flatMap(arbolModificado -> {
                    if (arbolModificado != null) {
                        if (arbolModificado.getTipoProducto() != TipoProducto.ARBOL) {
                            log.error("El id introducido no pertenece a un árbol");
                            return Mono.error(new ProductCreationException("El id introducido no coincide con ningún árbol de la base de datos"));
                        } else {
                            arbolModificado.setParametroGenerico(altura);
                            arbolModificado.setPrecio(precio);
                            log.info("El árbol se ha modificado correctamente");

                            return productoRepository.save(arbolModificado)
                                    .log("El árbol se ha guardado con sus nuevas modificaciones correctamente")
                                    .flatMap(savedTree -> converter.formProductDocumentToProductDto(Mono.just(savedTree)));
                        }
                    } else {
                        log.warn("No se encontró el árbol con el ID: {}", idValidado);
                        return Mono.error(new ProductNotFountException("No se encontró un árbol con el ID: " + idValidado));
                    }
                })
                .switchIfEmpty(Mono.error(new ProductNotFountException("No se encontró el árbol con el ID: " + idValidado)));
    }


    @Override
    public Mono<ProductoDto> updateFlower(String idFlower,String color, Double precio){
        return null;
    }
    @Override
    public Mono<ProductoDto> updateDecor(String idDecor,MaterialesDecoracion material, Double precio){
        return null;
    }

    @Override
    public Mono<Void> deleteProduct(String idProduct){
        return null;
    }
}
