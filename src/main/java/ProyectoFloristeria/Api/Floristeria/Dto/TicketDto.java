package ProyectoFloristeria.Api.Floristeria.Dto;

import ProyectoFloristeria.Api.Floristeria.Documents.ProductDocument;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@Builder
@ApiModel(description = "DTO para representar un Ticket")
public class TicketDto {
    @Schema(description = "Identificador único de ticket",example = "6534da9376380c7b7ccdde67")
    private String id;
    @Schema(description = "Fecha en la que se creó el ticket",example = "2023-10-30T23:00:00.000+00:00")
    private LocalDate fechaDeCreacion;
    @Schema(description = "Nombre de la tienda que generó el ticket", example = "Amapola")
    private String nombreTienda;
    @Schema(description = "Lista de productos")
    private List<ProductDocument> misProductos;
    @Schema(description = "Valor Total de la compra", example = "20.99")
    private Double total;
}
