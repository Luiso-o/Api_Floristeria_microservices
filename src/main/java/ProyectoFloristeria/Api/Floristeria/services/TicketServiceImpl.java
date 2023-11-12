package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Documents.ProductDocument;
import ProyectoFloristeria.Api.Floristeria.Documents.StoreDocument;
import ProyectoFloristeria.Api.Floristeria.Documents.TicketDocument;
import ProyectoFloristeria.Api.Floristeria.Dto.TicketDto;
import ProyectoFloristeria.Api.Floristeria.helper.DocumentToDtoConverter;
import ProyectoFloristeria.Api.Floristeria.repositories.ProductRepository;
import ProyectoFloristeria.Api.Floristeria.repositories.StoreRepository;
import ProyectoFloristeria.Api.Floristeria.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService{
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DocumentToDtoConverter converter;

    @Override
    public Mono<TicketDocument> createNewTicket(String idStore) {
        String idValidado = converter.verificaId(idStore);

        return storeRepository.findById(idValidado)
                .flatMap(store -> {
                    TicketDocument ticket = TicketDocument.builder()
                            .fechaDeCreacion(LocalDate.now())
                            .nombreTienda(store.getNombre())
                            .misProductos(new ArrayList<>())
                            .total(0.0)
                            .tienda(store)
                            .build();

                    return Mono.just(ticket);
                });
    }

    @Override
    public Mono<TicketDocument> createListOfProducts(List<String> productIds, Mono<TicketDocument> ticketMono) {
        Mono<StoreDocument> storeMono = ticketMono.map(TicketDocument::getTienda);

        return storeMono.flatMap(store -> {
            List<ProductDocument> allProducts = store.getMisProductos();

            List<ProductDocument> selectedProducts = allProducts.stream()
                    .filter(product -> productIds.contains(product.getId()))
                    .collect(Collectors.toList());

            return ticketMono.flatMap(ticketDocument -> {
                        ticketDocument.setMisProductos(selectedProducts);
                        return Mono.just(ticketDocument);
                    })
                    .flatMap(updatedTicket -> {
                        allProducts.removeAll(selectedProducts);
                        store.setMisProductos(allProducts);

                        return storeRepository.save(store)
                                .thenReturn(updatedTicket)
                                .onErrorResume(error -> Mono.error(new RuntimeException("Error al guardar la tienda", error)));
                    });
        });
    }

    @Override
    public Mono<TicketDto> convertAndSaveTicket(Mono<TicketDocument> ticketDocumentMono) {
        return ticketDocumentMono
                .flatMap(ticketRepository::save)
                .flatMap(savedTicketDocument -> converter.convertTicketDocumentToTicketDto(Mono.just(savedTicketDocument)));
    }


}
