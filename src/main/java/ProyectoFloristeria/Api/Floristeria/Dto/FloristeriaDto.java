package ProyectoFloristeria.Api.Floristeria.Dto;

import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@ApiModel(description = "DTO para representar una floristería")
public class FloristeriaDto {

    @Schema(description = "Identificador único de Floristeria",example = "1")
    private long idFloristeria;
    @Schema(description = "Nombre de la floristeria", example = "Amapola")
    private String nombre;
    @Schema(description = "País en el que se encuentra la floristería", example = "España")
    private String pais;
}
