package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Documents.StoreDocument;
import ProyectoFloristeria.Api.Floristeria.Documents.TicketDocument;
import ProyectoFloristeria.Api.Floristeria.Dto.TicketDto;
import ProyectoFloristeria.Api.Floristeria.helper.DocumentToDtoConverter;
import ProyectoFloristeria.Api.Floristeria.repositories.StoreRepository;
import ProyectoFloristeria.Api.Floristeria.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;

public class TicketServiceImpl implements TicketService{
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private DocumentToDtoConverter converter;

    @Override
    public Mono<TicketDto> createNewTicket(String idStore) {
    return null;
    }


}
