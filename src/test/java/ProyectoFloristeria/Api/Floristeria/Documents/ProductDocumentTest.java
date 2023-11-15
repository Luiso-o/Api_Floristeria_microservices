package ProyectoFloristeria.Api.Floristeria.Documents;

import ProyectoFloristeria.Api.Floristeria.enumeraciones.DecorationMaterials;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.ProductType;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.BranchCountries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductDocumentTest {

    private ProductDocument tree;
    private ProductDocument flower;
    private ProductDocument decor;
    private StoreDocument store;

    @BeforeEach
    void setUp() {
        LocalDate fecha = LocalDate.now();
        store = new StoreDocument("653d26ea747fff5e1de78177","Girasol", fecha, BranchCountries.ARGENTINA,new ArrayList<>(),new ArrayList<>());

        tree = new ProductDocument("65510a49e406ee2ed03b6741", ProductType.ARBOL,3.2,30,store);
        flower = new ProductDocument("6551f6bbdfe69c7bf12d69bc", ProductType.FLOR,"Amarilla",2.5,store);
        decor = new ProductDocument("653d26ea747fff5e1de78177", ProductType.DECORACION, DecorationMaterials.MADERA,25,store);
    }

    @Test
    void getId() {
        assertThat("65510a49e406ee2ed03b6741").isEqualTo(tree.getId());
        assertThat("6551f6bbdfe69c7bf12d69bc").isEqualTo(flower.getId());
        assertThat("653d26ea747fff5e1de78177").isEqualTo(decor.getId());
    }

    @Test
    void getTipoProducto() {
        assertThat(ProductType.ARBOL).isEqualTo(tree.getTipoProducto());
        assertThat(ProductType.FLOR).isEqualTo(flower.getTipoProducto());
        assertThat(ProductType.DECORACION).isEqualTo(decor.getTipoProducto());
    }

    @Test
    void getParametroGenerico() {
        assertThat(3.2).isEqualTo(tree.getParametroGenerico());
        assertThat("Amarilla").isEqualTo(flower.getParametroGenerico());
        assertThat(DecorationMaterials.MADERA).isEqualTo(decor.getParametroGenerico());
    }

    @Test
    void getPrecio() {
        assertThat(30.0).isEqualTo(tree.getPrecio());
        assertThat(2.5).isEqualTo(flower.getPrecio());
        assertThat(25.0).isEqualTo(decor.getPrecio());
    }

    @Test
    void getTienda() {
        assertThat(store).isEqualTo(tree.getTienda());
        assertThat(store).isEqualTo(flower.getTienda());
        assertThat(store).isEqualTo(decor.getTienda());
    }

    @Test
    void setId() {
        String newIdTree = "6a1e2f4bbdfe69c7bf12d69bc";
        String newIdFlower = "3908e2fbbdfe69c7bf12d69bc";
        String newIdDecor = "a5b2c4d6bdfe69c7bf12d69bc";

        tree.setId(newIdTree);
        flower.setId(newIdFlower);
        decor.setId(newIdDecor);

        assertThat(newIdTree).isEqualTo(tree.getId());
        assertThat(newIdFlower).isEqualTo(flower.getId());
        assertThat(newIdDecor).isEqualTo(decor.getId());

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
    void canEqual() {
       ProductDocument tree2 = new ProductDocument("65510a49e406ee2ed03b6741", ProductType.ARBOL,3.2,30,store);

        assertTrue(tree.canEqual(tree2));
        assertTrue(tree2.canEqual(tree));

        assertFalse(tree.canEqual(store));
        assertFalse(store.canEqual(tree));

    }

    @Test
    void testHashCode() {
        ProductDocument tree2 = new ProductDocument("65510a49e406ee2ed03b6741", ProductType.ARBOL,3.2,30,store);
        assertEquals(tree.hashCode(),tree2.hashCode());
    }

    @Test
    void builder() {
        ProductDocument productBuilder = ProductDocument.builder()
                .id("65510a49e406ee2ed03b6741")
                .tipoProducto(ProductType.ARBOL)
                .parametroGenerico(3.2)
                .precio(30)
                .tienda(store)
                .build();

        assertEquals("65510a49e406ee2ed03b6741",productBuilder.getId());
        assertEquals(ProductType.ARBOL,productBuilder.getTipoProducto());
        assertEquals(3.2,productBuilder.getParametroGenerico());
        assertEquals(30, productBuilder.getPrecio());
        assertEquals(store,productBuilder.getTienda());
    }

    @Test
    void noArgConstructorTest(){
        ProductDocument productDocument = new ProductDocument();
        assertNotNull(productDocument);
    }

}