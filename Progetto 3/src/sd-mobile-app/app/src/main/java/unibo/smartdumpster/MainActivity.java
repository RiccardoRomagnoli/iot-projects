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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.TimerTask;
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

    static String URL = "http://87fbc46d.ngrok.io";
    static long TIMEOUT = 7000;

    Button btnConnect;
    Button btnToken;
    boolean token;
    String type;
    TimerTask timeOutDeposit;
    Timer t;
    Handler timeOutHandler;
    Handler postDeposit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        if(btAdapter != null && !btAdapter.isEnabled()){
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), C.bluetooth.ENABLE_BT_REQUEST);
        }

        initUI();

        timeOutHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                btnToken.setEnabled(true);
                findViewById(R.id.btnA).setEnabled(false);
                findViewById(R.id.btnB).setEnabled(false);
                findViewById(R.id.btnC).setEnabled(false);
                findViewById(R.id.progress).setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"*TimeOut - Nessuna risposta del Controller*", Toast.LENGTH_LONG).show();
                releaseToken();
            }
        };
    }

    private void resetTimer(){
        t = new Timer();
        timeOutDeposit = new TimerTask() {
            @Override
            public void run() {
                //Edit gui on main thread
                timeOutHandler.obtainMessage(1).sendToTarget();
            }
        };
        t.schedule(timeOutDeposit, TIMEOUT);
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
                    type = "A";

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
                    type = "B";

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
                    type = "C";

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
                            releaseToken();
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
                            //Se non ricevo conferma dal controller resetto la gui
                            resetTimer();
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
                Toast.makeText(getApplicationContext(),"*Retry!*", Toast.LENGTH_LONG).show();
            }
        }).execute();
    }

    private void releaseToken(){
        final String url = URL+"/api/releaseToken";
        Http.get(url, response -> {
            Toast.makeText(getApplicationContext(), "*Token Rilasciato*\n", Toast.LENGTH_LONG).show();
        });
    }

    private void requestTokenGet(){
        final String url = URL+"/api/token";
        Http.get(url, response -> {
            try {
                if (response != null) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        try {
                            JSONArray arr = new JSONArray(response.contentAsString());
                            boolean value = Boolean.parseBoolean(arr.getJSONObject(0).getString("value"));

                            if(value) {
                                Toast.makeText(getApplicationContext(), "*Token Assegnato*", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "*Token Non Assegnato*", Toast.LENGTH_LONG).show();
                            }
                            setToken(value);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "*Errore nella Comunicazione*\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "*Errore HTTP - Server Attivo?*\n", Toast.LENGTH_LONG).show();
                }
            }catch(Exception e){
                Toast.makeText(getApplicationContext(), "*Errore Generico*\n", Toast.LENGTH_LONG).show();
            }
        });
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
        findViewById(R.id.progress).setVisibility(View.GONE);
    }

    private void depositoPost() throws JSONException {

        final String url = URL+"/api/dodeposit";
        String date = java.time.LocalDate.now().toString();
        final String content = new JSONObject().put("type", type)
                                               .put("date", date)
                                               .toString();

        Http.post(url, content.getBytes(), response -> {
            try {
                if(response != null) {
                    int code = response.code();
                    if (code == HttpURLConnection.HTTP_OK) {
                        try {

                            Toast.makeText(getApplicationContext(), "*Comunicazione Deposito Avvenuta*\n" + response.contentAsString(), Toast.LENGTH_LONG).show();
                            resetUI();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "*Errore HTTP*\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    if (code != HttpURLConnection.HTTP_OK) {
                        try {
                            Toast.makeText(getApplicationContext(), "*Errore Comunicazione Deposito - Riprova*", Toast.LENGTH_LONG).show();
                            ((Button) findViewById(R.id.btnA)).setEnabled(true);
                            ((Button) findViewById(R.id.btnB)).setEnabled(true);
                            ((Button) findViewById(R.id.btnC)).setEnabled(true);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "*Errore HTTP*\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "*Errore HTTP - Server Attivo?*\n", Toast.LENGTH_LONG).show();
                }
                findViewById(R.id.progress).setVisibility(View.GONE);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(), "*Errore HTTP*\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        t.cancel();
    }
}
