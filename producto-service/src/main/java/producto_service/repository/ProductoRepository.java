package producto_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import producto_service.model.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByNombre(String nombre);


    boolean existsByCategoriaId(Long categoriaId);

    List<Producto> findByCategoriaId(Long categoriaId);
}