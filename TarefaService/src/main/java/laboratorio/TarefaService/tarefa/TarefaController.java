package laboratorio.TarefaService.tarefa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = {"/home", "/"})
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("identificar.html");
        return modelAndView;
    }

    @PostMapping("/identificar")
    public ModelAndView identificar(Model model, @RequestParam String url) throws IOException {
        if (url.length() > 255)
            model.addAttribute("erro", "URL ultrapassa o limite de caracteres");
        else {
            try {
                URL u = new URL(url);
                tarefaService.createTarefa(String.valueOf(u), Timestamp.valueOf(LocalDateTime.now()));
                Tarefa tarefa = tarefaService.getTarefaById(tarefaService.getLastId());
                model.addAttribute("id", "Tarefa Id: " + tarefa.getId() /* tarefaService.getLastId() */);
                model.addAttribute("sucesso", "Tarefa criada com sucesso!");
                Process proc = Runtime.getRuntime().exec("java -Dtaskid=" + tarefa.getId() + " -jar TikaService-0.0.1-SNAPSHOT.jar");
                BufferedReader stdOut = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                new Thread() {
                    @Override
                    public void run() {
                        String line = "";
                        try {
                            while ((line = stdOut.readLine()) != null) {
                                System.out.println(line);
                            }
                        } catch (Exception e) {
                            throw new Error(e);
                        }
                    }
                }.start();
            } catch (MalformedURLException e) {
                model.addAttribute("erro", "URL inválido por favor insira novamente!");
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("identificar.html");
        return modelAndView;
    }

    @GetMapping("/estado")
    public ModelAndView estado() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("estado.html");
        return modelAndView;
    }

    @PostMapping("/estadoId")
    public ModelAndView estadoId(Model model, @RequestParam String id) {
        try {
            int n = Integer.parseInt(id);
            Tarefa tarefa = tarefaService.getTarefaById(n);
            if (tarefa == null)
                model.addAttribute("erro", "Id " + " não associado a nenhuma tarefa.");
            else {
                model.addAttribute("url", "URL documento: " + tarefa.getUrl());
                model.addAttribute("estado", "Estado: " + tarefa.getEstado());
                model.addAttribute("lingua", "Idioma: " + tarefa.getIdioma());
            }
        } catch (NumberFormatException e) {
            model.addAttribute("invalido", "Id inserido não é um número inteiro.");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("estado.html");
        return modelAndView;
    }
}