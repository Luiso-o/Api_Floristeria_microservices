package ProyectoFloristeria.Api.Floristeria.controllers;

import ProyectoFloristeria.Api.Floristeria.model.Dto.ProductoDto;
import ProyectoFloristeria.Api.Floristeria.model.services.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Luis
 */
@RestController
@RequestMapping("/api/v1/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Crea un arbol", description = "Introduce las características de un arbol como su altura y su precio")
    @ApiResponse(responseCode = "201", description = "Arbol creado con éxito")
    @PostMapping(value = "arbol/add")
    public ResponseEntity<ProductoDto>crearNuevoArbol(
            @RequestParam long idTienda,
            @RequestParam String altura,
            @RequestParam double precio
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.crearArbol(idTienda,altura,precio));
    }

    @Operation(summary = "Crea una flor", description = "Introduce las características de una flor como su color y su precio")
    @ApiResponse(responseCode = "201", description = "Flor creada con éxito")
    @PostMapping(value = "flor/add")
    public ResponseEntity<ProductoDto>crearNuevaFlor(
            @RequestParam long idTienda,
            @RequestParam String Color,
            @RequestParam double precio
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.crearFlor(idTienda,Color,precio));
    }

    @Operation(summary = "Crea una flor", description = "Introduce las características de una decoración como de que material está echa y su precio")
    @ApiResponse(responseCode = "201", description = "Decoración creada con éxito")
    @PostMapping(value = "decoracion/add")
    public ResponseEntity<ProductoDto>crearNuevaDecoracion(
            @RequestParam long idTienda,
            @RequestParam String material,
            @RequestParam double precio
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.crearDecoracion(idTienda,material,precio));
    }

}
