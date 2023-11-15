package ProyectoFloristeria.Api.Floristeria.Dto;

import ProyectoFloristeria.Api.Floristeria.enumeraciones.BranchCountries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TicketDtoTest {

    private TicketDto ticket;
    private LocalDate fecha;

    @BeforeEach
    void setUp() {
        fecha = LocalDate.now();
        ticket = new TicketDto("6551f6bbdfe69c7bf12d69bc",fecha,"Green Garden",new ArrayList<>(),80.0);
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
        TicketDto ticket2 = new TicketDto("6551f6bbdfe69c7bf12d69bc",fecha,"Green Garden",new ArrayList<>(),80.0);

        assertEquals(ticket,ticket2);
        assertEquals(ticket2,ticket);
    }

    @Test
    void canEqual() {
        StoreDto store = new StoreDto("6534da9376380c7b7ccdde67","Girasol", fecha, BranchCountries.FRANCIA);
        TicketDto ticket2 = new TicketDto("6551f6bbdfe69c7bf12d69bc",fecha,"Green Garden",new ArrayList<>(),80.0);

        assertTrue(ticket2.canEqual(ticket));
        assertTrue(ticket.canEqual(ticket2));

        assertFalse(store.canEqual(ticket2));
        assertFalse(ticket2.canEqual(store));

    }

    @Test
    void testHashCode() {
        TicketDto ticket2 = new TicketDto("6551f6bbdfe69c7bf12d69bc",fecha,"Green Garden",new ArrayList<>(),80.0);
        assertEquals(ticket.hashCode(),ticket2.hashCode());
    }

    @Test
    void builder() {
        TicketDto ticket = TicketDto.builder()
                .id("6551f6bbdfe69c7bf12d69bc")
                .fechaDeCreacion(fecha)
                .nombreTienda("Green Garden")
                .misProductos(new ArrayList<>())
                .total(66.82)
                .build();

        assertEquals("6551f6bbdfe69c7bf12d69bc",ticket.getId());
        assertEquals(fecha,ticket.getFechaDeCreacion());
        assertEquals("Green Garden",ticket.getNombreTienda());
        assertEquals(new ArrayList<>(),ticket.getMisProductos());
        assertEquals(66.82,ticket.getTotal());
    }
}