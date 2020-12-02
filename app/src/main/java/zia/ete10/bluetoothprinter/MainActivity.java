package zia.ete10.bluetoothprinter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.printable.ImagePrintable;
import com.mazenrashed.printooth.data.printable.Printable;
import com.mazenrashed.printooth.data.printable.RawPrintable;
import com.mazenrashed.printooth.data.printable.TextPrintable;
import com.mazenrashed.printooth.data.printer.DefaultPrinter;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;
import com.mazenrashed.printooth.utilities.PrintingCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

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
        changePairAndUnpair();
    }

    private void printText() {
        ArrayList<Printable> printables = new ArrayList<>();
        printables.add(new RawPrintable.Builder(new byte[]{27,100,4}).build());

        // add text
        printables.add(new TextPrintable.Builder()
                .setText("Hello World")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());

        //custom text
        printables.add(new TextPrintable.Builder()
        .setText("Hello World")
        .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_60())
        .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
        .setEmphasizedMode(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
        .setNewLinesAfter(1)
        .build());
        //printing.print(printables);
        Printooth.INSTANCE.printer().print(printables);

    }
    private void printImage() {
        ArrayList<Printable> printables =new ArrayList<>();

        //Load image from internet
        // remember size matter
        Picasso.get().load("https://via.placeholder.com/120/09f/fff.png")
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        printables.add(new ImagePrintable.Builder(bitmap).build());

                       Printooth.INSTANCE.printer().print(printables);

                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });


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
        Toast.makeText(this, "Connecting to printer", Toast.LENGTH_SHORT).show();
        

    }

    @Override
    public void connectionFailed(String s) {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(String s) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMessage(String s) {
        Toast.makeText(this, "s", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void printingOrderSentSuccessfully() {
        Toast.makeText(this, "Order sent to printer", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ScanningActivity.SCANNING_FOR_PRINTER && requestCode == Activity.RESULT_OK)
            initPrinting();
        changePairAndUnpair();
    }

    private void initPrinting() {
        if (!Printooth.INSTANCE.hasPairedPrinter())
            printing =Printooth.INSTANCE.printer();
        if (printing != null)
            printing.setPrintingCallback(this);

    }
}