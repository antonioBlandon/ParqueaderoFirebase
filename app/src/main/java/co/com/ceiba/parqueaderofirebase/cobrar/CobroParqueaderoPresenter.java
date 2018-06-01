package co.com.ceiba.parqueaderofirebase.cobrar;

import android.app.Activity;

import co.com.ceiba.parqueaderofirebase.R;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerParqueadero;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerVehiculo;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

public class CobroParqueaderoPresenter implements CobroParqueadero.Presenter {

    private CobroParqueadero.View view;
    private CobroParqueadero.Model model;

    private Vehiculo vehiculo;

    public CobroParqueaderoPresenter(CobroParqueadero.View view) {
        this.view = view;
        model = new CobroParqueaderoModel(this);
    }

    @Override
    public void buscarPlaca(String placa) {
        validarCamposNulos(placa);
    }

    @Override
    public void cobrar() {
        if (vehiculo != null) {
            model.registrarSalida(vehiculo, DataBaseManagerParqueadero.getParqueadero());
        }
    }

    @Override
    public void validarCamposNulos(String placa) {
        if (placa.isEmpty()) {
            if (view != null) {
                view.setUpErrorSearch(((Activity) view).getString(R.string.placa_vacia));
            }
        } else {
            model.getVehiculo(DataBaseManagerVehiculo.getListVehiculo(), placa);
        }
    }

    @Override
    public void validarPlacaExiste(Vehiculo vehiculo) {
        if (vehiculo.getPlaca() != null) {
            if (view != null) {
                view.setUpInfo(vehiculo);
                this.vehiculo = vehiculo;
            }
        } else {
            if (view != null) {
                view.setUpErrorSearch(((Activity) view).getString(R.string.placa_no_existe));
            }
        }
    }

    @Override
    public void showProcesoExitoso(Vehiculo vehiculo) {
        if (view != null) {
            view.showProcesoExitoso();
            view.showResumen(vehiculo);
        }
    }
}
