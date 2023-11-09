package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Dto.TicketDto;
import reactor.core.publisher.Mono;

public interface TicketService {

    Mono<TicketDto>createNewTicket(String idStore);

}
