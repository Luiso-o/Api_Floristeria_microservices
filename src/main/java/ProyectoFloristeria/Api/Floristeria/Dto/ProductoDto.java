package ProyectoFloristeria.Api.Floristeria.Dto;

import ProyectoFloristeria.Api.Floristeria.Documents.TiendaDocument;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.TipoProducto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDto {
    @Schema(description = "Identificador único de Producto",example = "6534da9376380c7b7ccdde67")
    private String idProducto;
    @Schema(description = "Puede ser : Arbol, Flor o Decoración",example = "ARBOL")
    private TipoProducto tipoProducto;
    @Schema(description = "Puede ser tamaño, color o material dependiendo del tipo",example = "Plástico")
    private Object parametroGenerico;
    @Schema(description = "Valor del producto",example = "9.99")
    private double precio;
}
