package co.com.ceiba.parqueaderofirebase.registrar;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Calendar;

import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerImpl;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerParqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class RegistroModel implements RegistroVehiculo.Model {

    private RegistroVehiculo.Presenter presenter;

    public RegistroModel(RegistroVehiculo.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Vehiculo crearVehiculo(String placa, int cilindraje) {
        return new Vehiculo(placa, cilindraje, Calendar.getInstance().getTimeInMillis());
    }

    @Override
    public void getTRM() {
        new Retrofit.Builder()
                .baseUrl("http://app.docm.co/")
                .build()
                .create(Trm.class)
                .getTrm()
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            presenter.showTRM("$ " + response.body().string().replace("\"", ""));
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        presenter.showErrorTRM();
                    }
                });
    }

    @Override
    public void agregarVehiculo(Vehiculo vehiculo, Parqueadero parqueadero) {
        boolean isCar = vehiculo.getCilindraje() == 0;
        DataBaseManagerImpl.getInstance().agregarVehiculo(isCar, vehiculo, DataBaseManagerParqueadero.getParqueadero());
        presenter.showRegistroExitoso();
    }

    interface Trm {
        @GET("prod/Dmservices/Utilities.svc/GetTRM")
        Call<ResponseBody> getTrm();
    }

}
