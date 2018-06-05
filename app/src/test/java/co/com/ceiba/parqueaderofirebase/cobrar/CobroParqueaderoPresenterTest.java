package co.com.ceiba.parqueaderofirebase.cobrar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerVehiculo;
import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

import static co.com.ceiba.parqueaderofirebase.ParqueaderoBuilder.aParking;
import static co.com.ceiba.parqueaderofirebase.VehiculoBuilder.aVehicle;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CobroParqueaderoPresenterTest {

    @Mock
    private CobroParqueadero.Model model;
    @Mock
    private CobroParqueadero.View view;

    private CobroParqueaderoPresenter presenter;

    @Before
    public void setup() throws Exception {
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
    public void validarPlacaNoExiste() {
        Vehiculo vehiculo = mock(Vehiculo.class);
        when(vehiculo.getPlaca()).thenReturn(null);
        presenter.validarPlacaExiste(vehiculo);
        verify(view).setUpErrorSearch(anyInt());
    }

    @Test
    public void validarPlacaExiste() {
        Vehiculo vehiculo = mock(Vehiculo.class);
        when(vehiculo.getPlaca()).thenReturn("DFG231");
        presenter.validarPlacaExiste(vehiculo);
        verify(view).setUpInfo(vehiculo);
    }

    @Test
    public void showProcesoExitoso() {
        Vehiculo vehiculo = mock(Vehiculo.class);
        presenter.showProcesoExitoso(vehiculo);
        verify(view).showProcesoExitoso();
        verify(view).showResumen(vehiculo);
    }
}