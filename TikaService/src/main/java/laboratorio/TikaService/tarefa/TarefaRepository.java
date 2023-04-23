package laboratorio.TikaService.tarefa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

    @Query(value = "select * from tarefa t where t.id = :id", nativeQuery = true)
    Tarefa selectTarefaById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "update tarefa t set t.estado = :estado, t.idioma = :idioma, t.hora_fim = :hora_fim where t.id = :id", nativeQuery = true)
    void updateTarefa(@Param("id") int id, @Param("idioma") String idioma, @Param("estado") String estado,
                      @Param("hora_fim") Timestamp hora_fim);
}