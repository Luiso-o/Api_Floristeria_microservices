package ProyectoFloristeria.Api.Floristeria.model.services;

import ProyectoFloristeria.Api.Floristeria.model.Dto.ProductoDto;
import ProyectoFloristeria.Api.Floristeria.model.entity.FloristeriaEntity;
import ProyectoFloristeria.Api.Floristeria.model.entity.ProductoEntity;
import ProyectoFloristeria.Api.Floristeria.model.entity.TipoProducto;
import ProyectoFloristeria.Api.Floristeria.model.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author Luis
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private FloristeriaService floristeriaService;

    public ProductoDto crearArbol(long idTienda, String altura, double precio) {
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

    public ProductoDto crearDecoracion(long idTienda, String material, double precio) {
        FloristeriaEntity tienda = floristeriaService.encuentraFloristeria(idTienda);
        return castearADto(productoRepository.save(ProductoEntity.builder()
                .tipo(TipoProducto.DECORACION)
                .caracteristica(material)
                .precio(precio)
                .floristeria(tienda)
                .build()));
    }

    /**
     * Castea un objeto de tipo ProductoEntity a ProductoDto
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
