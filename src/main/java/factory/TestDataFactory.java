package factory;

import datasource.JsonDataSource;
import datasource.TestDataSource;

public class TestDataFactory {

    public static TestDataSource json(){

        return new JsonDataSource();

    }

    /*public static TestDataSource excel(){

        return new ExcelDataSource();

    }
*/
   /* public static TestDataSource database(){

        return new DatabaseDataSource();

    }*/
/*
    public static TestDataSource api(){

        return new ApiDataSource();

    }*/

}