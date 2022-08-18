package ec.edu.espe.batcharias.utils;

import ec.edu.espe.batcharias.model.Estudiante;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

  public static void crearArchivo(ArrayList lista) {
    FileWriter flwriter = null;
    try {
      flwriter = new FileWriter("estudiantes.txt");
      BufferedWriter bfwriter = new BufferedWriter(flwriter);
      bfwriter.close();
      log.info("Archivo creado satisfactoriamente..");

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (flwriter != null) {
        try {
          flwriter.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static ArrayList leerArchivo(String ruta) {
    File file = new File("estudiantes.txt");
    ArrayList<Estudiante> listaEstudiantes = new ArrayList<>();
    Scanner scanner;
    try {
      scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String linea = scanner.nextLine();
        Scanner delimitar = new Scanner(linea);
        delimitar.useDelimiter("\\s*,\\s*");

        Estudiante e =
            Estudiante.builder()
                .codigo(delimitar.next())
                .codigoInterno(delimitar.next())
                .cedula(delimitar.next())
                .apellidos(delimitar.next())
                .nombres(delimitar.next())
                .nivel(Integer.parseInt(delimitar.next()))
                .build();
        listaEstudiantes.add(e);
        log.info("Datos del archivo {} \n", e.toString());
        delimitar.close();
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return listaEstudiantes;
  }

  public static void aniadirArchivo(List<Estudiante> lista) {
    FileWriter flwriter = null;
    try {
      flwriter = new FileWriter("data.txt", true);
      BufferedWriter bfwriter = new BufferedWriter(flwriter);
      for (Estudiante c : lista) {
        bfwriter.write(
            c.getCodigo()
                + ","
                + c.getCodigoInterno()
                + ","
                + c.getCedula()
                + ","
                + c.getApellidos()
                + ","
                + c.getNombres()
                + ","
                + c.getNivel()
                + "\n");
      }
      bfwriter.close();
      log.info("Archivo modificado satisfactoriamente..");

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (flwriter != null) {
        try {
          flwriter.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
