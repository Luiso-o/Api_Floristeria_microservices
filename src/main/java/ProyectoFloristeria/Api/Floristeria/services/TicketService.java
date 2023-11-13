package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Documents.TicketDocument;
import ProyectoFloristeria.Api.Floristeria.Dto.TicketDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TicketService {

    Mono<TicketDocument>createNewTicket(String idStore);
    Mono<TicketDocument>createListOfProducts(List<String> products, Mono<TicketDocument> ticket);
    Mono<TicketDto>convertAndSaveTicket(Mono<TicketDocument> ticketDocumentMono);
}
