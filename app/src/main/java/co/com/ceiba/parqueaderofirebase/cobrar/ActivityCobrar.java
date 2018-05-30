package co.com.ceiba.parqueaderofirebase.cobrar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import co.com.ceiba.parqueaderofirebase.R;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerParqueadero;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerVehiculo;
import co.com.ceiba.parqueaderofirebase.data.DataBaseManagerImpl;
import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;
import co.com.ceiba.parqueaderofirebase.domain.Vigilante;
import co.com.ceiba.parqueaderofirebase.domain.VigilanteImpl;
import co.com.ceiba.parqueaderofirebase.registrar.ActivityRegistrar;
import co.com.ceiba.parqueaderofirebase.utils.Utils;

public class ActivityCobrar extends AppCompatActivity {

    private Activity context = ActivityCobrar.this;
    private Vehiculo vehiculo;
    private boolean isCar;

    private LinearLayout llInfoVehiculo;
    private TextView tvPlaca;
    private TextView tvCilindraje;
    private TextView tvFechaIngreso;
    private EditText etBuscarPlaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobrar);

        llInfoVehiculo = (LinearLayout) findViewById(R.id.linear_layout_cobra_info_vehiculo);
        etBuscarPlaca = (EditText) findViewById(R.id.edit_text_cobrar_placa);
        tvPlaca = (TextView) findViewById(R.id.text_view_cobrar_placa);
        tvCilindraje = (TextView) findViewById(R.id.text_view_cobrar_cilindraje);
        tvFechaIngreso = (TextView) findViewById(R.id.text_view_cobrar_fecha_ingreso);

        etBuscarPlaca = (EditText) findViewById(R.id.edit_text_cobrar_placa);
        etBuscarPlaca.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(6)});

        Button btnBuscarPlaca = (Button) findViewById(R.id.btn_cobrar_buscar_placa);
        btnBuscarPlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placa = etBuscarPlaca.getText().toString();
                if (!placa.isEmpty()) {
                    vehiculo = getVehiculo(DataBaseManagerVehiculo.getListVehiculo(), placa);
                    boolean placaExiste = vehiculo.getPlaca() != null;
                    if (placaExiste) {
                        isCar = vehiculo.getCilindraje() == 0;
                        setUpInfo(vehiculo, isCar);
                    } else {
                        Toast.makeText(context, getString(R.string.placa_no_existe), Toast.LENGTH_SHORT).show();
                        llInfoVehiculo.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(context, getString(R.string.placa_vacia), Toast.LENGTH_SHORT).show();
                    llInfoVehiculo.setVisibility(View.GONE);
                }
            }
        });

        Button btnCobrarParqueadero = (Button) findViewById(R.id.btn_cobrar_cobrar_parqueadero);
        btnCobrarParqueadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehiculo.setFechaSalida(Calendar.getInstance().getTimeInMillis());
                vehiculo.setValorPagado(cobrar(vehiculo, DataBaseManagerParqueadero.getParqueadero()));
                DataBaseManagerImpl.getInstance().eliminarVehiculo(isCar, vehiculo, DataBaseManagerParqueadero.getParqueadero());
                actualizarVista();
                lanzarResumen(vehiculo, isCar, context);
            }
        });

    }

    public void actualizarVista() {
        Toast.makeText(context, getString(R.string.cobro_exitoso), Toast.LENGTH_SHORT).show();
        etBuscarPlaca.setText("");
        llInfoVehiculo.setVisibility(View.GONE);
    }

    public long cobrar(Vehiculo vehiculo, Parqueadero parqueadero) {
        Vigilante vigilante = VigilanteImpl.getInstance();
        long tiempoParqueadero = vigilante.calcularTiempoVehiculoParqueadero(vehiculo.getFechaIngreso(), vehiculo.getFechaSalida());
        long[] diasHoras = vigilante.calcularDiasHoras(tiempoParqueadero);
        vehiculo.setDiasEnParqueadero(diasHoras[0]);
        vehiculo.setHorasEnParqueadero(diasHoras[1]);
        return vigilante.cobrarParqueadero(vehiculo, parqueadero);
    }

    public Vehiculo getVehiculo(List<Vehiculo> listVehiculo, String placa) {
        Vehiculo vehiculoRetrieve = new Vehiculo();
        for (Vehiculo itemVehiculo : listVehiculo) {
            if (itemVehiculo.getPlaca().contains(placa)) {
                vehiculoRetrieve = itemVehiculo;
                break;
            }
        }
        return vehiculoRetrieve;
    }

    public void lanzarResumen(Vehiculo vehiculo, boolean isCar, Activity context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View viewResumen = getLayoutInflater().inflate(R.layout.content_dialog_factura, null);

        TextView tvPlacaResumen = (TextView) viewResumen.findViewById(R.id.text_view_resumen_placa);
        tvPlacaResumen.setText(vehiculo.getPlaca());
        TextView tvFechaIngresoResumen = (TextView) viewResumen.findViewById(R.id.text_view_resumen_fecha_ingreso);
        tvFechaIngresoResumen.setText(Utils.getDateHourInFormat(vehiculo.getFechaIngreso()));
        TextView tvFechaSalida = (TextView) viewResumen.findViewById(R.id.text_view_resumen_fecha_salida);
        tvFechaSalida.setText(Utils.getDateHourInFormat(vehiculo.getFechaSalida()));
        TextView tvTiempoParqueadero = (TextView) viewResumen.findViewById(R.id.text_view_resumen_tiempo);
        tvTiempoParqueadero.setText(
                Long.toString(VigilanteImpl.getInstance().calcularTiempoVehiculoParqueadero(vehiculo.getFechaIngreso()
                        , vehiculo.getFechaSalida())) + " hora(s)"
        );
        TextView tvCosto = (TextView) viewResumen.findViewById(R.id.text_view_resumen_valor_a_pagar);
        tvCosto.setText(Double.toString(vehiculo.getValorPagado()));

        TextView tvCilindrajeResumen = (TextView) viewResumen.findViewById(R.id.text_view_resumen_cilindraje);
        if (!isCar) {
            tvCilindrajeResumen.setText(Integer.toString(vehiculo.getCilindraje()));
        }

        dialog.setView(viewResumen).setPositiveButton(android.R.string.ok, null);
        dialog.create().show();
    }

    public void setUpInfo(Vehiculo vehiculo, boolean isCar) {
        llInfoVehiculo.setVisibility(View.VISIBLE);
        tvPlaca.setText(vehiculo.getPlaca());
        tvFechaIngreso.setText(Utils.getDateHourInFormat(vehiculo.getFechaIngreso()));
        if (!isCar) {
            tvCilindraje.setText(Integer.toString(vehiculo.getCilindraje()));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            startActivity(new Intent(context, ActivityRegistrar.class));
        }
        return super.onKeyDown(keyCode, event);
    }

}
