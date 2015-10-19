package com.tehagotuweb.portalvallecas.app.UI;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.tehagotuweb.portalvallecas.app.Modelo.ScriptDatabase;
import com.tehagotuweb.portalvallecas.app.R;
import com.tehagotuweb.portalvallecas.app.Web.VolleySingleton;

public class FeedAdapter extends CursorAdapter {

    /*
    Etiqueta de Depuraci�n
     */
    private static final String TAG = FeedAdapter.class.getSimpleName();

    /**
     * View holder para evitar multiples llamadas de findViewById()
     */
    static class ViewHolder {
        TextView titulo;
        TextView descripcion;
        NetworkImageView imagen;

        int tituloI;
        int descripcionI;
        int imagenI;
    }

    public FeedAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_noticias, null, false);

        ViewHolder vh = new ViewHolder();

        // Almacenar referencias
        vh.titulo = (TextView) view.findViewById(R.id.titulo);
        vh.descripcion = (TextView) view.findViewById(R.id.descripcion);
        vh.imagen = (NetworkImageView) view.findViewById(R.id.imagen);

        // Setear indices
        vh.tituloI = cursor.getColumnIndex(ScriptDatabase.ColumnEntradas.TITULO);
        vh.descripcionI = cursor.getColumnIndex(ScriptDatabase.ColumnEntradas.DESCRIPCION);
        vh.imagenI = cursor.getColumnIndex(ScriptDatabase.ColumnEntradas.URL_MINIATURA);

        view.setTag(vh);

        return view;
    }

    public void bindView(View view, Context context, Cursor cursor) {

        final ViewHolder vh = (ViewHolder) view.getTag();

        // Setear el texto al titulo
        vh.titulo.setText(cursor.getString(vh.tituloI));

        // Obtener acceso a la descripci�n y su longitud
        int ln = cursor.getString(vh.descripcionI).length();
        String descripcion = cursor.getString(vh.descripcionI);

        // Acortar descripci�n a 77 caracteres
        if (ln >= 150)
            vh.descripcion.setText(descripcion.substring(0, 150)+"...");
        else vh.descripcion.setText(descripcion);

        // Obtener URL de la imagen
        String thumbnailUrl = cursor.getString(vh.imagenI);

        // Obtener instancia del ImageLoader
        ImageLoader imageLoader = VolleySingleton.getInstance(context).getImageLoader();

        // Volcar datos en el image view
        vh.imagen.setImageUrl(thumbnailUrl, imageLoader);

    }
}