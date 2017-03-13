package cf.castellon.turistorre.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import cf.castellon.turistorre.R;
import cf.castellon.turistorre.adaptadores.AdaptadorDrawerList;
import cf.castellon.turistorre.adaptadores.DatosDrawerList;
import cf.castellon.turistorre.fragments.AcercaFragment;
import cf.castellon.turistorre.fragments.AjustesFragment;
import cf.castellon.turistorre.fragments.BandoFragment;
import cf.castellon.turistorre.fragments.BandoSeleccionadoFragment;
import cf.castellon.turistorre.fragments.FiestasFragment;
import cf.castellon.turistorre.fragments.GaleriaFragment;
import cf.castellon.turistorre.fragments.HomeFragment;
import cf.castellon.turistorre.fragments.LoginFragment;
import cf.castellon.turistorre.fragments.RaconsFragment;
import cf.castellon.turistorre.fragments.TrobamFragment;
import cf.castellon.turistorre.bean.Bando;

import static cf.castellon.turistorre.utils.Constantes.ACTION_GPO_BANDO;
import static cf.castellon.turistorre.utils.Constantes.ACTION_MAIN;
import static cf.castellon.turistorre.utils.Constantes.CUENTAS;
import static cf.castellon.turistorre.utils.Constantes.PERMISO_CAMARA;
import static cf.castellon.turistorre.utils.Constantes.PERMISO_ESCRIBIR_SD;
import static cf.castellon.turistorre.utils.Constantes.RC_GOOGLE_LOGIN;
import static cf.castellon.turistorre.utils.Constantes.TAG;
import static cf.castellon.turistorre.utils.Constantes.mDataBaseBandoRef;
import static cf.castellon.turistorre.utils.Utils.hideProgressDialog;
import static cf.castellon.turistorre.utils.Utils.seccion;

/**
 * Clase donde juntaremos todos los fragments , será como el recipiente
 */


public class MainActivityViejo extends AppCompatActivity implements ListView.OnItemClickListener/*, SelectorFragment.OnListSeccionListener*/ {
    /**
     * Es todo el objeto de la navegacion, que incluye el cajon "mDrawerList" y el frame donde
     * se incluirán los fragmentos(tambien se pueden meter activitys, pero con fragments el cambio es mas "armónico".
     */
    private DrawerLayout mDrawerLayout;
    /**
     * La lista que se ve a la izquierda el "drawer", "drawerlist" o el "cajón".
     */
    private ListView mDrawerList;
    /**
     * El fragmento que se insertará cuando pulsamos en la lista.
     */
    private Fragment frSeccion;
    ShareActionProvider mShareActionProvider;


    /*necesario para mostrar los datos en la lista de la drawer: */
    /**
     * Clase que contiene la estructura de datos para la drawer list.
     */
    private DatosDrawerList datosDrawer;

    /**
     * Adaptador utilizado para la drawer list.
     */
    private AdaptadorDrawerList adaptadorDrawer;
    /**
     * Intent para saltat a una activity: en el perfil pej..
     */
    private Intent intent;
    /**
     * Para las interacciones entre el drawer y la action bar
     * Action Bar que tiene los eventos de cerrar y abrir el drawer
     * Esta Action Bar especifica se utiliza porque en el developers
     * dice que cuando la Action Bar está desplegada solo aparecerá
     * el icono y el título de la app, los items de acciones se desplazan
     * al desbordamiento.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    /**
     * Para trabajar con los fragmentos
     */

    private FragmentTransaction fragmentTransaction;

