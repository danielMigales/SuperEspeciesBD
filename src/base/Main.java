package base;

import java.util.Scanner;

/**
 * @Daniel Migales
 */

public class Main {

    public static void main(String[] args) {

        Conexion conexion = null;
        Scanner teclado = new Scanner(System.in);
        boolean salir = true;

        try {

            conexion = new Conexion();
            do {
                System.out.println("\n----------------MENU PRINCIPAL-------------\n");
                System.out.println("1. Crear tabla de superespecie.");
                System.out.println("2. Añadir un superguerrero a la superespecie.");
                System.out.println("3. Asignar poder a un superguerrero.");
                System.out.println("4. Consultar  los datos de un superguerrero.");
                System.out.println("5. Resetear los poderes de un  superguerrero.");
                System.out.println("6. Borrar superguerrero.");
                System.out.println("7. Borrar una superespecie.");
                System.out.println("8. Salir del programa.");

                System.out.println("\n" + "Elija una opcion.");
                int opcion = teclado.nextInt();

                switch (opcion) {

                    case 1:
                        conexion.crearTabla();
                        break;
                    case 2:
                        conexion.añadirSuperGuerrero();
                        break;
                    case 3:
                        conexion.asignarPoder();
                        break;
                    case 4:
                        conexion.consulta();
                        break;
                    case 5:
                        conexion.resetearGuerrero();
                        break;
                    case 6:
                        conexion.borrarSuperguerrero();
                        break;
                    case 7:
                        System.out.println("¿Que superespecie desea eliminar?");
                        String superespecie = teclado.next();
                        conexion.eliminarTabla(superespecie);
                        break;
                    case 8:
                        System.out.println("Adios.");
                        salir = false;
                        break;
                }
            } while (salir);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conexion != null) {
                conexion.desconexion();
            }
        }
    }
}
