package co.com.ceiba.parqueaderofirebase.registrar;

import android.content.Context;

import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

public interface RegistroVehiculo {

    interface View {
        void mostrarCobrar();

        void showError(String messageError);

        void showErrorTRM(String messageError);

        void showRegistroExitoso();

        void showTRM(String TRM);
    }

    interface Presenter {
        void getTRM(Context context);

        void showTRM(String Trm);

        void showErrorTRM(String messageError);

        void showRegistroExitoso();

        void validarCamposNulos(String placa, String cilindraje);
    }

    interface Model {
        Vehiculo crearVehiculo(String placa, int cilindraje);

        void getTRM(Context context);

        void agregarVehiculo(Vehiculo vehiculo, Parqueadero parqueadero);
    }

}
