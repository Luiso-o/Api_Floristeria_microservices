package ProyectoFloristeria.Api.Floristeria.model.services;

import ProyectoFloristeria.Api.Floristeria.model.Dto.ProductoDto;
import ProyectoFloristeria.Api.Floristeria.model.entity.FloristeriaEntity;
import ProyectoFloristeria.Api.Floristeria.model.entity.ProductoEntity;
import ProyectoFloristeria.Api.Floristeria.model.entity.enumeraciones.MaterialesDecoracion;
import ProyectoFloristeria.Api.Floristeria.model.entity.enumeraciones.TipoProducto;
import ProyectoFloristeria.Api.Floristeria.model.repositories.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Luis
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private FloristeriaService floristeriaService;

    public ProductoDto crearArbol(long idTienda, double altura, double precio) {
        FloristeriaEntity tienda = floristeriaService.encuentraFloristeria(idTienda);
        return castearADto(productoRepository.save(ProductoEntity.builder()
                .tipo(TipoProducto.ARBOL)
                .caracteristica(altura)
                .precio(precio)
                .floristeria(tienda)
                .build()));

    }

    public ProductoDto crearFlor(long idTienda, String color, double precio) {
        FloristeriaEntity tienda = floristeriaService.encuentraFloristeria(idTienda);
        return castearADto(productoRepository.save(ProductoEntity.builder()
                .tipo(TipoProducto.FLOR)
                .caracteristica(color)
                .precio(precio)
                .floristeria(tienda)
                .build()));
    }

    public ProductoDto crearDecoracion(long idTienda, MaterialesDecoracion material, double precio) {
        FloristeriaEntity tienda = floristeriaService.encuentraFloristeria(idTienda);
        return castearADto(productoRepository.save(ProductoEntity.builder()
                .tipo(TipoProducto.DECORACION)
                .caracteristica(material.name())
                .precio(precio)
                .floristeria(tienda)
                .build()));
    }

    public List<ProductoDto> imprimeStockDeTienda(long idTienda) {
        List<ProductoEntity>miStock = productoRepository.findAllByFloristeria_IdFloristeria(idTienda);
        return miStock.stream().map(this::castearADto).collect(Collectors.toList());
    }

    public void eliminarProductoDelStock(ProductoEntity producto) {
        productoRepository.delete(encuentraProducto(producto.getIdProducto()));
    }

    public ProductoEntity encuentraProducto(long id){
        return productoRepository.findById(id).
                orElseThrow(()-> new EntityNotFoundException("No se encontró el producto con el id " + id));
    }


    /**
     * Castea un objeto ProductoEntidad a Dto.
     * @return ProductoDto
     */
    private ProductoDto castearADto(ProductoEntity nuevo) {
        return ProductoDto.builder()
                .tipo(nuevo.getTipo())
                .caracteristica(nuevo.getCaracteristica())
                .precio(nuevo.getPrecio())
                .nombreTienda(nuevo.getFloristeria().getNombre())
                .build();
    }



}
