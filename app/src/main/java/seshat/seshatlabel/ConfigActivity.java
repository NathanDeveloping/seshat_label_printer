package seshat.seshatlabel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import seshat.seshatlabel.models.Configuration;
import seshat.seshatlabel.models.LabelModel;

public class ConfigActivity extends Activity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration config = Configuration.getInstance();
        setContentView(R.layout.activity_config);
        EditText printerMAC = (EditText)this.findViewById(R.id.printerMACEdit);
        EditText rpiIP = (EditText)this.findViewById(R.id.raspberryIpEdit);
        EditText rpiPORT = (EditText)this.findViewById(R.id.raspberryPortEdit);
        EditText backupIP = (EditText)this.findViewById(R.id.backupIpEdit);
        EditText backupPORT = (EditText)this.findViewById(R.id.backupPortEdit);
        printerMAC.setText(config.getPrinterMAC());
        rpiIP.setText(config.getRpiIP());
        rpiPORT.setText(config.getRpiPORT());
        backupIP.setText(config.getBackupIP());
        backupPORT.setText(config.getBackupPORT());
        Button saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if (v == findViewById(R.id.saveButton))
        {
            Configuration config = Configuration.getInstance();
            EditText printerMAC = (EditText)this.findViewById(R.id.printerMACEdit);
            EditText rpiIP = (EditText)this.findViewById(R.id.raspberryIpEdit);
            EditText rpiPORT = (EditText)this.findViewById(R.id.raspberryPortEdit);
            EditText backupIP = (EditText)this.findViewById(R.id.backupIpEdit);
            EditText backupPORT = (EditText)this.findViewById(R.id.backupPortEdit);
            config.setPrinterMAC(printerMAC.getText().toString());
            config.setRpiIP(rpiIP.getText().toString());
            config.setRpiPORT(rpiPORT.getText().toString());
            config.setBackupIP(backupIP.getText().toString());
            config.setBackupPORT(backupPORT.getText().toString());
            Log.d("ConfigActivity", "Retour home...");
            this.finish();
        }
    }

}
