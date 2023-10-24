package ProyectoFloristeria.Api.Floristeria.controllers;

import ProyectoFloristeria.Api.Floristeria.Dto.TiendaDto;
import ProyectoFloristeria.Api.Floristeria.enumeraciones.PaisesSucursales;
import ProyectoFloristeria.Api.Floristeria.services.TiendaServiceImpl;
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
@RequestMapping("/api/v2/floristeria")
public class TiendaController {

    @Autowired
    private TiendaServiceImpl tiendaService;

    @Operation(summary = "Da de alta una nueva tienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operación realizada con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @PostMapping("add")
    public Mono<ResponseEntity<TiendaDto>> registrarNuevaTienda(
            @RequestParam (required = false, defaultValue = "Mi Floristeria") String nombre,
            @RequestParam PaisesSucursales pais
    ) {
        return tiendaService.createStore(nombre,pais)
                .map(nuevaTienda -> ResponseEntity.status(HttpStatus.CREATED).body(nuevaTienda));
    }

    @Operation(summary = "Selecciona una tienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tienda encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @GetMapping("getOne")
    public Mono<ResponseEntity<TiendaDto>> buscarUnaTienda(
            @RequestParam String idTienda
    ) {
        return tiendaService.getStoreById(idTienda)
                .map(tiendaEncontrada -> ResponseEntity.status(HttpStatus.OK).body(tiendaEncontrada));
    }

    @Operation(summary = "Modifica datos de una Tienda existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tienda modificada"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @PutMapping("update")
    public Mono<ResponseEntity<TiendaDto>> modificarTienda(
            @RequestParam String idTienda,
            @RequestParam (required = false, defaultValue = "Mi Floristeria") String nombre,
            @RequestParam PaisesSucursales pais
    ) {
        return tiendaService.updateStore(idTienda, nombre,pais)
                .map(tiendaModificada -> ResponseEntity.status(HttpStatus.OK).body(tiendaModificada));
    }

    @Operation(summary = "Elimina una tienda de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tienda eliminada"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @DeleteMapping("delete")
    public Mono<ResponseEntity<Void>> eliminarTienda(
            @RequestParam String idTienda
    ) {
        return tiendaService.deleteStore(idTienda)
                .map(tiendaEliminada -> ResponseEntity.status(HttpStatus.OK).body(tiendaEliminada));
    }

    @Operation(summary = "Ver todas las tiendas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación realizada con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno, por favor revisar consola")
    })
    @GetMapping("/getAll")
    public ResponseEntity<Flux<TiendaDto>> getAllStores() {
        Flux<TiendaDto> allStores = tiendaService.getAllStores();
        return ResponseEntity.ok(allStores);
    }


}
