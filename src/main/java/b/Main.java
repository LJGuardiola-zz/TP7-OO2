package b;

import b.model.DBFacade;
import b.model.JDBCFacade;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        DBFacade facade = new JDBCFacade();

        facade.open();

        List<String[]> resultArray = facade.queryResultAsArray(
                "SELECT * FROM PERSONS"
        );

        List<Map<String, String>> resultMap = facade.queryResultAsAssociation(
                "SELECT * FROM PHONES"
        );

        facade.close();

        resultArray.forEach(strings -> {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        });

        resultMap.forEach(System.out::println);

    }
}
