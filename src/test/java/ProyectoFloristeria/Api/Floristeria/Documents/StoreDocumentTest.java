package ProyectoFloristeria.Api.Floristeria.Documents;

import ProyectoFloristeria.Api.Floristeria.enumeraciones.ProductType;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.BranchCountries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StoreDocumentTest {

    private StoreDocument store;
    private LocalDate fecha;

    @BeforeEach
    void setUp() {
        fecha = LocalDate.now();
        store = new StoreDocument("6534da9376380c7b7ccdde67","Girasol", fecha, BranchCountries.FRANCIA,new ArrayList<>(),new ArrayList<>());
    }

    @Test
    void getId() {
        assertEquals("6534da9376380c7b7ccdde67",store.getId());
    }

    @Test
    void getNombre() {
        assertEquals("Girasol",store.getNombre());
    }

    @Test
    void getFechaApertura() {
        assertEquals(fecha,store.getFechaApertura());
    }

    @Test
    void getPais() {
        assertEquals(BranchCountries.FRANCIA,store.getPais());
    }

    @Test
    void getMisProductos() {
        assertEquals(new ArrayList<>(),store.getMisProductos());
    }

    @Test
    void getTickets() {
        assertEquals(new ArrayList<>(),store.getTickets());
    }

    @Test
    void setId() {
        store.setId("65510a49e406ee2ed03b6741");
        assertEquals("65510a49e406ee2ed03b6741",store.getId());
    }

    @Test
    void setNombre() {
        store.setNombre("Amapola");
        assertEquals("Amapola",store.getNombre());
    }

    @Test
    void setFechaApertura() {
        LocalDate newDate = LocalDate.parse("2023-11-10");
        store.setFechaApertura(newDate);
        assertEquals(newDate,store.getFechaApertura());
    }

    @Test
    void setPais() {
        store.setPais(BranchCountries.ALEMANIA);
        assertEquals(BranchCountries.ALEMANIA,store.getPais());
    }

    @Test
    void testEquals() {
        StoreDocument store2 = new StoreDocument("6534da9376380c7b7ccdde67","Girasol", fecha, BranchCountries.FRANCIA,new ArrayList<>(),new ArrayList<>());

        assertEquals(store2,store);
        assertEquals(store,store2);
    }

    @Test
    void canEqual() {
        StoreDocument store2 = new StoreDocument("6534da9376380c7b7ccdde67","Girasol", fecha, BranchCountries.FRANCIA,new ArrayList<>(),new ArrayList<>());
        ProductDocument tree = new ProductDocument("65510a49e406ee2ed03b6741", ProductType.ARBOL,3.2,30,store);

        assertTrue(store.canEqual(store2));
        assertTrue(store2.canEqual(store));

        assertFalse(store.canEqual(tree));
        assertFalse(tree.canEqual(store));
    }

    @Test
    void testHashCode() {
        StoreDocument store2 = new StoreDocument("6534da9376380c7b7ccdde67","Girasol", fecha, BranchCountries.FRANCIA,new ArrayList<>(),new ArrayList<>());
        assertEquals(store.hashCode(),store2.hashCode());
    }

    @Test
    void builder() {
        StoreDocument storeBuilder = StoreDocument.builder()
                .id("6534da9376380c7b7ccdde67")
                .nombre("Girasol")
                .fechaApertura(fecha)
                .pais(BranchCountries.ARGENTINA)
                .misProductos(new ArrayList<>())
                .tickets(new ArrayList<>())
                .build();

        assertEquals("6534da9376380c7b7ccdde67", storeBuilder.getId());
        assertEquals("Girasol", storeBuilder.getNombre());
        assertEquals(fecha,storeBuilder.getFechaApertura());
        assertEquals(BranchCountries.ARGENTINA,storeBuilder.getPais());
        assertEquals(new ArrayList<>(),storeBuilder.getMisProductos());
        assertEquals(new ArrayList<>(),storeBuilder.getTickets());
    }

    @Test
    void noArgConstructor(){
        StoreDocument storeDocument = new StoreDocument();
        assertNotNull(storeDocument);
    }
}