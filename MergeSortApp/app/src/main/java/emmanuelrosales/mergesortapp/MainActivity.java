package emmanuelrosales.mergesortapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

import emmanuelrosales.mergesortapp.MergeSort;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        View rootView;
        String resultado;
        Integer[] a;

        public PlaceholderFragment() {
        }

        public static Integer[] listaNum(int desde, int hasta, int tam){
            Integer[] numeros = new Integer [tam];
            Random rnd = new Random();
            for (int i = 0; i < numeros.length; i++) {
                numeros[i] = rnd.nextInt(hasta - desde + 1) + desde;
            }
            return numeros;
        }

        public void generarArreglo(){
         a = listaNum(0,100,20);

        }
        public void merge(){
            MergeSort merge = new MergeSort();

            resultado = merge.main(a);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            Button btn = (Button)rootView.findViewById(R.id.btnGenerar);
            Button btn2 = (Button)rootView.findViewById(R.id.btnOrdenar);
            btn2.setOnClickListener(this);
            btn.setOnClickListener(this);
            return rootView;
        }
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btnOrdenar:
                    merge();
                   ((TextView)rootView.findViewById(R.id.textView2)).setText(resultado.toString());
                break;
                case R.id.btnGenerar:
                    generarArreglo();
                    ((TextView)rootView.findViewById(R.id.textView)).setText(Arrays.toString(a));



            }

        }
    }
}
