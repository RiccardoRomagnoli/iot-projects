package unibo.smartdumpster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import unibo.btlib.BluetoothChannel;
import unibo.btlib.CommChannel;
import unibo.btlib.RealBluetoothChannel;
import unibo.smartdumpster.utils.ChannelUtility;


public class ConfirmActivity extends Activity implements View.OnClickListener {

    TextView label;
    Button btnConferma, btnAnnulla, btnEstendi;
    ProgressBar bar;
    String typeDeposit;
    private BluetoothChannel btChannel;
    Handler timeUpdateHandler;

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
/*        label.setOnEditorActionListener(new TextView.OnEditorActionListener(){
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

        });*/
        btnConferma = findViewById(R.id.btnConferma);
        btnAnnulla = findViewById(R.id.btnAnnulla);
        btnEstendi = findViewById(R.id.btnEstendi);

        btnConferma.setVisibility(View.VISIBLE);
        btnAnnulla.setVisibility(View.VISIBLE);

        btnConferma.setOnClickListener(this);
        btnAnnulla.setOnClickListener(this);
        btnEstendi.setOnClickListener(this);

        timeUpdateHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String time = ((String)msg.obj).split(":")[1];
                ((TextView) findViewById(R.id.txtTempo)).setText("Tempo Rimanente: "+time);
            }
        };

        ChannelUtility.registerListener(new RealBluetoothChannel.Listener() {
            @Override
            public void onMessageReceived(String receivedMessage) {
                //Toast.makeText(getApplicationContext(),"*Ricevuto: *\n"+receivedMessage, Toast.LENGTH_LONG).show();

                if(receivedMessage.contains("TIME")){
                    //update timedown
                    Message msg = new Message();
                    msg.obj = receivedMessage;
                    timeUpdateHandler.handleMessage(msg);
                }
                if(receivedMessage.contains("TIME:0")){
                    finish();
                }
            }

            @Override
            public void onMessageSent(String sentMessage) {
            }
        });
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
