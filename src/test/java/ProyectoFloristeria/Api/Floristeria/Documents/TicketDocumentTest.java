package ProyectoFloristeria.Api.Floristeria.Documents;

import ProyectoFloristeria.Api.Floristeria.enumeraciones.BranchCountries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TicketDocumentTest {

    private TicketDocument ticket;
    private LocalDate fecha;
    private StoreDocument store;

    @BeforeEach
    void setUp(){
        fecha = LocalDate.now();
        store = new StoreDocument("6534da9376380c7b7ccdde67","Green Garden", fecha, BranchCountries.FRANCIA,new ArrayList<>(),new ArrayList<>());
        ticket = new TicketDocument("6551f6bbdfe69c7bf12d69bc",fecha,"Green Garden",new ArrayList<>(),80.0,store);
    }

    @Test
    void getId() {
        assertEquals("6551f6bbdfe69c7bf12d69bc",ticket.getId());
    }

    @Test
    void getFechaDeCreacion() {
        assertEquals(fecha,ticket.getFechaDeCreacion());
    }

    @Test
    void getNombreTienda() {
        assertEquals("Green Garden",ticket.getNombreTienda());
    }

    @Test
    void getMisProductos() {
        assertEquals(new ArrayList<>(),ticket.getMisProductos());
    }

    @Test
    void getTotal() {
        assertEquals(80.0,ticket.getTotal());
    }

    @Test
    void getTienda() {
        assertEquals(store,ticket.getTienda());
    }

    @Test
    void setId() {
        ticket.setId("65510a49e406ee2ed03b6741");
        assertEquals("65510a49e406ee2ed03b6741",ticket.getId());
    }

    @Test
    void setFechaDeCreacion() {
        LocalDate newDate = LocalDate.parse("2023-11-10");
        ticket.setFechaDeCreacion(newDate);
        assertEquals(newDate,ticket.getFechaDeCreacion());
    }

    @Test
    void setNombreTienda() {
        ticket.setNombreTienda("Amaya");
        assertEquals("Amaya",ticket.getNombreTienda());
    }

    @Test
    void setTotal() {
        ticket.setTotal(89.9);
        assertEquals(89.9,ticket.getTotal());
    }

    @Test
    void testEquals() {
        TicketDocument ticket2 = new TicketDocument("6551f6bbdfe69c7bf12d69bc",fecha,"Green Garden",new ArrayList<>(),80.0,store);

        assertEquals(ticket,ticket2);
        assertEquals(ticket2,ticket);
    }

    @Test
    void canEqual() {
        StoreDocument store = new StoreDocument("6534da9376380c7b7ccdde67","Girasol", fecha, BranchCountries.FRANCIA,new ArrayList<>(),new ArrayList<>());
        TicketDocument ticket2 = new TicketDocument("6551f6bbdfe69c7bf12d69bc",fecha,"Green Garden",new ArrayList<>(),80.0,store);

        assertTrue(ticket2.canEqual(ticket));
        assertTrue(ticket.canEqual(ticket2));

        assertFalse(store.canEqual(ticket2));
        assertFalse(ticket2.canEqual(store));
    }

    @Test
    void testHashCode() {
        TicketDocument ticket2 = new TicketDocument("6551f6bbdfe69c7bf12d69bc",fecha,"Green Garden",new ArrayList<>(),80.0,store);
        assertEquals(ticket.hashCode(),ticket2.hashCode());
    }

    @Test
    void builder() {
        TicketDocument ticketBuilder = TicketDocument.builder()
                .id("6551f6bbdfe69c7bf12d69bc")
                .fechaDeCreacion(fecha)
                .nombreTienda("Green Garden")
                .misProductos(new ArrayList<>())
                .total(99.9)
                .tienda(store)
                .build();

        assertEquals("6551f6bbdfe69c7bf12d69bc",ticketBuilder.getId());
        assertEquals(fecha,ticketBuilder.getFechaDeCreacion());
        assertEquals("Green Garden",ticketBuilder.getNombreTienda());
        assertEquals(new ArrayList<>(),ticketBuilder.getMisProductos());
        assertEquals(99.9,ticketBuilder.getTotal());
        assertEquals(store,ticketBuilder.getTienda());
    }

    @Test
    void noArgConstructor(){
        TicketDocument ticketDocument = new TicketDocument();
        assertNotNull(ticketDocument);
    }

}