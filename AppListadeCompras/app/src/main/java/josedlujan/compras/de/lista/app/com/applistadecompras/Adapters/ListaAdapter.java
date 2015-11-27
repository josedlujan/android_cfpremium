package josedlujan.compras.de.lista.app.com.applistadecompras.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import josedlujan.compras.de.lista.app.com.applistadecompras.R;

/**
 * Created by jose on 25/11/15.
 */
public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder> {
    Context myContext;
    CursorAdapter myCursorAdapter;


    public ListaAdapter(Context context, Cursor cursor){
        myContext = context;

        myCursorAdapter = new CursorAdapter(myContext,cursor,0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View retView = inflater.inflate(R.layout.item,parent,false);

                return retView;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView textView = (TextView) view.findViewById(R.id.title);
                TextView listanombre = (TextView) view.findViewById(R.id.listanombre);


                textView.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
                listanombre.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
            }
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       // holder.textView.setText(myData[position]);
        myCursorAdapter.getCursor().moveToPosition(position);
        myCursorAdapter.bindView(holder.itemView,myContext,myCursorAdapter.getCursor());
    }

    @Override
    public int getItemCount() {
        return myCursorAdapter.getCount();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView,listanombre;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.title);
            listanombre = (TextView)itemView.findViewById(R.id.listanombre);
        }
    }
}
