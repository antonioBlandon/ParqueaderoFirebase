package co.com.ceiba.parqueaderofirebase.data;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.parqueaderofirebase.data.entities.Vehiculo;

public class DataBaseManagerVehiculo implements DataBaseManager {

    static DataBaseManagerVehiculo instance;

    static List<Vehiculo> listVehiculo = new ArrayList<>();
    protected static List<String> listKeyVehiculo = new ArrayList<>();

    public static DataBaseManagerVehiculo getInstance() {
        if (instance == null) {
            instance = new DataBaseManagerVehiculo();
        }
        return instance;
    }

    public static List<Vehiculo> getListVehiculo() {
        return listVehiculo;
    }

    @Override
    public void read(String referenceNode, final Activity activity) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(referenceNode);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Vehiculo vehiculo = dataSnapshot.getValue(Vehiculo.class);
                listVehiculo.add(vehiculo);
                listKeyVehiculo.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Vehiculo vehiculo = dataSnapshot.getValue(Vehiculo.class);
                String vehiculoKey = dataSnapshot.getKey();

                int offerIndex = listKeyVehiculo.indexOf(vehiculoKey);
                if (offerIndex > -1) {
                    listVehiculo.set(offerIndex, vehiculo);
                } else {
                    Log.w("VEHICULO MODIFICADO: ", "onChildChanged:unknown_child:" + vehiculoKey);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String vehiculoKey = dataSnapshot.getKey();

                int offersIndex = listKeyVehiculo.indexOf(vehiculoKey);
                if (offersIndex > -1) {
                    listKeyVehiculo.remove(offersIndex);
                    listVehiculo.remove(offersIndex);
                } else {
                    Log.w("VEHICULO_ELIMINADO: ", "onChildRemoved:unknown_child:" + vehiculoKey);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("VEHICULO_MOVIDO: ", "onChildMoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("OnCancelled: ", "postComments:onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void write(String nodeReference, Object object) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(nodeReference);
        reference.setValue(object);
    }

}
