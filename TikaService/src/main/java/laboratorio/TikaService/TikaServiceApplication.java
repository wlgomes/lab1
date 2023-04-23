package laboratorio.TikaService;

import laboratorio.TikaService.Tika.TikaService;
import laboratorio.TikaService.tarefa.TarefaRepository;
import laboratorio.TikaService.tarefa.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableEurekaClient
@EntityScan("laboratorio.TikaService")
public class TikaServiceApplication implements ApplicationRunner {

    @Value("${taskid}")
    private int id;

    @Autowired
    private TikaService tikaService;

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    @Qualifier("bean.porta")
    ServletWebServerApplicationContext socket;

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(TikaServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("A processar durante 1minuto...");
        tikaService.TikaService(id);
        String uribase = InetAddress.getLocalHost().getHostAddress();
        int porta = socket.getWebServer().getPort();
        String uri = "http://" + uribase + ":" + porta + "/actuator/shutdown";

        Thread.sleep(60000);

        if(tarefaService.getTarefaById(id).getEstado().startsWith("Em processamento"))
            tarefaService.updateEstadoTarefa(id, "","Cancelada(Identificador de idioma timeout)", Timestamp.valueOf(LocalDateTime.now()));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class);
        System.out.println("Codigo op Shutdown " + result.getStatusCode());
    }

}