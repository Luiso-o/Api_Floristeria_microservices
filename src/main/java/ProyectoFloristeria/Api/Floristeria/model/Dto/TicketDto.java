package ProyectoFloristeria.Api.Floristeria.model.Dto;

import io.swagger.annotations.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@ApiModel(description = "DTO para representar un Ticket")
public class TicketDto {

    @ApiModelProperty(value = "ID del ticket")
    private long idTicket;
    @ApiModelProperty(value = "Fecha del ticket")
    private LocalDate fecha;
    @ApiModelProperty(value = "Información del producto asociado al ticket")
    private ProductoDto producto;
}
