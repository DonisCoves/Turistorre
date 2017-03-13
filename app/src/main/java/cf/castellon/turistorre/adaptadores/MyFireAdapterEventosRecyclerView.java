package cf.castellon.turistorre.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import cf.castellon.turistorre.R;
import cf.castellon.turistorre.bean.Evento;
import static cf.castellon.turistorre.utils.Utils.*;

/**
 * Created by pccc on 28/02/2017.
 */
public class MyFireAdapterEventosRecyclerView  extends FirebaseIndexRecyclerAdapter<Evento, MyFireAdapterEventosRecyclerView.MyFireViewHolder>
        implements View.OnClickListener {
    private View.OnClickListener listener;

    public MyFireAdapterEventosRecyclerView(Class<Evento> modelClass, int modelLayout, Class<MyFireViewHolder> viewHolderClass, Query keyRef, DatabaseReference dataRef) {
        super(modelClass, modelLayout, viewHolderClass, keyRef, dataRef);
    }

    @Override
    public MyFireViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_eventos_layout, viewGroup, false);
        cambiarColorFondoTV(itemView);

        itemView.setOnClickListener(this);
        MyFireViewHolder holder = new MyFireViewHolder(itemView);
        return holder;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
    }

    @Override
    protected void populateViewHolder(MyFireViewHolder viewHolder, Evento model, int position) {
        viewHolder.bindDatos(model);
    }

    public static class MyFireViewHolder extends RecyclerView.ViewHolder {
        private TextView horaTv,tituloTv,descripcionTv;

        public MyFireViewHolder(View itemView) {
            super(itemView);
            tituloTv = (TextView) itemView.findViewById(R.id.tvTituloEvento);
            horaTv = (TextView) itemView.findViewById(R.id.tvHora);
            descripcionTv = (TextView) itemView.findViewById(R.id.tvDescripcion);
        }

        public void bindDatos(Evento evento) {
            tituloTv.setText(evento.getTitulo());
            horaTv.setText(evento.getHora_inicial());
            descripcionTv.setText(evento.getDescripcion());
        }

    }

}
