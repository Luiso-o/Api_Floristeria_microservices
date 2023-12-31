package ProyectoFloristeria.Api.Floristeria.Dto;

import ProyectoFloristeria.Api.Floristeria.enumeraciones.BranchCountries;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@ApiModel(description = "DTO para representar una floristería")
public class StoreDto {

    @Schema(description = "Identificador único de Floristeria",example = "6534da9376380c7b7ccdde67")
    private String idFloristeria;
    @Schema(description = "Nombre de la floristeria", example = "Amapola")
    private String nombre;
    @Schema(description = "Fecha en la que se dió de lata la tienda", example = "2023-10-20")
    private LocalDate fechaDeApertura;
    @Schema(description = "País en el que se encuentra la floristería", example = "España")
    private BranchCountries pais;
}
