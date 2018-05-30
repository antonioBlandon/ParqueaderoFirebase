package co.com.ceiba.parqueaderofirebase.cobrar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import co.com.ceiba.parqueaderofirebase.R;
import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;
import co.com.ceiba.parqueaderofirebase.registrar.ActivityRegistrar;

public class ActivityCobrar extends AppCompatActivity {

    private Context context = ActivityCobrar.this;
    public static Parqueadero parqueadero;

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

        /*Button btnBuscarPlaca = (Button) findViewById(R.id.btn_cobrar_buscar_placa);
        btnBuscarPlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placa = etBuscarPlaca.getText().toString();
                if (!placa.isEmpty()) {
                    DataBaseVehiculoManager dataBase = new DataBaseVehiculoManager(context);
                    vehiculo = dataBase.read(placa);
                    setUpInfo();
                } else {
                    Toast.makeText(context, getString(R.string.placa_vacia), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnCobrarParqueadero = (Button) findViewById(R.id.btn_cobrar_cobrar_parqueadero);
        btnCobrarParqueadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehiculo.setValorApagarParqueadero(cobrar());
                sacarVehiculo();
                guardarDatos();
                actualizarVista();
                lanzarResumen();
            }
        });*/

    }

    public void actualizarVista() {
        Toast.makeText(context, getString(R.string.cobro_exitoso), Toast.LENGTH_SHORT).show();
        etBuscarPlaca.setText("");
        llInfoVehiculo.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            startActivity(new Intent(context, ActivityRegistrar.class));
        }
        return super.onKeyDown(keyCode, event);
    }

    /*public long cobrar() {
        Vigilante vigilante = VigilanteImpl.getInstance();
        vehiculo.setFechaSalida(Calendar.getInstance().getTimeInMillis());
        long tiempoParqueadero = vigilante.calcularTiempoVehiculoParqueadero(vehiculo.getFechaIngreso(), vehiculo.getFechaSalida());
        long[] diasHoras = vigilante.calcularDiasHoras(tiempoParqueadero);
        vehiculo.setDiasEnParqueadero(diasHoras[0]);
        vehiculo.setHorasEnParqueadero(diasHoras[1]);
        return vigilante.cobrarParqueadero(vehiculo);
    }

    public void guardarDatos() {
        DataBaseVehiculoManager db = new DataBaseVehiculoManager(context);
        db.update(vehiculo);
    }

    public void lanzarResumen() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View viewResumen = getLayoutInflater().inflate(R.layout.content_dialog_factura, null);

        TextView tvPlacaResumen = (TextView) viewResumen.findViewById(R.id.text_view_resumen_placa);
        tvPlacaResumen.setText(vehiculo.getPlaca());
        TextView tvFechaIngresoResumen = (TextView) viewResumen.findViewById(R.id.text_view_resumen_fecha_ingreso);
        tvFechaIngresoResumen.setText(Utils.getDateHourInFormat(vehiculo.getFechaIngreso()));
        TextView tvFechaSalida = (TextView) viewResumen.findViewById(R.id.text_view_resumen_fecha_salida);
        tvFechaSalida.setText(Utils.getDateHourInFormat(vehiculo.getFechaSalida()));
        TextView tvTiempoParqueadero = (TextView) viewResumen.findViewById(R.id.text_view_resumen_tiempo);
        tvTiempoParqueadero.setText(Long.toString(vehiculo.getDiasEnParqueadero() * 24 + vehiculo.getHorasEnParqueadero()));
        TextView tvCosto = (TextView) viewResumen.findViewById(R.id.text_view_resumen_valor_a_pagar);
        tvCosto.setText(Double.toString(vehiculo.getValorApagarParqueadero()));

        TextView tvCilindrajeResumen = (TextView) viewResumen.findViewById(R.id.text_view_resumen_cilindraje);
        if (vehiculo instanceof Moto) {
            tvCilindrajeResumen.setText(Integer.toString(((Moto) vehiculo).getCilindraje()));
        }

        dialog.setView(viewResumen).setPositiveButton(android.R.string.ok, null);
        dialog.create().show();
    }

    public void sacarVehiculo() {
        DataBaseParqueaderoManager db = new DataBaseParqueaderoManager(context);
        if (vehiculo instanceof Moto) {
            db.update(DataBaseConstans.TablaParqueadero.CANTIDAD_MOTOS, false);
        } else {
            db.update(DataBaseConstans.TablaParqueadero.CANTIDAD_CARROS, false);
        }
    }

    public void setUpInfo() {

        if (vehiculo == null) {
            llInfoVehiculo.setVisibility(View.GONE);
            Toast.makeText(context, getString(R.string.error_read_data_base), Toast.LENGTH_SHORT).show();
            return;
        } else if (vehiculo.getPlaca() == null) {
            llInfoVehiculo.setVisibility(View.GONE);
            Toast.makeText(context, getString(R.string.placa_no_existe), Toast.LENGTH_SHORT).show();
            return;
        }
        llInfoVehiculo.setVisibility(View.VISIBLE);
        tvPlaca.setText(vehiculo.getPlaca());
        tvFechaIngreso.setText(Utils.getDateHourInFormat(vehiculo.getFechaIngreso()));
        if (vehiculo instanceof Moto) {
            tvCilindraje.setText(Integer.toString(((Moto) vehiculo).getCilindraje()));
        }

    }
*/
}
