package ProyectoFloristeria.Api.Floristeria.controllers;

import ProyectoFloristeria.Api.Floristeria.Dto.ProductoDto;
import ProyectoFloristeria.Api.Floristeria.Dto.TiendaDto;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.MaterialesDecoracion;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.PaisesSucursales;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.TipoProducto;
import ProyectoFloristeria.Api.Floristeria.services.ProductoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v2/producto")
public class ProductoController {

    @Autowired
    private ProductoServiceImpl productoService;

    @Operation(summary = "Da de alta un nuevo árbol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operación realizada con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @PostMapping(value = "addTree")
    public ResponseEntity<Mono<ProductoDto>>createNewTree(
            @RequestParam double altura,
            @RequestParam double precio,
            @RequestParam String idTienda
    ){
        Mono<ProductoDto> newTree = productoService.createTree(altura,precio,idTienda);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTree);
    }

    @Operation(summary = "Da de alta una nueva flor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operación realizada con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @PostMapping(value = "addFlower")
    public ResponseEntity<Mono<ProductoDto>>createNewFlower(
            @RequestParam String color,
            @RequestParam double precio,
            @RequestParam String idTienda
    ){
        Mono<ProductoDto> newFlower = productoService.createFlower(color,precio,idTienda);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFlower);
    }

    @Operation(summary = "Da de alta una nueva decoración")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operación realizada con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @PostMapping(value = "addDecor")
    public ResponseEntity<Mono<ProductoDto>>createNewDecor(
            @RequestParam MaterialesDecoracion material,
            @RequestParam double precio,
            @RequestParam String idTienda
    ){
        Mono<ProductoDto> newDecor = productoService.createDecor(material,precio,idTienda);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDecor);
    }

    @Operation(summary = "Ver todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación realizada con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @GetMapping("/getAll")
    public ResponseEntity<Flux<ProductoDto>> getAllProducts() {
        Flux<ProductoDto> allProducts = productoService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @Operation(summary = "Selecciona un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @GetMapping("getOne")
    public Mono<ResponseEntity<ProductoDto>> buscarUnProducto(
            @RequestParam String idProducto
    ) {
        return productoService.getProductById(idProducto)
                .map(productoEncontrado -> ResponseEntity.status(HttpStatus.OK).body(productoEncontrado));
    }

    @Operation(summary = "Modifica datos de un árbol existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arbol modificado"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @PutMapping("updateTree")
    public Mono<ResponseEntity<ProductoDto>> modificarArbol(
            @RequestParam String idArbol,
            @RequestParam double altura,
            @RequestParam double precio
    ) {
        return productoService.updateTree(idArbol,altura,precio)
                .map(arbolModificado -> ResponseEntity.status(HttpStatus.OK).body(arbolModificado));
    }

    @Operation(summary = "Modifica datos de una flor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flor modificada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @PutMapping("updateFlower")
    public Mono<ResponseEntity<ProductoDto>> modificarFlor(
            @RequestParam String idFlor,
            @RequestParam String color,
            @RequestParam double precio
    ) {
        return productoService.updateFlower(idFlor,color,precio)
                .map(florModificada -> ResponseEntity.status(HttpStatus.OK).body(florModificada));
    }

    @Operation(summary = "Modifica datos de una decoración existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Decoración modificada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @PutMapping("updateDecor")
    public Mono<ResponseEntity<ProductoDto>> modificarDecoracion(
            @RequestParam String idDecor,
            @RequestParam MaterialesDecoracion material,
            @RequestParam double precio
    ) {
        return productoService.updateDecor(idDecor,material,precio)
                .map(decorModificada -> ResponseEntity.status(HttpStatus.OK).body(decorModificada));
    }
}
