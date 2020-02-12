package unibo.smartdumpster;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.UUID;

import unibo.btlib.BluetoothChannel;
import unibo.btlib.ConnectionTask;
import unibo.btlib.RealBluetoothChannel;
import unibo.btlib.ConnectToBluetoothServerTask;
import unibo.btlib.BluetoothUtils;
import unibo.smartdumpster.netutils.Http;
import unibo.smartdumpster.utils.C;
import unibo.smartdumpster.utils.ChannelUtility;


public class MainActivity extends AppCompatActivity {

    Button btnConnect;
    Button btnToken;
    private boolean token;
    Handler timeUpdateHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        if(btAdapter != null && !btAdapter.isEnabled()){
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), C.bluetooth.ENABLE_BT_REQUEST);
        }

        initUI();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Se esco dalla app lo smartdumpster è disponibile alla connessione da altri devices
        ChannelUtility.close();
        btnConnect.setEnabled(true);
        ((TextView) findViewById(R.id.statusLabel)).setText("Status : Not Connected");

        //rilascio token e abilito btntocken e blocco i tre pulsanti A B C
        resetUI();
    }

    private void initUI() {
        btnConnect = findViewById(R.id.btnConnect);
        btnToken = findViewById(R.id.btnToken);

        findViewById(R.id.btnA).setEnabled(false);
        findViewById(R.id.btnB).setEnabled(false);
        findViewById(R.id.btnC).setEnabled(false);

        ((TextView) findViewById(R.id.statusLabel)).setText("Status : Not Connected");

        findViewById(R.id.btnToken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                token = false;
                //richiesta disponibilità da http
                try {
                    requestTokenGet();
                }catch(Exception e) {
                    Toast.makeText(getApplicationContext(),"*Error HTTP*\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.btnConnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    connectToBTServer();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"*Error on Connection*\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.btnA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Invio tipo
                    ChannelUtility.sendMessage("A");

                    Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("TYPE", "A");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"*Error Onclick button*\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.btnB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Invio tipo
                    ChannelUtility.sendMessage("B");

                    Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("TYPE", "B");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"*Error Onclick button*\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.btnC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Invio tipo
                    ChannelUtility.sendMessage("C");

                    Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("TYPE", "C");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }catch (Exception e){
                    Toast t = Toast.makeText(getApplicationContext(),"*Error Onclick button*\n" + e.getMessage(), Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }

    private void connectToBTServer() throws Exception {
        final BluetoothDevice serverDevice = BluetoothUtils.getPairedDeviceByName(C.bluetooth.BT_DEVICE_ACTING_AS_SERVER_NAME);

        final UUID uuid = BluetoothUtils.getEmbeddedDeviceDefaultUuid();

        new ConnectToBluetoothServerTask(serverDevice, uuid, new ConnectionTask.EventListener() {
            @Override
            public void onConnectionActive(final BluetoothChannel channel) {

                ((TextView) findViewById(R.id.statusLabel)).setText("Status : Connected to SmartDumpster");
                btnConnect.setEnabled(false);
                if(!btnToken.isEnabled()) {
                    findViewById(R.id.btnA).setEnabled(true);
                    findViewById(R.id.btnB).setEnabled(true);
                    findViewById(R.id.btnC).setEnabled(true);
                }

                ChannelUtility.setChannel(channel);
                ChannelUtility.registerListener(new RealBluetoothChannel.Listener() {
                    @Override
                    public void onMessageReceived(String receivedMessage) {
                        //Toast.makeText(getApplicationContext(),"*Ricevuto: *\n"+receivedMessage, Toast.LENGTH_LONG).show();

                        if(receivedMessage.contains("TIME:0")){
                            findViewById(R.id.btnToken).setEnabled(true);
                            findViewById(R.id.btnA).setEnabled(false);
                            findViewById(R.id.btnB).setEnabled(false);
                            findViewById(R.id.btnC).setEnabled(false);
                        }

                        //reset gui e invio conferma al sd-service dopo aver ricevuto conferma dal controller
                        if (receivedMessage.contains("DEPOSITED")) {
                            try {
                                depositoPost();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onMessageSent(String sentMessage) {
                        if(sentMessage.equals("DEPOSITED")){
                            //block GUI
                            findViewById(R.id.progress).setVisibility(View.VISIBLE);
                            findViewById(R.id.btnA).setEnabled(false);
                            findViewById(R.id.btnB).setEnabled(false);
                            findViewById(R.id.btnC).setEnabled(false);
                        }
                        //Toast.makeText(getApplicationContext(), "*Inviato: *\n" + sentMessage, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onConnectionCanceled() {
                ((TextView) findViewById(R.id.statusLabel)).setText("Status : Unable to Connect");
            }
        }).execute();
    }

    private void requestTokenGet(){
        final String url = "http://dummy.restapiexample.com/api/v1/employees";
        try{
            Http.get(url, response -> {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    try{
                        Toast.makeText(getApplicationContext(), "*Comunicazione Richiesta Token Avvenuta*\n" + response.contentAsString(), Toast.LENGTH_LONG).show();
                        //setToken(Boolean.getBoolean(response.contentAsString()));
                        setToken(true);
                    }catch (IOException e){
                        Toast.makeText(getApplicationContext(), "*Comunicazione Richiesta Token ERRORE*\n", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "*ERRORE HTTP*\n", Toast.LENGTH_LONG).show();
        }
    }

    private void setToken(boolean value){
        if(value) {
            btnToken.setEnabled(false);
            if(!btnConnect.isEnabled()) {
                findViewById(R.id.btnA).setEnabled(true);
                findViewById(R.id.btnB).setEnabled(true);
                findViewById(R.id.btnC).setEnabled(true);
            }
        }
    }

    private void resetUI(){
        btnToken.setEnabled(true);
        findViewById(R.id.btnA).setEnabled(false);
        findViewById(R.id.btnB).setEnabled(false);
        findViewById(R.id.btnC).setEnabled(false);
    }

    private void depositoPost() throws JSONException {

        final String url = "http://dummy.restapiexample.com/api/v1/create";
        final String content = new JSONObject().put("id","value")
                                               .put("id","value")
                                               .toString();

        Http.post(url, content.getBytes(), response -> {
            if(response.code() == HttpURLConnection.HTTP_OK){
                try {
                    Toast t = Toast.makeText(getApplicationContext(),"*Comunicazione Deposito Avvenuta*\n" + response.contentAsString(), Toast.LENGTH_LONG);
                    t.show();
                    resetUI();
                } catch (Exception e) {
                    Toast t = Toast.makeText(getApplicationContext(),"*Errore HTTP*\n" + e.getMessage(), Toast.LENGTH_LONG);
                    t.show();
                }
            }else{
                Toast t = Toast.makeText(getApplicationContext(),"*Errore Comunicazione Deposito - Riprova*", Toast.LENGTH_LONG);
                t.show();
                findViewById(R.id.btnA).setEnabled(true);
                findViewById(R.id.btnB).setEnabled(true);
                findViewById(R.id.btnC).setEnabled(true);
            }
            findViewById(R.id.progress).setVisibility(View.GONE);
        });
    }
}
