package ProyectoFloristeria.Api.Floristeria.helper;

import ProyectoFloristeria.Api.Floristeria.Documents.ProductoDocument;
import ProyectoFloristeria.Api.Floristeria.Dto.ProductoDto;
import ProyectoFloristeria.Api.Floristeria.Dto.TiendaDto;
import ProyectoFloristeria.Api.Floristeria.Documents.TiendaDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

@Component
public class Converter {
    private static final Pattern LONG_PATTERN = Pattern.compile("^[0-9a-fA-F]{24}$");
    private static final Logger log = LoggerFactory.getLogger(Converter.class);

    public Mono<TiendaDto> fromFloristeriaEntityToDto(Mono<TiendaDocument> store){
        return store.map(this::toTiendaDto);
    }

    public Mono<ProductoDto> formProductDocumentToProductDto(Mono<ProductoDocument> productoDocumentMono){
        return productoDocumentMono.map(this::toProductoDto);
    }

    public TiendaDto toTiendaDto(TiendaDocument store){
        return TiendaDto.builder()
                .idFloristeria(store.getIdTienda())
                .nombre(store.getNombre())
                .fechaDeApertura(store.getFechaApertura())
                .pais(store.getPais())
                .build();
    }

    public ProductoDto toProductoDto(ProductoDocument productoDocument){
        return ProductoDto.builder()
                .idProducto(productoDocument.getIdProducto())
                .tipoProducto(productoDocument.getTipoProducto())
                .parametroGenerico(productoDocument.getParametroGenerico())
                .precio(productoDocument.getPrecio())
                .build();
    }

    public String verificaId(String id) {
        if (!LONG_PATTERN.matcher(id).matches()) {
            log.info("El formato del ID no es válido: " + id);
            throw new IllegalArgumentException("El formato del ID no es válido: " + id);
        }else{
            return id;
        }
    }

}
