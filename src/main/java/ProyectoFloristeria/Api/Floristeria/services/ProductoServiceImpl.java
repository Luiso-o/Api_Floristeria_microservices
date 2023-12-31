package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Documents.ProductDocument;
import ProyectoFloristeria.Api.Floristeria.Documents.StoreDocument;
import ProyectoFloristeria.Api.Floristeria.Dto.ProductDto;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.DecorationMaterials;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.ProductType;
import ProyectoFloristeria.Api.Floristeria.exceptions.ProductCreationException;
import ProyectoFloristeria.Api.Floristeria.exceptions.ProductNotFountException;
import ProyectoFloristeria.Api.Floristeria.exceptions.StoreNotFoundException;
import ProyectoFloristeria.Api.Floristeria.helper.DocumentToDtoConverter;
import ProyectoFloristeria.Api.Floristeria.repositories.ProductRepository;
import ProyectoFloristeria.Api.Floristeria.repositories.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.*;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class ProductoServiceImpl implements ProductoService{
    private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);
    @Autowired
    private ProductRepository productoRepository;
    @Autowired
    private StoreRepository tiendaRepository;
    @Autowired
    private DocumentToDtoConverter converter;

    @Override
    public Flux<ProductDto> getAllProducts() {
        return productoRepository.findAll()
                .flatMap(productos -> converter.convertProductDocumentToProductDto(Mono.just(productos)))
                .onErrorResume(RuntimeException.class, ex ->{
                    log.error("Error al obtener la lista de productos", ex);
                    return Flux.empty();
                });
    }

    @Override
    public Mono<ProductDto> getProductById(String id) {
        String idVerificado = converter.verificaId(id);
        return productoRepository.findById(idVerificado)
                .flatMap(product -> {
                    if (product != null) {
                        log.info("Producto encontrado con éxito: {}", product);
                        ProductDto dto = converter.toProductoDto(product);
                        return Mono.just(dto);
                    } else {
                        log.warn("No se encontró el producto con el ID: {}", id);
                        return Mono.empty();
                    }
                })
                .switchIfEmpty(Mono.error(new ProductNotFountException("No se encontró el producto con el ID: " + id)));
    }

    @Override
    public Mono<ProductDto> createTree(Double altura, Double precio, String idTienda) {
        String idVerificado = converter.verificaId(idTienda);
        Mono<StoreDocument> tienda = tiendaRepository.findById(idVerificado);

        ProductDocument newTree = ProductDocument.builder()
                .tipoProducto(ProductType.ARBOL)
                .parametroGenerico(altura)
                .precio(precio)
                .tienda(tienda.block())
                .build();

        return productoRepository.save(newTree)
                .flatMap(savedTree -> {
                    log.info("Nuevo árbol creado y guardado correctamente");

                    return tienda.flatMap(miTienda -> {
                                if (miTienda != null) {
                                    miTienda.getMisProductos().add(savedTree);

                                    return tiendaRepository.save(miTienda)
                                            .flatMap(savedStore -> {
                                                log.info("Nuevo árbol guardado en la tienda correctamente");
                                                return converter.convertProductDocumentToProductDto(Mono.just(savedTree));
                                            });
                                } else {
                                    log.error("No se encontró la tienda con id " + idVerificado + ". No se puede guardar el árbol en la tienda");
                                    return Mono.error(new StoreNotFoundException("La tienda a la que querías agregar el nuevo árbol no existe"));
                                }
                            });
                });
    }


    @Override
    public Mono<ProductDto> createFlower(String color, Double precio, String idTienda) {
        String idVerificado = converter.verificaId(idTienda);
        Mono<StoreDocument> tienda = tiendaRepository.findById(idVerificado);

        ProductDocument newFlower = ProductDocument.builder()
                .tipoProducto(ProductType.FLOR)
                .parametroGenerico(color)
                .precio(precio)
                .tienda(tienda.block())
                .build();

        return productoRepository.save(newFlower)
                .flatMap(savedFlower -> {
                    log.info("Nueva flor creada y guardada correctamente");

                    return tienda.flatMap(miTienda -> {
                                if (miTienda != null) {
                                    miTienda.getMisProductos().add(savedFlower);

                                    return tiendaRepository.save(miTienda)
                                            .flatMap(savedStore -> {
                                                log.info("Nueva flor guardada en la tienda correctamente");
                                                return converter.convertProductDocumentToProductDto(Mono.just(savedFlower));
                                            });
                                } else {
                                    log.error("No se encontró la tienda con id " + idVerificado + ". No se puede guardar la flor en la tienda");
                                    return Mono.error(new StoreNotFoundException("La tienda a la que querías agregar la flor no existe"));
                                }
                            });
                });
    }

    @Override
    public Mono<ProductDto> createDecor(DecorationMaterials material, Double precio, String idTienda) {
        String idVerificado = converter.verificaId(idTienda);
        Mono<StoreDocument> tienda = tiendaRepository.findById(idVerificado);

        ProductDocument newTree = ProductDocument.builder()
                .tipoProducto(ProductType.DECORACION)
                .parametroGenerico(material)
                .precio(precio)
                .tienda(tienda.block())
                .build();

        return productoRepository.save(newTree)
                .flatMap(savedDecor -> {
                    log.info("Nueva decoración creada y guardada correctamente");

                    return tienda.flatMap(miTienda -> {
                                if (miTienda != null) {
                                    miTienda.getMisProductos().add(savedDecor);

                                    return tiendaRepository.save(miTienda)
                                            .flatMap(savedStore -> {
                                                log.info("Nueva decoración guardada en la tienda correctamente");
                                                return converter.convertProductDocumentToProductDto(Mono.just(savedDecor));
                                            });
                                } else {
                                    log.error("No se encontró la tienda con id " + idVerificado + ". No se puede guardar la decoración en la tienda");
                                    return Mono.error(new StoreNotFoundException("La tienda a la que querías agregar la nueva decoración no existe"));
                                }
                            });
                });
    }

    @Override
    public Mono<ProductDto> updateTree(String idTree, Double altura, Double precio) {
        String idValidado = converter.verificaId(idTree);

        return productoRepository.findById(idValidado)
                .flatMap(arbolModificado -> {
                    if (arbolModificado != null) {
                        if (arbolModificado.getTipoProducto() != ProductType.ARBOL) {
                            log.error("El id introducido no pertenece a un árbol");
                            return Mono.error(new ProductCreationException("El id introducido no coincide con ningún árbol de la base de datos"));
                        } else {
                            arbolModificado.setParametroGenerico(altura);
                            arbolModificado.setPrecio(precio);
                            log.info("El árbol se ha modificado correctamente");

                            return actualizarProductoDeTienda(Mono.just(arbolModificado))
                                    .then(productoRepository.save(arbolModificado))
                                    .log("El árbol se ha guardado con sus nuevas modificaciones correctamente")
                                    .flatMap(savedTree -> converter.convertProductDocumentToProductDto(Mono.just(savedTree)));
                        }
                    } else {
                        log.warn("No se encontró el árbol con el ID: {}", idValidado);
                        return Mono.error(new ProductNotFountException("No se encontró un árbol con el ID: " + idValidado));
                    }
                })
                .switchIfEmpty(Mono.error(new ProductNotFountException("No se encontró el árbol con el ID: " + idValidado)));
    }

    @Override
    public Mono<ProductDto> updateFlower(String idFlower, String color, Double precio){
        String idValidado = converter.verificaId(idFlower);

        return productoRepository.findById(idValidado)
                .flatMap(florModificada -> {
                    if (florModificada != null) {
                        if (florModificada.getTipoProducto() != ProductType.FLOR) {
                            log.error("El id introducido no pertenece a una flor");
                            return Mono.error(new ProductCreationException("El id introducido no coincide con ninguna flor de la base de datos"));
                        } else {
                            florModificada.setParametroGenerico(color);
                            florModificada.setPrecio(precio);
                            log.info("La flor se ha modificado correctamente");

                            return actualizarProductoDeTienda(Mono.just(florModificada))
                                    .then(productoRepository.save(florModificada))
                                    .log("La flor se ha guardado con sus nuevas modificaciones correctamente")
                                    .flatMap(savedFlower -> converter.convertProductDocumentToProductDto(Mono.just(savedFlower)));
                        }
                    } else {
                        log.warn("No se encontró la flor con el ID: {}", idValidado);
                        return Mono.error(new ProductNotFountException("No se encontró una flor con el ID: " + idValidado));
                    }
                })
                .switchIfEmpty(Mono.error(new ProductNotFountException("No se encontró la flor con el ID: " + idValidado)));
    }


    @Override
    public Mono<ProductDto> updateDecor(String idDecor, DecorationMaterials material, Double precio){
        String idValidado = converter.verificaId(idDecor);

        return productoRepository.findById(idValidado)
                .flatMap(decorModificado -> {
                    if (decorModificado != null) {
                        if (decorModificado.getTipoProducto() != ProductType.DECORACION) {
                            log.error("El id introducido no pertenece a una decoración");
                            return Mono.error(new ProductCreationException("El id introducido no coincide con ninguna decoración de la base de datos"));
                        } else {
                            decorModificado.setParametroGenerico(material);
                            decorModificado.setPrecio(precio);
                            log.info("La decoración se ha modificado correctamente");

                            return actualizarProductoDeTienda(Mono.just(decorModificado))
                                    .then(productoRepository.save(decorModificado))
                                    .log("La decoración se ha guardado con sus nuevas modificaciones correctamente")
                                    .flatMap(savedDecor -> converter.convertProductDocumentToProductDto(Mono.just(savedDecor)));
                        }
                    } else {
                        log.warn("No se encontró la decoración con el ID: {}", idValidado);
                        return Mono.error(new ProductNotFountException("No se encontró una decoración con el ID: " + idValidado));
                    }
                })
                .switchIfEmpty(Mono.error(new ProductNotFountException("No se encontró la decoración con el ID: " + idValidado)));
    }

    @Override
    public Mono<Void> deleteProduct(String idProduct){
        String idValidado = converter.verificaId(idProduct);
        return productoRepository.deleteById(idValidado);
    }

    private Mono<Void> actualizarProductoDeTienda(Mono<ProductDocument> productoDocumentMono) {
        return productoDocumentMono.flatMap(productoAActualizar -> {
            Mono<StoreDocument> tiendaMono = Mono.just(productoAActualizar.getTienda());

            return tiendaMono.flatMap(tienda -> {
                List<ProductDocument> productosDeTienda = tienda.getMisProductos();

                int indice = IntStream.range(0, productosDeTienda.size())
                        .filter(i -> productosDeTienda.get(i).getId()
                                .equals(productoAActualizar.getId()))
                        .findFirst()
                        .orElse(-1);

                if (indice >= 0) {
                    productosDeTienda.set(indice, productoAActualizar);
                } else {
                    productosDeTienda.add(productoAActualizar);
                }

                tienda.setMisProductos(productosDeTienda);
                return tiendaRepository.save(tienda);
            });
        }).then();
    }

}

