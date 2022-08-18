package ec.edu.espe.batcharias.process;

import ec.edu.espe.batcharias.model.Estudiante;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class asignacionEstudianteTask implements Tasklet, StepExecutionListener {
  private List<Estudiante> estudiantes;
  private RestTemplate restTemplate = new RestTemplate();
  private String url = "http://localhost:8080/estudiantes/asignar";

  @Override
  public void beforeStep(StepExecution stepExecution) {
    ExecutionContext executionContext = stepExecution
          .getJobExecution()
          .getExecutionContext();
    this.estudiantes = (List<Estudiantes>) executionContext.get("estudiantes");
    log.info("Se va a asignar los estudiantes");
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
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    return ExitStatus.COMPLETED;
  }
}
