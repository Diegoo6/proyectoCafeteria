package producto_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import producto_service.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNombre(String nombre);

}