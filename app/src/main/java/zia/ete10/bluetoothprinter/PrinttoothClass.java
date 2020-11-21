package zia.ete10.bluetoothprinter;

import android.app.Application;

import com.mazenrashed.printooth.Printooth;

public class PrinttoothClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Printooth.INSTANCE.init(this);    }
}
