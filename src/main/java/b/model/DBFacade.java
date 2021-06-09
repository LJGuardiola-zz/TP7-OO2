package b.model;

import java.util.List;
import java.util.Map;

public interface DBFacade {

    /**
     * Abre una conexión a una base de datos
     */
    void open();

    /**
     * Ejecuta una sentencia SQL.
     *
     * @return una Lista de filas, donde cada fila es un Mapa con nombreColumna => valorColumna. O una lista vacía.
     * @throws RuntimeException si la conexión no esta abierta o existe un error en la sentencia sql
     */
    List<Map<String, String>> queryResultAsAssociation(String sql);

    /**
     * Ejecuta una sentencia SQL.
     *
     * @return una Lista de filas, donde cada fila es una arreglo. O una lista vacía.
     * @throws RuntimeException si la conexión no esta abierta o existe un error en la sentencia sql.
     */
    List<String[]> queryResultAsArray(String sql);

    /**
     * Cierra la conexión a la base de datos.
     */
    void close();

}
