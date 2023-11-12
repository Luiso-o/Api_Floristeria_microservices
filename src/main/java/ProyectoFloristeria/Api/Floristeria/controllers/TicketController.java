package ProyectoFloristeria.Api.Floristeria.controllers;

import ProyectoFloristeria.Api.Floristeria.Documents.TicketDocument;
import ProyectoFloristeria.Api.Floristeria.Dto.TicketDto;
import ProyectoFloristeria.Api.Floristeria.services.TicketServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v2/ticket")
public class TicketController {

    @Autowired
    private TicketServiceImpl ticketService;

    @Operation(summary = "Generar una compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operación realizada con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @PostMapping(value = "addTicket")
    public Mono<TicketDto> createTicket(
          @RequestParam String idStore,
          @RequestParam List<String> idProducts
    ){
      Mono<TicketDocument> createdTicket = ticketService.createNewTicket(idStore);
      Mono<TicketDocument> ticketWhitProducts = ticketService.createListOfProducts(idProducts,createdTicket);
      return ticketService.convertAndSaveTicket(ticketWhitProducts);
    }




}
