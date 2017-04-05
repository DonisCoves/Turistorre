package cf.castellon.turistorre.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.facebook.FacebookSdk;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import cf.castellon.turistorre.R;

import static cf.castellon.turistorre.utils.Utils.*;
public class MiAplicacion extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //[1.- Facebook]


        FacebookSdk.sdkInitialize(this);
        //[END 1.- Facebook]
        tokenFireBase = FirebaseInstanceId.getInstance().getToken();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);        //Persistencia local
        establecerPreferenciasIniciales(getApplicationContext());
        usuarios = new ArrayList<>();
        racons = new ArrayList<>();
        setTheme(R.style.dialogos);
    }


}
