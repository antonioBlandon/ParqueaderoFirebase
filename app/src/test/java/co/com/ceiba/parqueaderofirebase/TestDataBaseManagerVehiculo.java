package co.com.ceiba.parqueaderofirebase;

import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerVehiculo;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestDataBaseManagerVehiculo {

    @Test
    public void testWrite(){
        DataBaseManagerVehiculo dataBaseManagerVehiculo = mock(DataBaseManagerVehiculo.class);
        Vehiculo vehiculo = mock(Vehiculo.class);
        doNothing().when(dataBaseManagerVehiculo).write(isA(String.class), isA(Object.class));
        dataBaseManagerVehiculo.write("", vehiculo);
        verify(dataBaseManagerVehiculo, times(1)).write("", vehiculo);
    }

    @Test
    public void testWrite1(){
        DataBaseManagerVehiculo dataBaseManagerVehiculo = mock(DataBaseManagerVehiculo.class);
        Vehiculo vehiculo = mock(Vehiculo.class);
        dataBaseManagerVehiculo.write("", vehiculo);
        verify(dataBaseManagerVehiculo, times(1)).write("", vehiculo);
    }

    @Test
    public void testRead(){
        DataBaseManagerVehiculo dataBaseManagerVehiculo = mock(DataBaseManagerVehiculo.class);
        Vehiculo vehiculo = mock(Vehiculo.class);
        doNothing().when(dataBaseManagerVehiculo).read(isA(String.class));
        dataBaseManagerVehiculo.read("");
        verify(dataBaseManagerVehiculo, times(1)).read("");
    }

}