    /**
     * En la creación de la actividad hacemos:
     * 1.-Creamos un acction Bar (ActionBarDrawerToggle) el cual manejará los eventos entre
     * la drawer y nuestra action bar normal . Estos eventos son:
     * onDrawerOpened y onDrawerClosed.- Se abre/cierra el "cajón" con lo que , segun la doc. oficial,
     * deberán desaparecer los iconos de la action bar y el título de la action bar no será el del
     * módulo en el que estemos sino el nombre de la app (Showpping en nuestor caso)
     * (todo esto en onDrawerOpened en el onDrawerClose tendremos que mostrar los botones y el titulo
     * del módulo seleccionado).
     * En estos métodos tendremos que llamar a invalidateOptionsMenu en donde le indicaremos a Android
     * que queremos cambiar la action Bar. invalidateOptionsMenu llama a onPrepareOptionsMenu  y
     * es aquí donde comprobamos si el drawer está abierto o cerrado(ya que tambien podemos
     * entrar a onPrepareOptionsMenu desde el método de cierre del drawer onDrawerClosed). Si el
     * cajón está abierto pues lo dicho hacemos invisibles los botones de la action y
     * mostramos el nombre de la app
     * <p/>
     * 2.- Anulamos los eventos del Drawer,es decir, el drawer ya no manejará cuando se abre/cierra el cajón
     * sino que lo hará el ActionBarDrawerToggle, ¿por qué? Sino tuvieramos Action Bar no pasaría nada,
     * pero al tenerla tenemos que hacer lo de mostrar/ocultar los iconos y el título de la action bar.
     * Para ello se utiliza esta clase ActionBarDrawerToggle que tiene los eventos de apertura/cierre del
     * cajón. Entonces la clase fuente que se encarga de recoger los eventos físicos(la DrawerLayout)
     * cojerá los eventos del ActionBarDrawerToggle.
     * <p/>
     * 3.- Habilitamos el icono de la actionBar para hacer que se abre/esconda  getActionBar().setHomeButtonEnabled(true)
     * <p/>
     * 4.- Habilitamos la navegación hacia arriba del icono de la actionBar getActionBar().setDisplayHomeAsUpEnabled(true)
     * <p/>
     * 5.- Montamos el listview mDrawerList. Con un clase con una estructura de los datos, un xml como recurso
     * que contendrá el diseño de una fila de la lista fila_drawer_list.xml y un adaptador para unirlo todo
     * y luego pasarselo a la listview para que muestre los datos
     * <p/>
     * 6.-Mostramos el fragment inicial
     */
    //Si la app esta en 2ºplano y llega una notificacion saltamos en el fragment pertinente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (getIntent().getAction()) {
            case ACTION_GPO_BANDO:
                Bundle bund = getIntent().getExtras();
                String uidBand = bund.getString("uidBando");
                mDataBaseBandoRef.child(uidBand).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Bando bando = dataSnapshot.getValue(Bando.class);
                                Bundle bund = new Bundle();
                                bund.putString("TITULO",bando.getTitulo());
                                bund.putString("DESCRIPCION",bando.getDescripcion());
                                bund.putString("IMAGEN_URL_STR",bando.getUrl());
                                //FragmentManager fragmentManager =

                                Fragment fragment = new BandoSeleccionadoFragment();

                                fragment.setArguments(bund);
//                                fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                                fragmentTransaction.replace(R.id.content_frame, frSeccion,seccion).commit();

