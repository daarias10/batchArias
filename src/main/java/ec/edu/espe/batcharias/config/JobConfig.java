package ec.edu.espe.batcharias.config;

import ec.edu.espe.batcharias.process.asignacionEstudianteTask;
import ec.edu.espe.batcharias.process.crearEstudianteTask;
import ec.edu.espe.batcharias.process.generarListadoTask;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
public class JobConfig {

  @Autowired private JobBuilderFactory jobs;
  @Autowired private StepBuilderFactory steps;
  @Autowired private MongoTemplate mongoTemplate;

  @Bean
  protected Step crearEstudianteTask() {
    return steps.get("crearEstudianteTask").tasklet(new crearEstudianteTask()).build();
  }

  @Bean
  protected Step asignacionEstudianteTask() {
    return steps.get("asignacionEstudianteTask").tasklet(new asignacionEstudianteTask()).build();
  }

  @Bean
  protected Step generarListadoTask() {
    return steps.get("generarListadoTask").tasklet(new generarListadoTask()).build();
  }

  @Bean
  public Job processTextFileJob() {
    return jobs.get("estudiantesJob")
        .start(crearEstudianteTask())
        .next(asignacionEstudianteTask())
        .next(generarListadoTask())
        .build();
  }
}
