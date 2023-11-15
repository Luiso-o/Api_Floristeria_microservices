package ProyectoFloristeria.Api.Floristeria.Dto;

import ProyectoFloristeria.Api.Floristeria.enumeraciones.DecorationMaterials;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.ProductType;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.BranchCountries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductDtoTest {

    private ProductDto tree;
    private ProductDto flower;
    private ProductDto decor;

    @BeforeEach
    void setUp(){
        tree = new ProductDto("65510a49e406ee2ed03b6741", ProductType.ARBOL,3.2,30);
        flower = new ProductDto("6551f6bbdfe69c7bf12d69bc", ProductType.FLOR,"Amarilla",2.5);
        decor = new ProductDto("653d26ea747fff5e1de78177", ProductType.DECORACION, DecorationMaterials.MADERA,25);
    }

    @Test
    void getIdProducto() {
        assertThat("65510a49e406ee2ed03b6741").isEqualTo(tree.getIdProducto());
        assertThat("6551f6bbdfe69c7bf12d69bc").isEqualTo(flower.getIdProducto());
        assertThat("653d26ea747fff5e1de78177").isEqualTo(decor.getIdProducto());
    }

    @Test
    void getTipoProducto() {
        assertThat(ProductType.ARBOL).isEqualTo(tree.getTipoProducto());
        assertThat(ProductType.FLOR).isEqualTo(flower.getTipoProducto());
        assertThat(ProductType.DECORACION).isEqualTo(decor.getTipoProducto());
    }

    @Test
    void getCaracteristica() {
        assertThat(3.2).isEqualTo(tree.getCaracteristica());
        assertThat("Amarilla").isEqualTo(flower.getCaracteristica());
        assertThat(DecorationMaterials.MADERA).isEqualTo(decor.getCaracteristica());
    }

    @Test
    void getPrecio() {
        assertThat(30.0).isEqualTo(tree.getPrecio());
        assertThat(2.5).isEqualTo(flower.getPrecio());
        assertThat(25.0).isEqualTo(decor.getPrecio());
    }

    @Test
    void setIdProducto() {
        String newIdTree = "6a1e2f4bbdfe69c7bf12d69bc";
        String newIdFlower = "3908e2fbbdfe69c7bf12d69bc";
        String newIdDecor = "a5b2c4d6bdfe69c7bf12d69bc";

    tree.setIdProducto(newIdTree);
    flower.setIdProducto(newIdFlower);
    decor.setIdProducto(newIdDecor);

    assertThat(newIdTree).isEqualTo(tree.getIdProducto());
    assertThat(newIdFlower).isEqualTo(flower.getIdProducto());
    assertThat(newIdDecor).isEqualTo(decor.getIdProducto());

    }

    @Test
    void setPrecio() {

        double priceTree = 5.0;
        double priceFlower = 33.5;
        double priceDecor = 32.9;

        tree.setPrecio(priceTree);
        flower.setPrecio(priceFlower);
        decor.setPrecio(priceDecor);

        assertThat(priceTree).isEqualTo(tree.getPrecio());
        assertThat(priceFlower).isEqualTo(flower.getPrecio());
        assertThat(priceDecor).isEqualTo(decor.getPrecio());
    }

    @Test
    void testEquals() {
        ProductDto treeCopy = new ProductDto("65510a49e406ee2ed03b6741", ProductType.ARBOL, 3.2, 30);

        assertEquals(tree, treeCopy);
        assertEquals(treeCopy, tree);
        assertNotEquals(tree, flower);
    }

    @Test
    void canEqual() {
        ProductDto tree2 = new ProductDto("65510a49e406ee2ed03b6741", ProductType.ARBOL, 3.2, 30);
        StoreDto store = new StoreDto("6534da9376380c7b7ccdde67","Girasol", LocalDate.now(), BranchCountries.ALEMANIA);

        assertTrue(tree.canEqual(tree2));
        assertTrue(tree2.canEqual(tree));

        assertFalse(tree.canEqual(store));
        assertFalse(store.canEqual(tree));
    }

    @Test
    void testHashCode() {
        ProductDto tree2 = new ProductDto("65510a49e406ee2ed03b6741", ProductType.ARBOL, 3.2, 30);
        assertEquals(tree.hashCode(), tree2.hashCode());
    }

    @Test
    void builder() {
        ProductDto flower = ProductDto.builder()
                .idProducto("3908e2fbbdfe69c7bf12d69bc")
                .tipoProducto(ProductType.FLOR)
                .caracteristica("Roja")
                .precio(3)
                .build();

        assertEquals("3908e2fbbdfe69c7bf12d69bc", flower.getIdProducto());
        assertEquals(ProductType.FLOR, flower.getTipoProducto());
        assertEquals("Roja", flower.getCaracteristica());
        assertEquals(3, flower.getPrecio());

    }
}