package laboratorio.TarefaService.tarefa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

    @Query(value = "select * from tarefa", nativeQuery = true)
    ArrayList<Tarefa> selectTarefas();

    @Query(value = "select max(t.id) from tarefa t", nativeQuery = true)
    int selectLastId();

    @Query(value = "select * from tarefa t where t.id = :id", nativeQuery = true)
    Tarefa selectTarefaById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "insert into tarefa (url, estado, idioma, hora_ini) values (:url, 'Em processamento', 'Desconhecido', :hora_ini)", nativeQuery = true)
    void insertTarefa(@Param("url") String url, @Param("hora_ini") Timestamp hora_ini);

}