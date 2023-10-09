package ProyectoFloristeria.Api.Floristeria.controllers;

import ProyectoFloristeria.Api.Floristeria.model.Dto.FloristeriaDto;
import ProyectoFloristeria.Api.Floristeria.model.services.FloristeriaService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Luis
 */
@RestController
@RequestMapping("/api/v1/floristeria")
public class FloristeriaController {

      @Autowired
      private FloristeriaService floristeriaService;

    @Operation(summary = "Crea una nueva Floristería", description = "Introduce el nombre y el pais de la floristeria que deseas crear")
    @ApiResponse(responseCode = "201", description = "Floristeria creada con éxito")
    @PostMapping(value = "add")
    public ResponseEntity<FloristeriaDto>crearNuevaFloristeria(
            @RequestParam String nombre,
            @RequestParam(required = false, defaultValue = "No mencionado") String pais)
    {
         FloristeriaDto nueva = floristeriaService.crearNuevaFloristeria(nombre,pais);
         return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @Operation(summary = "Actualiza una Floristería de la BD", description = "Para actualizar una floristeria debes conocer su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Floristeria actualizada con éxito"),
            @ApiResponse(responseCode = "500", description = "Floristeria inexistente o base de datos vacía")
    })
    @PutMapping(value = "update")
    public ResponseEntity<FloristeriaDto>actualizarFloristeria(
        @RequestParam long id,
        @RequestParam @Nullable String nombre,
        @RequestParam(required = false, defaultValue = "No mencionado") String pais
    ){
        FloristeriaDto floristeriaActualizada = floristeriaService.actualizarFloristeria(id,nombre,pais);
        return  ResponseEntity.status(HttpStatus.OK).body(floristeriaActualizada);
    }


    @Operation(summary = "Eliminar una Floristería de la BD", description = "Para eliminar una floristeria debes conocer su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Floristeria eliminada con éxito"),
            @ApiResponse(responseCode = "500", description = "Floristeria inexistente o base de datos vacía")
    })
    @DeleteMapping(value = "delete")
    public ResponseEntity<Void> eliminarFloristeria(@RequestParam long id) {
        try {
            floristeriaService.eliminarFloristeriaDeLaBaseDeDatos(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Buscar Floristeria", description = "Para buscar una floristeria debes conocer su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Floristeria encontrada con éxito"),
            @ApiResponse(responseCode = "500", description = "Floristeria inexistente o base de datos vacía")
    })
    @GetMapping(value = "getOne")
    public ResponseEntity<FloristeriaDto> muestraFloristeriaBuscandolaPorSuId(@RequestParam long id){
           return ResponseEntity.status(HttpStatus.OK).body(floristeriaService.encuentraFloristeriaPorSuId(id));
    }

    @Operation(summary = "Ver floristerias", description = "Muestra todas las floristerias de la base de datos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Floristerias existentes "),
            @ApiResponse(responseCode = "500", description = "base de datos vacía")
    })
    @GetMapping(value = "getAll")
    public ResponseEntity<?> muestraListaDeFloristerias(){
        try {
            List<FloristeriaDto> floristerias = floristeriaService.encuentraTodasLasFloristeriasDeLaBaseDeDatos();
            if (floristerias.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(floristerias);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Ver valor del Stock", description = "Muestra el valor total del stock de la tienda")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud realizada con éxito "),
            @ApiResponse(responseCode = "404", description = "Stock vacío "),
            @ApiResponse(responseCode = "500", description = "Id no encontrado")
    })
    @GetMapping(value = "getAll/valorDelStock")
    public ResponseEntity<Map<String, Object>> calculaValorTotalDeLaFloristeria(@RequestParam long idTienda) {
        double valorTotal = floristeriaService.devuelveValorTotalStock(idTienda);
        Map<String, Object> response = new HashMap<>();
        response.put("valorTotal ", valorTotal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
