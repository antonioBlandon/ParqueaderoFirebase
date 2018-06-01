package co.com.ceiba.parqueaderofirebase.registrar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.mockito.runners.MockitoJUnitRunner;

import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

import static co.com.ceiba.parqueaderofirebase.VehiculoBuilder.aVehicle;
import static co.com.ceiba.parqueaderofirebase.ParqueaderoBuilder.aParking;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegistroModelTest {

    @Mock
    private RegistroVehiculo.Presenter presenter;

    private RegistroModel model;
    private Vehiculo vehiculo;

    @Before
    public void setup () throws Exception{
        model = new RegistroModel(presenter);
        vehiculo = aVehicle().build();
    }

    @Test
    public void crearVehiculo() {
        //Arrange
        String placa = "RTY342";
        int cilindraje = 0;
        //Act
        Vehiculo vehiculo = model.crearVehiculo(placa, cilindraje);
        //Assert
        Assert.assertNotNull(vehiculo);
    }

    @Test
    public void getTRM() {
        /*model.getTRM();
        verify(presenter).showTRM(anyString());*/
    }

}