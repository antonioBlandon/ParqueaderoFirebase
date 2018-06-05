package co.com.ceiba.parqueaderofirebase.cobrar;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import co.com.ceiba.parqueaderofirebase.R;
import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;
import co.com.ceiba.parqueaderofirebase.domain.VigilanteImpl;
import co.com.ceiba.parqueaderofirebase.registrar.RegistroActivity;
import co.com.ceiba.parqueaderofirebase.utils.Utils;

public class CobroParqueaderoActivity extends AppCompatActivity implements CobroParqueadero.View {

    private LinearLayout llInfoVehiculo;
    private TextView tvPlaca;
    private TextView tvCilindraje;
    private TextView tvFechaIngreso;
    private EditText etBuscarPlaca;

    private CobroParqueadero.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobro_parqueadero);

        llInfoVehiculo = findViewById(R.id.linear_layout_cobra_info_vehiculo);
        tvPlaca = findViewById(R.id.text_view_cobrar_placa);
        tvCilindraje = findViewById(R.id.text_view_cobrar_cilindraje);
        tvFechaIngreso = findViewById(R.id.text_view_cobrar_fecha_ingreso);

        etBuscarPlaca = findViewById(R.id.edit_text_cobrar_placa);
        etBuscarPlaca.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(6)});

        Button btnBuscarPlaca = findViewById(R.id.btn_cobrar_buscar_placa);
        btnBuscarPlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.buscarPlaca(etBuscarPlaca.getText().toString());
            }
        });

        Button btnCobrarParqueadero = findViewById(R.id.btn_cobrar_cobrar_parqueadero);
        btnCobrarParqueadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.cobrar();
            }
        });

        presenter = new CobroParqueaderoPresenter(this);

    }

    @Override
    public void showProcesoExitoso() {
        Toast.makeText(this, getString(R.string.cobro_exitoso), Toast.LENGTH_SHORT).show();
        etBuscarPlaca.setText("");
        llInfoVehiculo.setVisibility(View.GONE);
    }

    @Override
    public void showResumen(Vehiculo vehiculo) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View viewResumen = getLayoutInflater().inflate(R.layout.content_dialog_factura, null);

        TextView tvPlacaResumen = viewResumen.findViewById(R.id.text_view_resumen_placa);
        tvPlacaResumen.setText(vehiculo.getPlaca());
        TextView tvFechaIngresoResumen = viewResumen.findViewById(R.id.text_view_resumen_fecha_ingreso);
        tvFechaIngresoResumen.setText(Utils.getDateHourInFormat(vehiculo.getFechaIngreso()));
        TextView tvFechaSalida = viewResumen.findViewById(R.id.text_view_resumen_fecha_salida);
        tvFechaSalida.setText(Utils.getDateHourInFormat(vehiculo.getFechaSalida()));
        TextView tvTiempoParqueadero = viewResumen.findViewById(R.id.text_view_resumen_tiempo);
        tvTiempoParqueadero.setText(
                Long.toString(VigilanteImpl.getInstance().calcularTiempoVehiculoParqueadero(vehiculo.getFechaIngreso()
                        , vehiculo.getFechaSalida())) + " hora(s)"
        );
        TextView tvCosto = viewResumen.findViewById(R.id.text_view_resumen_valor_a_pagar);
        tvCosto.setText(Double.toString(vehiculo.getValorPagado()));

        TextView tvCilindrajeResumen = viewResumen.findViewById(R.id.text_view_resumen_cilindraje);
        if (vehiculo.getCilindraje() != 0) {
            tvCilindrajeResumen.setText(Integer.toString(vehiculo.getCilindraje()));
        }

        dialog.setView(viewResumen).setPositiveButton(android.R.string.ok, null);
        dialog.create().show();
    }

    @Override
    public void setUpErrorSearch(int idMessageError) {
        Toast.makeText(this, getString(idMessageError), Toast.LENGTH_SHORT).show();
        llInfoVehiculo.setVisibility(View.GONE);
    }

    @Override
    public void setUpInfo(Vehiculo vehiculo) {
        llInfoVehiculo.setVisibility(View.VISIBLE);
        tvPlaca.setText(vehiculo.getPlaca());
        tvFechaIngreso.setText(Utils.getDateHourInFormat(vehiculo.getFechaIngreso()));
        if (vehiculo.getCilindraje() != 0) {
            tvCilindraje.setText(Integer.toString(vehiculo.getCilindraje()));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            startActivity(new Intent(this, RegistroActivity.class));
        }
        return super.onKeyDown(keyCode, event);
    }

}
