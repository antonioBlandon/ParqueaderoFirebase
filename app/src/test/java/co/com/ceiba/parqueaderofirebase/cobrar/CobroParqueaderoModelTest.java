package co.com.ceiba.parqueaderofirebase.cobrar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.mockito.runners.MockitoJUnitRunner;

import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerImpl;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerParqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

import static co.com.ceiba.parqueaderofirebase.ParqueaderoBuilder.aParking;
import static co.com.ceiba.parqueaderofirebase.VehiculoBuilder.aVehicle;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CobroParqueaderoModelTest {

    @Mock
    private CobroParqueadero.Presenter presenter;

    private CobroParqueaderoModel model;

    @Before
    public void setup() throws Exception {
        model = new CobroParqueaderoModel(presenter);
    }

    @Test
    public void getVehiculo() {
    }

    @Test
    public void registrarSalida() {

    }

    @Test
    public void cobrar() {
        //Arrange
        Vehiculo vehiculo = aVehicle().build();
        Parqueadero parqueadero = aParking().build();
        //Act
        long valorParqueadero = model.cobrar(vehiculo, parqueadero);
        //Assert
        Assert.assertEquals(11000, valorParqueadero);
    }
}