package co.com.ceiba.parqueaderofirebase.data.entities;

import android.app.Activity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.com.ceiba.parqueaderofirebase.data.DataBaseConstants;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManager;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerParqueadero;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerVehiculo;

public class DataBaseManagerImpl implements DataBaseManager{

    static DataBaseManagerImpl instance;

    public static DataBaseManagerImpl getInstance() {
        if (instance == null) {
            instance = new DataBaseManagerImpl();
        }
        return instance;
    }

    public void agregarVehiculo(boolean isCar, Vehiculo vehiculo, Parqueadero parqueadero){
        DataBaseManagerVehiculo
                .getInstance()
                .write(DataBaseConstants.REFERENCE_VEHICLE + vehiculo.getPlaca(), vehiculo);
        if (isCar){
            DataBaseManagerParqueadero.getInstance().write(DataBaseConstants.REFERENCE_PARKING+DataBaseConstants.REFERENCE_COUNT_CAR, parqueadero.getCantidadCarros() + 1);
        }else{
            DataBaseManagerParqueadero.getInstance().write(DataBaseConstants.REFERENCE_PARKING+DataBaseConstants.REFERENCE_COUNT_MOTO, parqueadero.getCantidadMotos() + 1);
        }
    }

    public void eliminarVehiculo(boolean isCar, Vehiculo vehiculo, Parqueadero parqueadero){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(DataBaseConstants.REFERENCE_VEHICLE);
        reference.child(vehiculo.getPlaca()).removeValue();
        if (isCar){
            DataBaseManagerParqueadero.getInstance().write(DataBaseConstants.REFERENCE_PARKING+DataBaseConstants.REFERENCE_COUNT_CAR, parqueadero.getCantidadCarros() - 1);
        }else{
            DataBaseManagerParqueadero.getInstance().write(DataBaseConstants.REFERENCE_PARKING+DataBaseConstants.REFERENCE_COUNT_MOTO, parqueadero.getCantidadMotos() - 1);
        }
        DataBaseManagerVehiculo.getInstance().write(DataBaseConstants.REFERENCE_REGISTER + vehiculo.getPlaca() + vehiculo.getFechaIngreso(), vehiculo);
    }

    @Override
    public void read(String nodeRerence, Activity activity) {

    }

    @Override
    public void write(String nodeReference, Object object) {

    }
}
