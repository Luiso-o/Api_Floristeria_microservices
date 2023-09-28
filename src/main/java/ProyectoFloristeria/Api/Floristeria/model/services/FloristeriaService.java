package ProyectoFloristeria.Api.Floristeria.model.services;

import ProyectoFloristeria.Api.Floristeria.model.Dto.FloristeriaDto;
import ProyectoFloristeria.Api.Floristeria.model.entity.FloristeriaEntity;
import ProyectoFloristeria.Api.Floristeria.model.repositories.FloristeriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
        tiendaAntigua.setNombre(nombre);
        tiendaAntigua.setPais(pais);
        return castearFloristeriaADto(floristeriaRepository.save(tiendaAntigua));
    }

    private FloristeriaEntity encuentraFloristeria(long id) {
        return floristeriaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No existe floristeria con el id " + id));
    }

    private FloristeriaDto castearFloristeriaADto(FloristeriaEntity nueva) {
        return FloristeriaDto.builder()
                .idFloristeria(nueva.getIdFloristeria())
                .nombre(nueva.getNombre())
                .pais(nueva.getPais())
                .build();
    }

}
