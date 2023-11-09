package ProyectoFloristeria.Api.Floristeria.helper;

import ProyectoFloristeria.Api.Floristeria.Documents.ProductDocument;
import ProyectoFloristeria.Api.Floristeria.Documents.TicketDocument;
import ProyectoFloristeria.Api.Floristeria.Dto.ProductoDto;
import ProyectoFloristeria.Api.Floristeria.Dto.TicketDto;
import ProyectoFloristeria.Api.Floristeria.Dto.TiendaDto;
import ProyectoFloristeria.Api.Floristeria.Documents.StoreDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

@Component
public class DocumentToDtoConverter {
    private static final Pattern LONG_PATTERN = Pattern.compile("^[0-9a-fA-F]{24}$");
    private static final Logger log = LoggerFactory.getLogger(DocumentToDtoConverter.class);

    public Mono<TiendaDto> convertStoreDocumentToDto(Mono<StoreDocument> store){
        return store.map(this::toTiendaDto);
    }

    public Mono<ProductoDto> convertProductDocumentToProductDto(Mono<ProductDocument> productoDocumentMono){
        return productoDocumentMono.map(this::toProductoDto);
    }

    public Mono<TicketDto> convertTicketDocumentToTicketDto(Mono<TicketDocument> ticketDocumentMono){
        return ticketDocumentMono.map(this::toTicketDto);
    }

    public TiendaDto toTiendaDto(StoreDocument store){
        return TiendaDto.builder()
                .idFloristeria(store.getId())
                .nombre(store.getNombre())
                .fechaDeApertura(store.getFechaApertura())
                .pais(store.getPais())
                .build();
    }

    public ProductoDto toProductoDto(ProductDocument productoDocument){
        return ProductoDto.builder()
                .idProducto(productoDocument.getId())
                .tipoProducto(productoDocument.getTipoProducto())
                .caracteristica(productoDocument.getParametroGenerico())
                .precio(productoDocument.getPrecio())
                .build();
    }

    public TicketDto toTicketDto(TicketDocument ticket){
        return TicketDto.builder()
                .id(ticket.getId())
                .fechaDeCreacion(ticket.getFechaDeCreacion())
                .nombreTienda(ticket.getNombreTienda())
                .misProductos(ticket.getMisProductos())
                .total(ticket.getTotal())
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
