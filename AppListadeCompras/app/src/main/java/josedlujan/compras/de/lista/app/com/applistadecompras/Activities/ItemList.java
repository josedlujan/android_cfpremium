package josedlujan.compras.de.lista.app.com.applistadecompras.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import josedlujan.compras.de.lista.app.com.applistadecompras.Fragments.ItemsFragment;
import josedlujan.compras.de.lista.app.com.applistadecompras.R;

/**
 * Created by jose on 25/11/15.
 */
public class ItemList extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);


        Bundle intent = getIntent().getExtras();

        if(savedInstanceState == null){

            ItemsFragment fragment = new ItemsFragment();
            fragment.setArguments(intent);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.list_item,fragment)
                    .commit();
        }
    }
}
