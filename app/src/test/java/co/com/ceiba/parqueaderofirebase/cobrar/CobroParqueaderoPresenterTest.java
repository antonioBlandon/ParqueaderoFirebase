package co.com.ceiba.parqueaderofirebase.cobrar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

import static co.com.ceiba.parqueaderofirebase.ParqueaderoBuilder.aParking;
import static co.com.ceiba.parqueaderofirebase.VehiculoBuilder.aVehicle;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CobroParqueaderoPresenterTest {

    @Mock
    private CobroParqueadero.Model model;
    @Mock
    private CobroParqueadero.View view;

    private CobroParqueaderoPresenter presenter;

    @Before
    public void setup() throws Exception{
        presenter = new CobroParqueaderoPresenter(view);
    }

    @Test
    public void buscarPlaca() {
    }

    @Test
    public void cobrar() {

    }

    @Test
    public void validarCamposNulos() {
    }

    @Test
    public void validarPlacaExiste() {
    }

    @Test
    public void showProcesoExitoso() {
    }
}