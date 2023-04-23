package laboratorio.TarefaService.tarefa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public ArrayList<Tarefa> getTarefas(){
        return tarefaRepository.selectTarefas();
    }

    public int getLastId(){
        return tarefaRepository.selectLastId();
    }

    public Tarefa getTarefaById(int id){
        return tarefaRepository.selectTarefaById(id);
    }

    public void createTarefa(String url, Timestamp hora_ini){
        tarefaRepository.insertTarefa(url, hora_ini);

    }
}