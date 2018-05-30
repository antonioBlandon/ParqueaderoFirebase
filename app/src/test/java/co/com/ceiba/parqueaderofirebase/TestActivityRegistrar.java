package co.com.ceiba.parqueaderofirebase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerVehiculo;
import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;
import co.com.ceiba.parqueaderofirebase.registrar.ActivityRegistrar;

import static co.com.ceiba.parqueaderofirebase.ParqueaderoBuilder.aParking;
import static co.com.ceiba.parqueaderofirebase.VehiculoBuilder.aVehicle;
import static org.mockito.Matchers.anyString;

public class TestActivityRegistrar {

    private ActivityRegistrar activityRegistrar;

    @Before
    public void prepareData() {
        activityRegistrar = new ActivityRegistrar();
    }

    @Test
    public void testValidarCamposNulos(){
        //Arrange
        String placa = "TRE567";
        //Act
        Vehiculo vehiculo = activityRegistrar.validarCamposNulos(placa, anyString());
        //Assert
        Assert.assertNotNull(vehiculo);
    }

    @Test
    public void testValidarCamposNulosSinPlaca(){
        //Arrange
        String placa = "";
        //Act
        Vehiculo vehiculo = activityRegistrar.validarCamposNulos(placa, anyString());
        //Assert
        Assert.assertNull(vehiculo);
    }

    @Test
    public void testValidarCupoCarro(){
        //Arrange
        Vehiculo vehiculo = aVehicle().build();
        Parqueadero parqueadero = aParking().build();
        boolean isCar = true;
        //Act
        boolean tieneCupo = activityRegistrar.validarCupo(vehiculo, isCar, parqueadero);
        //Assert
        Assert.assertEquals(false, tieneCupo);
    }

    @Test
    public void testValidarCupoMoto(){
        //Arrange
        Vehiculo vehiculo = aVehicle().build();
        Parqueadero parqueadero = aParking().build();
        boolean isCar = false;
        //Act
        boolean tieneCupo = activityRegistrar.validarCupo(vehiculo, isCar, parqueadero);
        //Assert
        Assert.assertEquals(false, tieneCupo);
    }

    @Test
    public void testValidarPlacaExiste() {
        //Arrange
        List<Vehiculo> vehiculoList = DataBaseManagerVehiculo.getListVehiculo();
        Vehiculo vehiculo = aVehicle().build();
        vehiculoList.add(vehiculo);
        //Act
        boolean placaExiste = activityRegistrar.validarPlacaExiste(vehiculoList, vehiculo);
        //Assert
        Assert.assertEquals(true, placaExiste);
    }

    @Test
    public void testValidarPlacaNoExiste() {
        //Arrange
        List<Vehiculo> vehiculoList = DataBaseManagerVehiculo.getListVehiculo();
        vehiculoList.add(aVehicle().build());
        Vehiculo nuevoVehiculo = aVehicle().withPlacaWithoutAinit("TRE345").build();
        //Act
        boolean placaExiste = activityRegistrar.validarPlacaExiste(vehiculoList, nuevoVehiculo);
        //Assert
        Assert.assertEquals(false, placaExiste);
    }

}
