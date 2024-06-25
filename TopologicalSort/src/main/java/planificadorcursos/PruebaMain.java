/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package planificadorcursos;

import graph.Vertex;
import java.util.Scanner;
import uni.aed.linkedlistTDA.*;

/**
 *
 * @author camila
 */
public class PruebaMain {
    private static Scanner scanner = new Scanner(System.in);
    private static PlanificadorCursos planificador = new PlanificadorCursos();

    public static void main(String[] args) {
        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (opcion) {
                case 1:
                    agregarCurso();
                    break;
                case 2:
                    marcarCursoComoCompletado();
                    break;
                case 3:
                    obtenerOrdenTopologico();
                    break;
                case 4:
                    obtenerCursosDisponibles();
                    break;
                case 5:
                    obtenerCursosSinPrerrequisitos();
                    break;
                case 6:
                    obtenerCursosAbiertos();
                    break;
                case 7:
                    agregarCursosAbiertos();
                    break;
                case 8:
                    mostrar();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("Planificador de Cursos Universitarios");
        System.out.println("1. Agregar Curso");
        System.out.println("2. Marcar Curso como Completado");
        System.out.println("3. Obtener Orden Topológico");
        System.out.println("4. Obtener Cursos Disponibles");
        System.out.println("5. Obtener Cursos sin Prerrequisitos");
        System.out.println("6. Obtener Cursos que Abre un Curso");
        System.out.println("7. Agregar Cursos Abiertos a un Curso Existente");
        System.out.println("8. Mostrar");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void agregarCurso() {
        System.out.print("Ingrese el ID del curso: ");
        String cursoId = scanner.nextLine();
        System.out.print("Ingrese los IDs de los cursos que abre (separados por comas): ");
        LinkedListTDA<String> cursosQueAbreList = leerDesdeInput();

        Curso curso = new Curso(cursoId);
        planificador.agregarCurso(curso, cursosQueAbreList);
        System.out.println("Curso agregado exitosamente.");
    }

    private static void marcarCursoComoCompletado() {
        System.out.print("Ingrese el ID del curso a marcar como completado: ");
        String cursoId = scanner.nextLine();
        planificador.cursoCompletado(cursoId);
        System.out.println("Curso marcado como completado.");
    }

    private static void obtenerOrdenTopologico() {
        try {
            LinkedListTDA<String> ordenTopologico = planificador.obtenerOrdenTopologico();
            System.out.println("Orden Topológico: " + ordenTopologico);
        } catch (Exception e) {
            System.out.println("Error al obtener el orden topológico: " + e.getMessage());
        }
    }

    private static void obtenerCursosDisponibles() {
        System.out.print("Ingrese los IDs de los cursos completados (separados por comas): ");
        LinkedListTDA<String> cursosCompletadosList = leerDesdeInput();
        try {
            LinkedListTDA<String> cursosDisponibles = planificador.obtenerCursosDisponibles(cursosCompletadosList);
            System.out.println("Cursos Disponibles: " + cursosDisponibles);
        } catch (Exception e) {
            System.out.println("Error al obtener los cursos disponibles: " + e.getMessage());
        }
    }

    private static void obtenerCursosSinPrerrequisitos() {
        LinkedListTDA<String> cursosSinPrerrequisitos = planificador.obtenerCursosSinPrerrequisitos();
        System.out.println("Cursos sin Prerrequisitos: " + cursosSinPrerrequisitos);
    }

    private static void obtenerCursosAbiertos() {
        System.out.print("Ingrese el ID del curso: ");
        String cursoId = scanner.nextLine();
        LinkedListTDA<String> cursosAbiertos = planificador.obtenerCursosAbiertos(cursoId);
        if (cursosAbiertos.isEmpty()) {
        System.out.println("El curso " + cursoId + " no abre ningún curso.");
        } else {
        System.out.println("Cursos que abre el curso " + cursoId + ": " + cursosAbiertos);
        }
    }
    
    private static void agregarCursosAbiertos() {
        System.out.print("Ingrese el ID del curso existente: ");
        String cursoId = scanner.nextLine();
        Vertex<String> vertex =  planificador.getMallaCurricular().getVertex(cursoId);

        if (vertex == null) {
        System.out.println("Curso no encontrado.");
        return;
    }

        Curso curso = new Curso(vertex.getId());

        System.out.print("Ingrese los IDs de los cursos que abre (separados por comas): ");
        LinkedListTDA<String> cursosQueAbreList = leerDesdeInput();

        planificador.agregarCursosAbiertos(curso, cursosQueAbreList);
        System.out.println("Cursos agregados exitosamente.");
    }
    
    public static LinkedListTDA<String> leerDesdeInput() {
        LinkedListTDA<String> entrada = new LinkedListTDA<>();
        String input = scanner.nextLine().trim();  // Lee la entrada del usuario y elimina espacios en blanco al inicio y al final

        // Leer cada entrada separado por comas y agregarlo a la lista
        StringBuilder entradaActual = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == ',') {
                // Agregar la entrada actual a la lista
                entrada.add(entradaActual.toString().trim());
                // Limpiar para la próxima entrada
                entradaActual.setLength(0);
            } else {
                // Construir el nombre de la entrada actual
                entradaActual.append(c);
            }
        }

        // Agregar la ultima entrada después del último separador ","
        if (entradaActual.length() > 0) {
            entrada.add(entradaActual.toString().trim());
        }

        return entrada;
    }
    private static void mostrar() {
        System.out.println(planificador.toString());
    }
}
