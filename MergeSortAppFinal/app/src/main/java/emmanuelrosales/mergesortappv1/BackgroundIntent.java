package emmanuelrosales.mergesortappv1;

/**
 * Created by Emmanuel on 30-Apr-15.
 */

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class BackgroundIntent extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "BackgroundIntent";

    public BackgroundIntent() {
        super(BackgroundIntent.class.getName());
    }


    static final int MAX_VALUE = 2000000;
    int data[] = new int[MAX_VALUE];


    public void mergeApp(){
        mergeSort(data);
    }

    public static void mergeSort( int a[ ]){
        int tmpArray[] = new int[ a.length ];

        mergeSort( a, tmpArray, 0, a.length - 1 );
    }
    private static void mergeSort( int a[ ], int tmpArray[],int left, int right ){
        if( left < right )
        {
            int center = ( left + right ) / 2;
            mergeSort( a, tmpArray, left, center );
            mergeSort( a, tmpArray, center + 1, right );
            merge( a, tmpArray, left, center + 1, right );
        }
    }

    private static void merge( int a[ ],int tmpArray[],int leftPos, int rightPos, int rightEnd){
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos + 1;

        // Main loop
        while( leftPos <= leftEnd && rightPos <= rightEnd ){
            if( a[ leftPos ]<( a[ rightPos ] ) ){
                tmpArray[ tmpPos++ ] = a[ leftPos++ ];
            }
            else{
                tmpArray[ tmpPos++ ] = a[ rightPos++ ];
            }
        }
        while( leftPos <= leftEnd ){    // Copy rest of first half
            tmpArray[ tmpPos++ ] = a[ leftPos++ ];
        }
        while( rightPos <= rightEnd ){  // Copy rest of right half
            tmpArray[ tmpPos++ ] = a[ rightPos++ ];
        }
        // Copy TmpArray back
        for( int i = 0; i < numElements; i++, rightEnd-- ){
            a[ rightEnd ] = tmpArray[ rightEnd ];
        }
    }


    public void generarNuevoArreglo() {
        Random random = new Random();

        for (int i = 0; i < data.length; i++){
            data[i] = random.nextInt(MAX_VALUE);
        } }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "Service Started!");

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");

        Bundle bundle = new Bundle();

/* Service Started */
        receiver.send(STATUS_RUNNING, Bundle.EMPTY);
        try {

            long start = System.nanoTime();
            generarNuevoArreglo();
            for(int i = 0; i < 200;i++){
                mergeApp();
            }

            long end = System.nanoTime();

            long elapsedTime = end - start;
            String res = "trascurrieron: " + elapsedTime + "nano seconds\n" +
                    "lo cual es " + TimeUnit.NANOSECONDS.toSeconds(elapsedTime) + " segundos";

            bundle.putString("exectime",res);
        }
        catch (Error err)
        {
            bundle.putString(Intent.EXTRA_TEXT, err.getMessage());
            receiver.send(STATUS_ERROR, bundle);
        }

       /* Status Finished */
        receiver.send(STATUS_FINISHED, bundle);
        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }
}