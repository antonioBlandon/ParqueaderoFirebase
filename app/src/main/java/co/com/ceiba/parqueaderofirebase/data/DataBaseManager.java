package co.com.ceiba.parqueaderofirebase.data;

public interface DataBaseManager {

    void read(String nodeRerence);

    void write(String nodeReference, Object object);

}
