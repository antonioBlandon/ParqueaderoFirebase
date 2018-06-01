package co.com.ceiba.parqueaderofirebase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.parqueaderofirebase.cobrar.ActivityCobrar;
import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

import static co.com.ceiba.parqueaderofirebase.ParqueaderoBuilder.aParking;
import static co.com.ceiba.parqueaderofirebase.VehiculoBuilder.aVehicle;
import static org.mockito.Matchers.anyString;

public class TestActivityCobrar {

    private ActivityCobrar activityCobrar;

    @Before
    public void prepareData(){
        activityCobrar = new ActivityCobrar();
    }

    @Test
    public void testGetVehiculo(){
        //Arrange
        List<Vehiculo> listVehiculo = new ArrayList<>();
        listVehiculo.add(aVehicle().build());
        //Act
        Vehiculo vehiculo = activityCobrar.getVehiculo(listVehiculo, anyString());
        //Assert
        Assert.assertNotNull(vehiculo);
    }

    @Test
    public void testCobrar(){
        //Arrange
        Vehiculo vehiculo = aVehicle().build();
        Parqueadero parqueadero = aParking().build();
        //Act
        long valorCobrado = activityCobrar.cobrar(vehiculo, parqueadero);
        //Assert
        Assert.assertEquals(11000,valorCobrado);
    }

}
