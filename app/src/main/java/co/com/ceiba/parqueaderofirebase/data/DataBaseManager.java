package co.com.ceiba.parqueaderofirebase.data;

import android.app.Activity;

public interface DataBaseManager {

    void read(String nodeRerence);

    void write(String nodeReference, Object object);

}
