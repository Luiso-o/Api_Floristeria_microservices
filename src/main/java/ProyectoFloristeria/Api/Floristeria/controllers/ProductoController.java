package ProyectoFloristeria.Api.Floristeria.controllers;

import ProyectoFloristeria.Api.Floristeria.model.Dto.ProductoDto;
import ProyectoFloristeria.Api.Floristeria.model.entity.ProductoEntity;
import ProyectoFloristeria.Api.Floristeria.model.entity.enumeraciones.MaterialesDecoracion;
import ProyectoFloristeria.Api.Floristeria.model.entity.enumeraciones.TipoProducto;
import ProyectoFloristeria.Api.Floristeria.model.services.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
            @RequestParam  double altura,
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

    @Operation(summary = "Crea una decoración", description = "Introduce las características de una decoración como de que material está echa y su precio")
    @ApiResponse(responseCode = "201", description = "Decoración creada con éxito")
    @PostMapping(value = "decoracion/add")
    public ResponseEntity<ProductoDto>crearNuevaDecoracion(
            @RequestParam long idTienda,
            @RequestParam MaterialesDecoracion material,
            @RequestParam double precio
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.crearDecoracion(idTienda,material,precio));
    }

    @Operation(summary = "Ver stock de floristeria", description = "Muestra todos los productos que estan en el stock de una tienda")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tienda encontrada : "),
            @ApiResponse(responseCode = "204", description = "Lista Vacía : "),
            @ApiResponse(responseCode = "500", description = "Id de tienda no encontrado")
    })
    @GetMapping(value = "stock/getAll")
    public ResponseEntity<?> imprimeStockDeTienda(@RequestParam long idTienda){
        List<ProductoDto> miStock = productoService.imprimeStockDeTienda(idTienda);
        if(!miStock.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(miStock);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock vacío");
        }
    }

    @Operation(summary = "Eliminar un árbol", description = "Elimina un árbol localizándolo por su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Árbol eliminado con éxito : "),
            @ApiResponse(responseCode = "409", description = "Conflicto con el id proporcionado : "),
            @ApiResponse(responseCode = "500", description = "No se encontró un árbol con éste id : ")
    })
    @DeleteMapping(value = "arbol/delete")
    public ResponseEntity<String>retirarArbolDeStock(@RequestParam long idArbol){
        ProductoEntity arbol = productoService.encuentraProducto(idArbol);
        if(arbol.getTipo() == TipoProducto.ARBOL){
            productoService.eliminarProductoDelStock(arbol);
            return ResponseEntity.status(HttpStatus.OK).body("Árbol eliminado con éxito");
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El id proporcionado no coincide con un árbol");
        }
    }

    @Operation(summary = "Eliminar una flor", description = "Elimina una flor localizándola por su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flor eliminada con éxito : "),
            @ApiResponse(responseCode = "409", description = "Conflicto con el id proporcionado : "),
            @ApiResponse(responseCode = "500", description = "No se encontró una flor con éste id : ")
    })
    @DeleteMapping(value = "flor/delete")
    public ResponseEntity<String>retirarFlorDeStock(@RequestParam long idFlor){
        ProductoEntity arbol = productoService.encuentraProducto(idFlor);
        if(arbol.getTipo() == TipoProducto.FLOR){
            productoService.eliminarProductoDelStock(arbol);
            return ResponseEntity.status(HttpStatus.OK).body("Flor eliminada con éxito");
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El id proporcionado no coincide con una flor");
        }
    }

    @Operation(summary = "Eliminar una decoración", description = "Elimina una decoración localizándola por su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Decoración eliminado con éxito : "),
            @ApiResponse(responseCode = "409", description = "Conflicto con el id proporcionado : "),
            @ApiResponse(responseCode = "500", description = "No se encontró una decoración con éste id : ")
    })
    @DeleteMapping(value = "decoracion/delete")
    public ResponseEntity<String>retirarDecoracionDeStock(@RequestParam long idDecoracion){
        ProductoEntity arbol = productoService.encuentraProducto(idDecoracion);
        if(arbol.getTipo() == TipoProducto.FLOR){
            productoService.eliminarProductoDelStock(arbol);
            return ResponseEntity.status(HttpStatus.OK).body("Decoración eliminada con éxito");
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El id proporcionado no coincide con una decoración");
        }
    }

    @Operation(summary = "Ver lista de productos", description = "Verifica la cantidad de tu Stock a nivel global")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operación ejecutada con éxito: "),
    })
    @GetMapping(value = "getAll/cantidades")
    public ResponseEntity<Map<TipoProducto, Integer>> imprimirCantidadesDeProductos() {
        EnumMap<TipoProducto, Integer> cantidades = productoService.listaDeCantidades();
        return ResponseEntity.status(HttpStatus.OK).body(cantidades);
    }


}
