package arquetipo.arquetipo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import arquetipo.arquetipo.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long>{
    
}
