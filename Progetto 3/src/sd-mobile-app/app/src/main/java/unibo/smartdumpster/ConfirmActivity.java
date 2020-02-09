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

/**
 * Created by Riccardo on 11/04/2017.
 */

public class ConfirmActivity extends Activity implements View.OnClickListener, Serializable {

    TextView label;
    Button btnConferma, btnAnnulla, btnEstendi;
    ProgressBar bar;
    String typeDeposit[];
    private BluetoothChannel btChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent ii = getIntent();
        typeDeposit = ii.getStringArrayExtra("TYPE");
        btChannel = (BluetoothChannel) ii.getSerializableExtra("BT");
        this.setFinishOnTouchOutside(false);
        setTitle("Conferma Deposito");

        setContentView(R.layout.activity_confirm);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        label = findViewById(R.id.txtTempo);
        label.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView t, int i, KeyEvent k){
                if(t.toString().contains("0")){
                    finish();
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
                    btChannel.sendMessage("DEPOSITED");
                    Toast t = Toast.makeText(getApplicationContext(),"*Confermato*\n"+typeDeposit[0], Toast.LENGTH_LONG);
                    t.show();
                    break;
                case R.id.btnEstendi:
                    //richiesta estensione
                    btChannel.sendMessage("ESTENDI");
                    t = Toast.makeText(getApplicationContext(), "*Esteso*\n", Toast.LENGTH_LONG);
                    t.show();
                    break;
                case R.id.btnAnnulla:
                    finish();
                    break;
            }
        }catch (Exception e){}
    }
}
