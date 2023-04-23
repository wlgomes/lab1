package laboratorio.TikaService.Tika;

import laboratorio.TikaService.tarefa.Tarefa;
import laboratorio.TikaService.tarefa.TarefaService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.apache.tika.exception.*;
import org.apache.tika.langdetect.optimaize.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@Service
public class TikaService {
    @Autowired
    private TarefaService tarefaService;

    @Async("asyncExecutor")
    public CompletableFuture<String> TikaService(int id) throws MalformedURLException {
        Tarefa tarefa = tarefaService.getTarefaById(id);
        URL url = new URL(tarefa.getUrl());
        Pattern fileType = Pattern.compile("(((?i)(.txt))$)");
        String idiomaNull = "";
        Timestamp horaFimCancelado = Timestamp.valueOf(LocalDateTime.now());
        if (!fileType.matcher(url.getPath()).find()) {
            String estado = "Cancelada(tipo de ficheiro nao suportado)";
            String idioma = " ";
            tarefaService.updateEstadoTarefa(id, idioma, estado, horaFimCancelado);
        } else {
            try {
                Tika tika = new Tika();
                String content = tika.parseToString(url);
                LanguageDetector detector = new OptimaizeLangDetector().loadModels();
                LanguageResult result = detector.detect(content);
                Locale.setDefault(new Locale("pt", "PT"));
                Locale idioma = new Locale(Locale.forLanguageTag(result.getLanguage()).getDisplayLanguage());
                String idiomaExt = StringUtils.capitalize(idioma.toString());
                String estado = "Concluida";
                Timestamp hora_fim = Timestamp.valueOf(LocalDateTime.now());
                tarefaService.updateEstadoTarefa(id, idiomaExt, estado, hora_fim);
            } catch (ZeroByteFileException e) {
                String estado = "Cancelado(Ficheiro vazio)";
                tarefaService.updateEstadoTarefa(id, idiomaNull, estado, horaFimCancelado);
            } catch (AccessPermissionException e) {
                String estado = "Cancelado(Acesso não permitido)";
                tarefaService.updateEstadoTarefa(id, idiomaNull, estado, horaFimCancelado);
            } catch (CorruptedFileException e) {
                String estado = "Cancelado(Ficheiro corrompido)";
                tarefaService.updateEstadoTarefa(id, idiomaNull, estado, horaFimCancelado);
            } catch (EncryptedDocumentException e) {
                String estado = "Cancelado(Ficheiro encriptado)";
                tarefaService.updateEstadoTarefa(id, idiomaNull, estado, horaFimCancelado);
            } catch (TikaException | IOException e) {
                String estado = "Cancelado(URL invalido.)";
                tarefaService.updateEstadoTarefa(id, idiomaNull, estado, horaFimCancelado);
            }
        }
        String estadoThread = "Concluido";
        return CompletableFuture.completedFuture(estadoThread);
    }
}