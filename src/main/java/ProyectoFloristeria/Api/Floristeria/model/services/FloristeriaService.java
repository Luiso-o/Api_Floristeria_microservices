package ProyectoFloristeria.Api.Floristeria.model.services;

import ProyectoFloristeria.Api.Floristeria.model.Dto.FloristeriaDto;
import ProyectoFloristeria.Api.Floristeria.model.entity.FloristeriaEntity;
import ProyectoFloristeria.Api.Floristeria.model.repositories.FloristeriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Luis
 */
@Service
public class FloristeriaService {

    @Autowired
    private FloristeriaRepository floristeriaRepository;

    public FloristeriaDto crearNuevaFloristeria(String nombre, String pais) {
        return castearFloristeriaADto(floristeriaRepository.save(FloristeriaEntity.builder()
                .fechaApertura(LocalDate.now())
                .nombre(nombre)
                .pais(pais)
                .build()));
    }

    public FloristeriaDto actualizarFloristeria(long id, String nombre, String pais) {
        FloristeriaEntity tiendaAntigua = encuentraFloristeria(id);
        tiendaAntigua.setNombre(nombre != null && !nombre.isBlank() ? nombre : tiendaAntigua.getNombre());
        tiendaAntigua.setPais(pais);
        return castearFloristeriaADto(floristeriaRepository.save(tiendaAntigua));
    }

    public void eliminarFloristeriaDeLaBaseDeDatos(long id) {
        Optional<FloristeriaEntity> floristeria = floristeriaRepository.findById(id);
        if (floristeria.isPresent()) {
            floristeriaRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Floristería no encontrada con ID: " + id);
        }
    }

    public FloristeriaDto encuentraFloristeriaPorSuId(long id) {
     FloristeriaEntity floristeria = encuentraFloristeria(id);
        return castearFloristeriaADto(floristeria);
    }

    public List<FloristeriaDto> encuentraTodasLasFloristeriasDeLaBaseDeDatos() {
        List<FloristeriaEntity> misTiendas = floristeriaRepository.findAll();
        return misTiendas.stream().map(this::castearFloristeriaADto).collect(Collectors.toList());
    }


    public FloristeriaEntity encuentraFloristeria(long id) {
        return floristeriaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("No existe floristeria con el id " + id));
    }

    /**
     * Metodos privados de la clase FloristeriaService
     */
    private FloristeriaDto castearFloristeriaADto(FloristeriaEntity nueva) {
        return FloristeriaDto.builder()
                .idFloristeria(nueva.getIdFloristeria())
                .nombre(nueva.getNombre())
                .pais(nueva.getPais())
                .build();
    }


}
