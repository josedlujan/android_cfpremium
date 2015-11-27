package josedlujan.compras.de.lista.app.com.applistadecompras.Fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;

import josedlujan.compras.de.lista.app.com.applistadecompras.Helpers.DataBaseHelper;
import josedlujan.compras.de.lista.app.com.applistadecompras.R;

/**
 * Created by jose on 25/11/15.
 */
public class ItemsFragment extends Fragment {
    ListView listView;
    TextView additem;
    String lista;
    SimpleCursorAdapter cursorAdapter;
    DataBaseHelper myDBHelper;
    Cursor myCursor;
    String[] from;
    int[] to;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.items_fragment,container,false);

        listView = (ListView) rootView.findViewById(R.id.list_item);
        additem = (TextView) rootView.findViewById(R.id.additem);

        Bundle bundle = getArguments();

        if(bundle != null) {
            boolean restart = bundle.getBoolean("restart");

            if(restart){
                listView.setAdapter(null);
            }else {

                lista = (String) bundle.get("lista");
                DataBaseHelper myDbHelper = new DataBaseHelper(getActivity());

                try {
                    myDbHelper.createDataBase();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {

                    myDbHelper.openDatabase();
                    myCursor = myDbHelper.fetchItemsList(lista);

                    if (myCursor != null) {
                        from = new String[]{"_id", "item"};
                        to = new int[]{R.id.id, R.id.title_item};

                        cursorAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.item_list, myCursor, from, to);
                        listView.setAdapter(cursorAdapter);

                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }


        }

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Click 1",Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Agregar un item");
                final EditText input =new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseHelper myDbHelper = new DataBaseHelper(getActivity().getApplicationContext());
                        SQLiteDatabase db = myDbHelper.getWritableDatabase();
                        ContentValues valores = new ContentValues();
                        valores.put("item",input.getText().toString());

                        db.insert(lista, null, valores);
                        db.close();

                        try {
                            myDbHelper.openDatabase();
                            Cursor cursor = myDbHelper.fetchItemsList(lista);
                            from = new String[]{"_id","item"};
                            to = new int[] {R.id.id,R.id.title_item};
                            cursorAdapter =
                                    new SimpleCursorAdapter(getActivity().getApplicationContext(),R.layout.item_list,cursor,from,to);
                            listView.setAdapter(cursorAdapter);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                builder.show();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),"Click 2",Toast.LENGTH_SHORT).show();

                final TextView ID = (TextView) view.findViewById(R.id.id);

                AlertDialog.Builder adb =new AlertDialog.Builder(getActivity());
                adb.setTitle("Borrar");
                adb.setMessage("¿Seguro deseas eliminr el item?");
                adb.setNegativeButton("Cancelar", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getActivity(), "Si si si", Toast.LENGTH_SHORT).show();
                        myDBHelper = new DataBaseHelper(getActivity().getApplicationContext());

                        SQLiteDatabase db = myDBHelper.getWritableDatabase();
                        String  where ="_id = '"+ID.getText().toString()+"'";
                        db.delete(lista,where,null);
                        db.close();

                        try {
                            myDBHelper.openDatabase();
                            Cursor cursor = myDBHelper.fetchItemsList(lista);
                            from = new String[]{"_id","item"};
                            to = new int[] {R.id.id,R.id.title_item};
                            cursorAdapter =
                                    new SimpleCursorAdapter(getActivity().getApplicationContext(),R.layout.item_list,cursor,from,to);
                            listView.setAdapter(cursorAdapter);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    }
                });
                adb.show();



            }
        });





        return rootView;
    }
}