                                FragmentTransaction transaccion = fragment.getChildFragmentManager().beginTransaction();
                                transaccion.replace(R.id.content_frame, fragment).commit();
                                //transaccion.addToBackStack(null);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e(TAG, "notificacionBando:onFailure: "+ databaseError.getMessage());
                                hideProgressDialog();
                            }
                        });
                break;
            case ACTION_MAIN:
                setContentView(R.layout.activity_main);
                seccion = "Home";
                frSeccion = new HomeFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (getSupportFragmentManager().findFragmentById(R.id.frSelector) != null) { //tablet
                    fragmentTransaction.add(R.id.flMain, frSeccion).commit();
                } else {
                    fragmentTransaction.add(R.id.content_frame, frSeccion).commit();
                    prepareDrawer();
                    mDrawerLayout.setDrawerListener(mDrawerToggle);
                }
        }


    }

    private void prepareDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Explicado en 1.-

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.mipmap.ic_navigation_drawer, R.string.open, R.string.close) {
            /*    En estos métodos tendremos que llamar a invalidateOptionsMenu en donde le indicaremos a Android
            *    que queremos cambiar la action Bar. invalidateOptionsMenu llama a onPrepareOptionsMenu  */
            @Override
            public void onDrawerOpened(View drawerView) {
                //mostramos el nombre de la app
                invalidateOptionsMenu();
                getSupportActionBar().setTitle(getTitle());
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
                getSupportActionBar().setTitle(seccion);
            }
        };
        //Explicado en 3.-
        //Enable or disable the "home" button in the corner of the action bar.
        //(Note that this is the application home/up affordance on the action bar, not the systemwide home button.)
        //This defaults the application should call this method to enable interaction with the home/up affordance.
        //Habilitamos la imagen como botón
        getSupportActionBar().setHomeButtonEnabled(true);

        //Explicado en 4.-
        //Set whether home should be displayed as an "up" affordance.
        //Set this to true if selecting "home" returns up by a single level in your
        //UI rather than back to the top level or front page.
        //Habilitamos navegacion hacia arriba
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adaptadorDrawer = new AdaptadorDrawerList(this);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        //iconosDrawer.recycle();  //como no se ejecuta el garbaje tenemos que hacerlo nosotros
        mDrawerList.setAdapter(adaptadorDrawer);
        mDrawerList.setOnItemClickListener(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDrawerOpen;
        /*comprobamos si el drawer está abierto o cerrado(ya que tambien podemos
        *    entrar a onPrepareOptionsMenu desde el método de cierre del drawer onDrawerClosed). Si el
        *    cajón está abierto pues lo dicho hacemos invisibles los botones de la action
        *
        */
        if (getSupportFragmentManager().findFragmentById(R.id.frSelector) == null) { //movil
            //deberán desaparecer los iconos de la action bar
            isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
            if (isDrawerOpen) {
                //menu.findItem(R.id.action_settings).setVisible(false);
                String nada="nada";
            } else { //tendremos que mostrar los botones y el titulo
                //del módulo seleccionado).
//                menu.findItem(R.id.action_settings).setVisible(true);
           /* if (mDrawerList.isSelected()) {
                getActionBar().setTitle(mDrawerList.getSelectedItem().toString());

            }*/
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        seccion = ((TextView) view.findViewById(R.id.tvTitulo)).getText().toString();
        switch (seccion) {
            case "Home":
                frSeccion = new HomeFragment();
                seccion = "TurisTorre";
                break;
            case "Racons":
                frSeccion = new RaconsFragment();
                break;
            case "Galeria":
                frSeccion = new GaleriaFragment();
                break;
            case "Bandos":
                frSeccion = new BandoFragment();
                break;
            case "Trobam":
                frSeccion = new TrobamFragment();
                break;
            case "Login":
                frSeccion = new LoginFragment();
                break;
            case "Fiestas":
                frSeccion = new FiestasFragment();
                break;
            case "Ajustes":
                frSeccion = new AjustesFragment();
                break;
            case "Acerca_de":
                frSeccion = new AcercaFragment();
                break;
        }
        mDrawerList.setItemChecked(position, true);
        getSupportActionBar().setTitle(seccion);
        mDrawerLayout.closeDrawer(mDrawerList);
        //fragmentTransaction.remove(getFragmentManager().getFragment());
        fragmentTransaction.replace(R.id.content_frame, frSeccion,seccion).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }



    protected Intent getDefaultShareIntent() {
        /*Intent shareIntent = new Intent(Intent.ActionSend);
        shareIntent.SetType("text/plain");
        shareIntent.PutExtra(Android.Content.Intent.ExtraText,"TestText");
        shareIntent.PutExtra(Android.Content.Intent.ExtraSubject, "TestSubject");*/
        /*Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Some text");  // For debugging
        shareIntent.setType("text/plain");*/
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Extra Text");
        return shareIntent;

       /* Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
       /* Job job = getArguments().getSerializable(JOB);
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, job.title);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, job.toText());*//*
        return shareIntent;*/
     /*   Intent myShareIntent = new Intent(Intent.ACTION_SEND);
        myShareIntent.setType("image*//*");
        myShareIntent.putExtra(Intent.EXTRA_STREAM, myImageUri);*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //	Si el ikono de nuestra barra de accion se ha pulsado devolvemos true
        // para indicar que el evento se ha procesado y que se encargar� el ActionBarDrawerToggle de procesarlo
        int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


    /*@Override
    public void onItemSelected(String seccionStr) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (seccionStr) {
            case "Home":
                frSeccion = new HomeFragment();
                break;
            case "Racons":
                frSeccion = new RaconsFragment();
                break;
            case "Galeria":
                frSeccion = new GaleriaFragment();
                break;
            case "Trobam":
                frSeccion = new TrobamFragment();
                break;
            case "Login":
                frSeccion = new LoginFragment();
                break;
            case "Fiestas":
                frSeccion = new FiestasFragment();
                break;
            case "Ajustes":
                frSeccion = new AjustesFragment();
                break;
            case "Acerca_de":
                frSeccion = new AcercaFragment();
                break;
        }
        fragmentTransaction.replace(R.id.flMain, frSeccion).commit();
        getSupportFragmentManager().executePendingTransactions();
        LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag("prueba");
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_GOOGLE_LOGIN) {
            LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag(seccion);
            fragment.onActivityResult(requestCode, resultCode, data);
        } else
            super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CUENTAS) {
            LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag(seccion);
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }if (requestCode == PERMISO_CAMARA || requestCode == PERMISO_ESCRIBIR_SD ) {
            GaleriaFragment fragment = (GaleriaFragment) getSupportFragmentManager().findFragmentByTag(seccion);
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public Intent getIntent() {
      /*  switch (super.getIntent().getAction()) {
            case (ACTION_GPO_BANDO):
                Intent intent = new Intent(this, BandoFragment.class);
                startActivity(intent);
        }*/
        return super.getIntent();
    }

}
