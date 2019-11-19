package base;

/**
 * @Daniel Migales
 */

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Conexion {

    private static Connection conn;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/superespecies";
    Scanner tecladoStrings = new Scanner(System.in);
    Scanner tecladoNumeros = new Scanner(System.in);

    public Conexion() {
        conn = null;
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Conexion a la base de datos correcta.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar a la base de datos correcta." + e);
        }
    }

    public void desconexion() {
        conn = null;
    }

    public void crearTabla() throws SQLException {

        String nombreTabla;
        boolean salir = false;

        do {
            System.out.println("¿Desea añadir alguna tabla de superespecie. SI/NO");
            String decision = tecladoStrings.next().toUpperCase();
            switch (decision) {
                case "SI":
                    System.out.println("Introduzca el nombre de la tabla a crear.");
                    nombreTabla = tecladoStrings.next();
                    Statement st = null;
                    eliminarTabla(nombreTabla);

                    try {
                        st = conn.createStatement();
                        String sql
                                = "CREATE TABLE " + nombreTabla + " ("
                                + " id SERIAL PRIMARY KEY, "
                                + " nombre TEXT,"
                                + " descripcion TEXT, "
                                + " poder INT ) ";

                        st.executeUpdate(sql);
                        System.out.println("Creada tabla SuperEspecie.");
                    } finally {
                        if (st != null) {
                            st.close();
                        }
                    }
                    break;
                case "NO":
                    salir = true;
            }
        } while (!salir);
    }

    public void eliminarTabla(String nombreTabla) throws SQLException {

        try (Statement st = conn.createStatement()) {

            String sql = "DROP TABLE IF EXISTS " + nombreTabla + " ";
            st.executeUpdate(sql);
            System.out.println("Eliminada tabla " + nombreTabla);
        }
    }

    public void añadirSuperGuerrero() throws SQLException {

        System.out.println("Introduzca el nombre de la tabla en la cual desea insertar valores.");
        String nombreTabla = tecladoStrings.next();
        System.out.println("Introduzca el nombre del personaje.");
        String nombre = tecladoStrings.next();
        System.out.println("Introduzca la descripcion.");
        String descripcion = tecladoStrings.next();
        System.out.println("Introduzca el poder del personaje.");
        int poder = tecladoNumeros.nextInt();

        String sql = "INSERT INTO " + nombreTabla + " (nombre, descripcion, poder) values ('"
                + nombre
                + "', '"
                + descripcion
                + "', '"
                + poder
                + "')";

        Statement st = null;
        try {
            st = conn.createStatement();
            int num = st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println("Numero de superguerreros añadidos: " + num);
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            System.out.println("Añadida Superespecie " + 1);
            rs.close();
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void consulta() throws SQLException {

        System.out.println("Introduzca el nombre de la tabla a consultar.");
        String nombreTabla = tecladoStrings.next();

        String sql = "SELECT * FROM " + nombreTabla;
        Statement st = null;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int nSuperGuerreros = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                int poder = rs.getInt("poder");
                System.out.println("ID: " + id + " Nombre: " + nombre
                        + " Descripcion: " + descripcion + " Poder: " + poder);
                nSuperGuerreros++;
            }
            if (nSuperGuerreros == 0) {
                System.out.println("No hay superguerreros");
            }
            rs.close();
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void asignarPoder() throws SQLException {

        System.out.println("Introduzca el nombre de la tabla.");
        String nombreTabla = tecladoStrings.next();
        System.out.println("Introduzca el ID del superguerrero.");
        int id = tecladoNumeros.nextInt();
        System.out.println("Introduzca el nivel de poder.");
        int poder = tecladoNumeros.nextInt();

        Statement st = null;
        String sql = "UPDATE " + nombreTabla + " SET poder = " + poder + " WHERE id = " + id + ";";
        try {
            st = conn.createStatement();
            st.executeUpdate(sql);
            System.out.println("Asignado nuevo poder al superguerrero");

        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void resetearGuerrero() throws SQLException {

        System.out.println("Introduzca el nombre de la tabla.");
        String nombreTabla = tecladoStrings.next();
        System.out.println("Introduzca el ID del superguerrero.");
        int id = tecladoNumeros.nextInt();

        Statement st = null;
        String sql = "UPDATE " + nombreTabla + " SET poder = 0 WHERE id = " + id + ";";
        try {
            st = conn.createStatement();
            st.executeUpdate(sql);
            System.out.println("El poder del superguerrero ha sido reiniciado.");

        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public void borrarSuperguerrero() throws SQLException {

        System.out.println("Introduzca el nombre de la tabla.");
        String nombreTabla = tecladoStrings.next();
        System.out.println("Introduzca el ID del superguerrero.");
        String id = tecladoNumeros.nextLine();

        Statement st = null;
        String sql = "DELETE FROM " + nombreTabla + " WHERE id = " + id + ";";
        try {
            st = conn.createStatement();
            st.executeUpdate(sql);
            System.out.println("El superguerrero ha sido eliminado.");

        } finally {
            if (st != null) {
                st.close();
            }
        }
    }
}
