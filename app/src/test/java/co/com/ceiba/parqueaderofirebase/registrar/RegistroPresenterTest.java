package co.com.ceiba.parqueaderofirebase.registrar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
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
    public void validarCamposNulos() {
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
    }
}