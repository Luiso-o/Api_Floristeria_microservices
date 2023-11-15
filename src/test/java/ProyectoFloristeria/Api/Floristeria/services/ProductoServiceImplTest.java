package ProyectoFloristeria.Api.Floristeria.services;

import ProyectoFloristeria.Api.Floristeria.Documents.ProductDocument;
import ProyectoFloristeria.Api.Floristeria.Documents.StoreDocument;
import ProyectoFloristeria.Api.Floristeria.Dto.ProductDto;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.DecorationMaterials;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.ProductType;
import ProyectoFloristeria.Api.Floristeria.helper.DocumentToDtoConverter;
import ProyectoFloristeria.Api.Floristeria.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductoServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private DocumentToDtoConverter converter;
    @InjectMocks
    private ProductoServiceImpl productoService;

    private List<ProductDocument>productDocumentList;
    private List<ProductDto>productDtoList;
    private ProductDocument treeDocument;
    private ProductDocument flowerDocument;
    private ProductDocument decorDocument;
    private ProductDto treeDto;
    private ProductDto flowerDto;
    private ProductDto decorDto;



    @BeforeEach
    void SetUp(){
        MockitoAnnotations.openMocks(this);

        treeDocument = new ProductDocument("5f4adc845e826e001bdc3c9a", ProductType.ARBOL,3,90.9,new StoreDocument());
        flowerDocument = new ProductDocument("5f4adc845e826e001bdc3c9b", ProductType.FLOR,"Roja",8.5,new StoreDocument());
        decorDocument = new ProductDocument("5f4adc845e826e001bdc3c9c", ProductType.DECORACION, DecorationMaterials.MADERA,80.0,new StoreDocument());

        treeDto = new ProductDto("5f4adc845e826e001bdc3c9a", ProductType.ARBOL,3,90.9);
        flowerDto = new ProductDto("5f4adc845e826e001bdc3c9b", ProductType.FLOR,"Roja",8.2);
        decorDto = new ProductDto("5f4adc845e826e001bdc3c9c", ProductType.DECORACION, DecorationMaterials.MADERA,30.0);

        productDocumentList = new ArrayList<>();
        productDocumentList.add(treeDocument);
        productDocumentList.add(flowerDocument);
        productDocumentList.add(decorDocument);

        productDtoList = new ArrayList<>();
        productDtoList.add(treeDto);
        productDtoList.add(flowerDto);
        productDtoList.add(decorDto);

    }

    @Test
    void getAllProductsTest() {
        when(productRepository.findAll()).thenReturn(Flux.fromIterable(productDocumentList));

        when(converter.convertProductDocumentToProductDto(any(Mono.class)))
                .thenReturn(Mono.just(productDtoList.get(0)));

        StepVerifier.create(productoService.getAllProducts())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void getAllProductsErrorTest() {
        when(productRepository.findAll()).thenReturn(Flux.error(new RuntimeException("Simulación de error al obtener la lista")));
        when(converter.convertProductDocumentToProductDto(Mockito.any())).thenReturn(null);

        StepVerifier.create(productoService.getAllProducts())
                .expectNextCount(0)
                .verifyComplete();
    }

}