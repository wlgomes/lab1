package laboratorio.TikaService.tarefa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public Tarefa getTarefaById(int id){
        return tarefaRepository.selectTarefaById(id);
    }

    public void updateEstadoTarefa(int id, String idioma, String estado, Timestamp hora_fim) {
        tarefaRepository.updateTarefa(id, idioma,estado,hora_fim);
    }

}