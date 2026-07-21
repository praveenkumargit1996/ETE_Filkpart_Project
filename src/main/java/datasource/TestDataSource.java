package datasource;

public interface TestDataSource {

    // Look! No "public" keyword needed here. Java automatically treats it as public.
   <T> T read(String source, Class<T> clazz);

}