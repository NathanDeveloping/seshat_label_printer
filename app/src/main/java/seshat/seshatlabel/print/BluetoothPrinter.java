package seshat.seshatlabel.print;
import android.os.Looper;
import com.zebra.sdk.comm.BluetoothConnectionInsecure;
import com.zebra.sdk.comm.Connection;


public class BluetoothPrinter {

    private final static String PRINTER_MAC_ADDRESS = "AC:3F:A4:58:AC:88";

    public void print(final String message) {
        new Thread(new Runnable() {
            String toPrint = message;
            public void run() {
                try {
                    // Instantiate insecure connection for given Bluetooth&reg; MAC Address.
                    Connection thePrinterConn = new BluetoothConnectionInsecure(BluetoothPrinter.PRINTER_MAC_ADDRESS);

                    // Initialize
                    Looper.prepare();

                    // Open the connection - physical connection is established here.
                    thePrinterConn.open();

                    // This example prints "This is a ZPL test." near the top of the label.
                    String zplData = "^XA^FO40,40^A0N,25,25^LS-100^LT^FD" + toPrint + "^FS^XZ";

                    // Send the data to printer as a byte array.
                    thePrinterConn.write(zplData.getBytes());

                    // Make sure the data got to the printer before closing the connection
                    Thread.sleep(500);

                    // Close the insecure connection to release resources.
                    thePrinterConn.close();

                    Looper.myLooper().quit();
                } catch (Exception e) {
                    // Handle communications error here.
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
