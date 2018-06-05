package co.com.ceiba.parqueaderofirebase.registrar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

import static co.com.ceiba.parqueaderofirebase.ParqueaderoBuilder.aParking;
import static co.com.ceiba.parqueaderofirebase.VehiculoBuilder.aVehicle;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegistroPresenterTest {

    @Mock
    private RegistroVehiculo.View view;
    @Mock
    private RegistroVehiculo.Model model;

    private RegistroPresenter presenter;

    @Before
    public void setup () throws Exception {
        presenter = new RegistroPresenter(view);
    }

    @Test
    public void getTRM() {
    }

    @Test
    public void showTRM() {
        presenter.showTRM(anyString());
        verify(view).showTRM(anyString());
    }

    @Test
    public void showErrorTRM() {
        presenter.showErrorTRM();
        verify(view).showErrorTRM();
    }

    @Test
    public void showRegistroExitoso() {
        presenter.showRegistroExitoso();
        verify(view).showRegistroExitoso();
    }

    @Test
    public void validarCamposNulosPlacaVacia() {
        String placa = "";
        presenter.validarCamposNulos(placa,anyString());
        verify(view).showError(anyInt());
    }

    @Test
    public void validarCamposNulos() {
        String placa = "";
        presenter.validarCamposNulos(placa,anyString());
        presenter.validarPlaca(aVehicle().build());
    }

    @Test
    public void validarCamposNoNulos() {
        String placa = "ASDRET";
        presenter.validarCamposNulos(placa,anyString());
        presenter.validarPlaca(aVehicle().build());
    }

    @Test
    public void validarCupoCarro() {
    }

    @Test
    public void validarCupoMoto() {
    }

    @Test
    public void validarPlaca() {
    }

    @Test
    public void validarPlacaExiste() {
        Vehiculo vehiculo = aVehicle().build();
        List<Vehiculo> list = new ArrayList<>();
        list.add(vehiculo);
        presenter.validarPlacaExiste(list,vehiculo);
        verify(view).showError(anyInt());
    }

}