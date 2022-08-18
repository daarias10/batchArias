package ec.edu.espe.batcharias;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class BatchariasApplication {

  @Autowired JobLauncher jobLauncher;

  @Autowired
  @Qualifier("estudiantesJob")
  Job job1;

  public static void main(String[] args) {
    SpringApplication.run(BatchariasApplication.class, args);
  }

  @Scheduled(fixedDelay = 10000000, initialDelay = 10000)
  // @Scheduled(cron = "0 */1 * * * ?")
  public void perform() throws Exception {
    log.info("------------------------ INICIALIZACION DEL JOB -----------------------");
    JobParameters params =
        new JobParametersBuilder()
            .addString("estudiantesJob", String.valueOf(System.currentTimeMillis()))
            .toJobParameters();
    jobLauncher.run(job1, params);
  }
}
