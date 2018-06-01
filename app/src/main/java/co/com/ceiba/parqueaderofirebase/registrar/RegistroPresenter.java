package co.com.ceiba.parqueaderofirebase.registrar;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import co.com.ceiba.parqueaderofirebase.R;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerParqueadero;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerVehiculo;
import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;
import co.com.ceiba.parqueaderofirebase.domain.VigilanteImpl;

public class RegistroPresenter implements RegistroVehiculo.Presenter {

    private RegistroVehiculo.View view;
    private RegistroVehiculo.Model model;

    public RegistroPresenter(RegistroVehiculo.View view) {
        this.view = view;
        model = new RegistroModel(this);
    }

    @Override
    public void getTRM(Context context) {
        model.getTRM(context);
    }

    @Override
    public void showTRM(String trm) {
        if (view != null) {
            view.showTRM(trm);
        }
    }

    @Override
    public void showErrorTRM(String messageError) {
        if (view != null) {
            view.showErrorTRM(messageError);
        }
    }

    @Override
    public void showRegistroExitoso() {
        if (view != null) {
            view.showRegistroExitoso();
        }
    }

    @Override
    public void validarCamposNulos(String placa, String cilindraje) {
        if (placa.isEmpty()) {
            if (view != null) {
                view.showError(((Activity) view).getString(R.string.placa_vacia));
            }
        } else {
            if (cilindraje.isEmpty()) {
                validarPlaca(model.crearVehiculo(placa, 0));
            } else {
                validarPlaca(model.crearVehiculo(placa, Integer.valueOf(cilindraje)));
            }
        }
    }

    public void validarCupoCarro(Vehiculo vehiculo, Parqueadero parqueadero) {
        if (VigilanteImpl.getInstance().validarCantidadCarros(parqueadero.getCantidadCarros())) {
            validarPlacaExiste(DataBaseManagerVehiculo.getListVehiculo(), vehiculo);
        } else {
            if (view != null) {
                view.showError(((Activity) view).getString(R.string.parqueadero_lleno));
            }
        }
    }

    public void validarCupoMoto(Vehiculo vehiculo, Parqueadero parqueadero) {
        if (VigilanteImpl.getInstance().validarCantidadMotos(parqueadero.getCantidadMotos())) {
            validarPlacaExiste(DataBaseManagerVehiculo.getListVehiculo(), vehiculo);
        } else {
            if (view != null) {
                view.showError(((Activity) view).getString(R.string.parqueadero_lleno));
            }
        }
    }

    public void validarPlaca(Vehiculo vehiculo) {
        if (VigilanteImpl.getInstance().validarPlaca(vehiculo.getPlaca(), vehiculo.getFechaIngreso())) {
            if (vehiculo.getCilindraje() == 0) {
                validarCupoCarro(vehiculo, DataBaseManagerParqueadero.getParqueadero());
            } else {
                validarCupoMoto(vehiculo, DataBaseManagerParqueadero.getParqueadero());
            }
        } else {
            if (view != null) {
                view.showError(((Activity) view).getString(R.string.vehiculo_no_autorizado));
            }
        }
    }

    public void validarPlacaExiste(List<Vehiculo> listVehiculo, Vehiculo newVehicle) {
        for (Vehiculo vehiculoItem : listVehiculo) {
            if (vehiculoItem.getPlaca().contains(newVehicle.getPlaca())) {
                if (view != null) {
                    view.showError(((Activity) view).getString(R.string.placa_existe));
                }
                return;
            }
        }
        model.agregarVehiculo(newVehicle, DataBaseManagerParqueadero.getParqueadero());
    }

}
