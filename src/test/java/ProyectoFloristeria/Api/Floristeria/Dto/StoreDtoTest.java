package ProyectoFloristeria.Api.Floristeria.Dto;


import ProyectoFloristeria.Api.Floristeria.enumeraciones.ProductType;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.BranchCountries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StoreDtoTest {

    private StoreDto store;
    private LocalDate fecha;

    @BeforeEach
    void setUp() {
        fecha = LocalDate.now();
        store = new StoreDto("6534da9376380c7b7ccdde67","Girasol", fecha, BranchCountries.FRANCIA);
    }

    @Test
    void getIdFloristeria() {
        assertEquals("6534da9376380c7b7ccdde67",store.getIdFloristeria());
    }

    @Test
    void getNombre() {
        assertEquals("Girasol",store.getNombre());
    }

    @Test
    void getFechaDeApertura() {
        assertEquals(fecha,store.getFechaDeApertura());
    }

    @Test
    void getPais() {
        assertEquals(BranchCountries.FRANCIA,store.getPais());
    }

    @Test
    void setIdFloristeria() {
        store.setIdFloristeria("65510a49e406ee2ed03b6741");
        assertEquals("65510a49e406ee2ed03b6741",store.getIdFloristeria());
    }

    @Test
    void setNombre() {
        store.setNombre("Amapola");
        assertEquals("Amapola",store.getNombre());
    }

    @Test
    void setFechaDeApertura() {
        LocalDate newDate = LocalDate.parse("2023-11-10");
        store.setFechaDeApertura(newDate);
        assertEquals(newDate,store.getFechaDeApertura());
    }

    @Test
    void setPais() {
        store.setPais(BranchCountries.ALEMANIA);
        assertEquals(BranchCountries.ALEMANIA,store.getPais());
    }

    @Test
    void testEquals() {
       StoreDto store2 = new StoreDto("6534da9376380c7b7ccdde67","Girasol", fecha, BranchCountries.FRANCIA);

       assertEquals(store2,store);
       assertEquals(store,store2);
    }

    @Test
    void canEqual() {
        StoreDto store2 = new StoreDto("6534da9376380c7b7ccdde67","Girasol", fecha, BranchCountries.FRANCIA);
        ProductDto tree = new ProductDto("65510a49e406ee2ed03b6741", ProductType.ARBOL,3.2,30);

        assertTrue(store.canEqual(store2));
        assertTrue(store2.canEqual(store));

        assertFalse(store.canEqual(tree));
        assertFalse(tree.canEqual(store));

    }

    @Test
    void testHashCode() {
        StoreDto store2 = new StoreDto("6534da9376380c7b7ccdde67","Girasol", fecha, BranchCountries.FRANCIA);
        assertEquals(store.hashCode(),store2.hashCode());
    }

    @Test
    void builder() {
        StoreDto storeBuilder = StoreDto.builder()
                .idFloristeria("3908e2fbbdfe69c7bf12d69bc")
                .nombre("Girasol")
                .fechaDeApertura(fecha)
                .pais(BranchCountries.ALEMANIA)
                .build();

        assertEquals("3908e2fbbdfe69c7bf12d69bc", storeBuilder.getIdFloristeria());
        assertEquals("Girasol", storeBuilder.getNombre());
        assertEquals(fecha,storeBuilder.getFechaDeApertura());
        assertEquals(BranchCountries.ALEMANIA,storeBuilder.getPais());
    }
}