package ProyectoFloristeria.Api.Floristeria.controllers;

import ProyectoFloristeria.Api.Floristeria.model.Dto.FloristeriaDto;
import ProyectoFloristeria.Api.Floristeria.model.services.FloristeriaService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiResponse(responseCode = "200", description = "Floristeria actualizada con éxito")
    @ApiResponse(responseCode = "500", description = "No existe una floristeria con ese Id")
    @PutMapping(value = "update")
    public ResponseEntity<FloristeriaDto>actualizarFloristeria(
        @RequestParam long id,
        @RequestParam String nombre,
        @RequestParam(required = false, defaultValue = "No mencionado") String pais
    ){
        FloristeriaDto floristeriaActualizada = floristeriaService.actualizarFloristeria(id,nombre,pais);
        return  ResponseEntity.status(HttpStatus.OK).body(floristeriaActualizada);
    }




}
