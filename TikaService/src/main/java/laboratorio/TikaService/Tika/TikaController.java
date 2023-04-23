package laboratorio.TikaService.Tika;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/tika")
public class TikaController {

    @Autowired
    private TikaService tikaService;

    @Autowired
    private RestTemplate restTemplate;

    /* "https://gutenberg.org/files/5200/5200-0.txt"
    @GetMapping("/{tarefaId}")
    public void identificarIdioma(@PathVariable("tarefaId") int id) throws Exception {

        CompletableFuture<String> thread = tikaService.TikaService(id);

        //CompletableFuture.allOf(thread).join();
        //System.out.println(thread.get());

    } */

}