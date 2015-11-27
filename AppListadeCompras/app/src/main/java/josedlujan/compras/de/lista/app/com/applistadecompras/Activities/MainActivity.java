package josedlujan.compras.de.lista.app.com.applistadecompras.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.io.InputStream;

import josedlujan.compras.de.lista.app.com.applistadecompras.Fragments.ItemsFragment;
import josedlujan.compras.de.lista.app.com.applistadecompras.Fragments.ListaFragment;
import josedlujan.compras.de.lista.app.com.applistadecompras.R;

public class MainActivity extends AppCompatActivity implements ListaFragment.CallBacks, ListaFragment.Refresh{
    public static boolean mTwoPane;
    private static  final String ELEMENTS_TAG = "ELEMENTS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.list_item) != null ){
            mTwoPane =true;

            if(savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_item,new ItemsFragment(), ELEMENTS_TAG)
                        .commit();
            }else {
                mTwoPane = false;
            }
        }
    }

    public boolean getmTwoPane(){
        return this.mTwoPane;
    }

    @Override
    public void onItemSelected(String nombrelista, String lista) {
        if(mTwoPane){
            Bundle bundle = new Bundle();
            bundle.putString("nombrelista",nombrelista);
            bundle.putString("lista", lista);
            ItemsFragment itemsFragment = new ItemsFragment();
            itemsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.list_item,itemsFragment).
                    commit();
        }
        else{
            Intent intent = new Intent(this,ItemList.class);
            intent.putExtra("nombrelista",nombrelista);
            intent.putExtra("lista",lista);
            startActivity(intent);
        }
    }

    @Override
    public void refreshList() {
        Bundle bundle = new Bundle();

        ItemsFragment itemsFragment = new ItemsFragment();

        bundle.putBoolean("restart",true);
        itemsFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.list_item,itemsFragment).
                commit();
    }
}
