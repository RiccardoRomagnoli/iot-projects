package unibo.smartdumpster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;

import unibo.btlib.BluetoothChannel;
import unibo.smartdumpster.utils.ChannelUtility;

/**
 * Created by Riccardo on 11/04/2017.
 */

public class ConfirmActivity extends Activity implements View.OnClickListener, Serializable {

    TextView label;
    Button btnConferma, btnAnnulla, btnEstendi;
    ProgressBar bar;
    String typeDeposit;
    private BluetoothChannel btChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent ii = getIntent();
        this.setFinishOnTouchOutside(false);
        setTitle("Conferma Deposito");

        setContentView(R.layout.activity_confirm);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


/*        typeDeposit = ii.getStringArrayExtra("TYPE");
        btChannel = (BluetoothChannel) ii.getSerializableExtra("BT");*/

        Bundle bundle = ii.getExtras();

        typeDeposit = bundle.getString("TYPE");

        label = findViewById(R.id.txtTempo);
        label.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView t, int i, KeyEvent k){
                if(t.toString().contains("0")){
                    finish();
                    findViewById(R.id.btnToken).setEnabled(true);
                    findViewById(R.id.btnA).setEnabled(false);
                    findViewById(R.id.btnB).setEnabled(false);
                    findViewById(R.id.btnC).setEnabled(false);
                }
                return false;
            }

        });
        btnConferma = findViewById(R.id.btnConferma);
        btnAnnulla = findViewById(R.id.btnAnnulla);
        btnEstendi = findViewById(R.id.btnEstendi);
        bar = findViewById(R.id.progress);

        bar.setVisibility(View.GONE);
        btnConferma.setVisibility(View.VISIBLE);
        btnAnnulla.setVisibility(View.VISIBLE);

        btnConferma.setOnClickListener(this);
        btnAnnulla.setOnClickListener(this);
        btnEstendi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()) {
                case R.id.btnConferma:
                    //Avvenuto deposito
                    ChannelUtility.sendMessage("DEPOSITED");
                    finish();
                    break;
                case R.id.btnEstendi:
                    //richiesta estensione
                    ChannelUtility.sendMessage("EXTEND");
                    break;
                case R.id.btnAnnulla:
                    //Comunico annullamento operazione
                    ChannelUtility.sendMessage("BACK");
                    finish();
                    break;
            }
        }catch (Exception e){}
    }
}
