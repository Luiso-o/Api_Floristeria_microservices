package ProyectoFloristeria.Api.Floristeria.model.repositories;

import ProyectoFloristeria.Api.Floristeria.model.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity,Long> {
}
