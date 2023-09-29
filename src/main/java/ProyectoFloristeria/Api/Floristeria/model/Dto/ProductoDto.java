package ProyectoFloristeria.Api.Floristeria.model.Dto;

import ProyectoFloristeria.Api.Floristeria.model.entity.TipoProducto;
import io.swagger.annotations.*;
import lombok.*;

/**
 * @author Luis
 */
@Builder
@Data
@AllArgsConstructor
@ApiModel(description = "DTO para representar un Producto")
public class ProductoDto {

    @ApiModelProperty(value = "Tipo de producto (Arbol, Flor o Decoracion)", example = "Arbol")
    private TipoProducto tipo;
    @ApiModelProperty(value = "Características del producto", example = "1.5 cm")
    private String caracteristica;
    @ApiModelProperty(value = "Precio del producto en euros", example = "50.0")
    private double precio;
    @ApiModelProperty(value = "Nombre de la floristería a la que pertenece el producto")
    private String nombreTienda;

}
