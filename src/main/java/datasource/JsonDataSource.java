package datasource;

import utils.JsonReader;

public class JsonDataSource implements TestDataSource{

    private final JsonReader jsonReader;

    public JsonDataSource() {

        jsonReader = new JsonReader();
    }

    @Override
    public <T> T read(String file,Class<T> clazz){

        return jsonReader.read(file, clazz);

    }

}