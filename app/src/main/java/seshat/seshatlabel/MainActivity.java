package seshat.seshatlabel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import seshat.seshatlabel.print.BluetoothPrinter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothPrinter bp = new BluetoothPrinter();
        bp.print("salut salut !");
    }
}
