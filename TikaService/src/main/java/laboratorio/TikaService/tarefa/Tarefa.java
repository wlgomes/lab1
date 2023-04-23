package laboratorio.TikaService.tarefa;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
//@EnableAutoConfiguration
@Table(name = "Tarefa")
public @Data class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    private String estado;
    private String idioma;
    private java.sql.Timestamp hora_ini;
    private java.sql.Timestamp hora_fim;


    public Tarefa() {
    }

    public Tarefa(Integer id, String url, String estado, String idioma, Timestamp hora_ini, Timestamp hora_fim) {
        this.id = id;
        this.url = url;
        this.estado = estado;
        this.idioma = idioma;
        this.hora_ini = hora_ini;
        this.hora_fim = hora_fim;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    public Timestamp getHora_ini() {
        return hora_ini;
    }

    public void setHora_ini(Timestamp hora_ini) {
        this.hora_ini = hora_ini;
    }

    public Timestamp getHora_fim() {
        return hora_fim;
    }

    public void setHora_fim(Timestamp hora_fim) {
        this.hora_fim = hora_fim;
    }

}