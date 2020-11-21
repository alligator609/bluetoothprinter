package zia.ete10.bluetoothprinter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;
import com.mazenrashed.printooth.utilities.PrintingCallback;

public class MainActivity extends AppCompatActivity implements PrintingCallback {
    Printing printing;
    Button btnText, imagebtn, unpairbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }
    private void initView(){
        btnText=findViewById(R.id.PrintHello);
        imagebtn=findViewById(R.id.PrintImage);
        unpairbtn=findViewById(R.id.Unpair);
        if (printing !=null){
            printing.setPrintingCallback(this);
        }
        // Event
        unpairbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Printooth.INSTANCE.hasPairedPrinter()){
                    Printooth.INSTANCE.removeCurrentPrinter();
                }
                else {
                    startActivityForResult(new Intent(MainActivity.this, ScanningActivity.class),ScanningActivity.SCANNING_FOR_PRINTER);
                    changePairAndUnpair();
                }
            }
        });
        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Printooth.INSTANCE.hasPairedPrinter()){
                    startActivityForResult((new Intent(MainActivity.this,ScanningActivity.class)),ScanningActivity.SCANNING_FOR_PRINTER);
                }
                else {
                    printImage();
                }
            }


        });
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Printooth.INSTANCE.hasPairedPrinter()){
                    startActivityForResult((new Intent(MainActivity.this,ScanningActivity.class)),ScanningActivity.SCANNING_FOR_PRINTER);
                }
                else {
                    printText();
                }
            }



        });
    }

    private void printImage() {
    }
    private void printText() {
    }

    private void changePairAndUnpair() {
        if (Printooth.INSTANCE.hasPairedPrinter()){
            unpairbtn.setText(new StringBuilder("Unpair").append(Printooth.INSTANCE.getPairedPrinter().getName().toString()) );
        }
        else{
            unpairbtn.setText("Pair with printer");

        }
    }

    public void connectingWithPrinter(){

    }

    @Override
    public void connectionFailed(String s) {

    }

    @Override
    public void onError(String s) {

    }

    @Override
    public void onMessage(String s) {

    }

    @Override
    public void printingOrderSentSuccessfully() {

    }
}