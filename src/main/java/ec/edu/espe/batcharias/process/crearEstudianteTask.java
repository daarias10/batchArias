package ec.edu.espe.batcharias.process;

import ec.edu.espe.batcharias.model.Estudiante;
import ec.edu.espe.batcharias.utils.FileUtils;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class crearEstudianteTask implements Tasklet, StepExecutionListener {
  private List<Estudiante> estudiantes;
  private RestTemplate restTemplate = new RestTemplate();
  private String url = "http://localhost:8080/estudiantes/crear";

  @Override
  public void beforeStep(StepExecution stepExecution) {
    estudiantes = FileUtils.leerArchivo("c:/temp/estudiantes.txt");
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    ResponseEntity<Estudiante> response =
        restTemplate.postForEntity(url, estudiantes, Estudiante[].class);
    Estudiante[] auxEstudiantes = response.getBody();
    estudiantes = Arrays.asList(auxEstudiantes);
    return RepeatStatus.FINISHED;
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    log.info("Estudiantes creados: {}", estudiantes.size());
    stepExecution.getJobExecution().getExecutionContext().put("estudiantes", this.estudiantes);
    return ExitStatus.COMPLETED;
  }
}
