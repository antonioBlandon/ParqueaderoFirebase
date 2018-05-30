package co.com.ceiba.parqueaderofirebase;

import org.junit.Before;
import org.junit.Test;

import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerVehiculo;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestDataBaseManagerVehiculo {

    @Test
    public void testWrite(){
        DataBaseManagerVehiculo dataBaseManagerVehiculo = mock(DataBaseManagerVehiculo.class);
        Vehiculo vehiculo = mock(Vehiculo.class);
        doNothing().when(dataBaseManagerVehiculo).write(isA(String.class), isA(Object.class));
        dataBaseManagerVehiculo.write("", vehiculo);
        verify(dataBaseManagerVehiculo, times(1)).write("", vehiculo);
    }
}
