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
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.UUID;

import unibo.btlib.BluetoothChannel;
import unibo.btlib.ConnectionTask;
import unibo.btlib.RealBluetoothChannel;
import unibo.btlib.ConnectToBluetoothServerTask;
import unibo.btlib.BluetoothUtils;
import unibo.smartdumpster.netutils.Http;
import unibo.smartdumpster.utils.C;


public class MainActivity extends AppCompatActivity implements Serializable {

    Button btnConnect;
    Button btnToken;
    private BluetoothChannel btChannel;
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

        timeUpdateHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String time = ((String)msg.obj).split(":")[1];
                ((TextView) findViewById(R.id.txtTempo)).setText("Tempo Rimanente: "+time);
            }
        };

        initUI();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if( btChannel != null)
            btChannel.close();
    }

    private void initUI() {
        btnConnect = findViewById(R.id.btnConnect);
        btnToken = findViewById(R.id.btnToken);

        findViewById(R.id.btnToken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                token = false;
                //richiesta disponibilità da http
                try {
                    requestTokenGet();
                }catch(Exception e) {
                    Toast t = Toast.makeText(getApplicationContext(),"*Error HTTP*\n" + e.getMessage(), Toast.LENGTH_LONG);
                    t.show();
                }

                boolean token = true;
                if(token){
                    btnConnect.setVisibility(View.VISIBLE);
                    btnConnect.setEnabled(true);
                    btnToken.setVisibility(View.VISIBLE);
                    btnToken.setEnabled(false);
                }
            }
        });

        findViewById(R.id.btnConnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    connectToBTServer();
                } catch (Exception e) {
                    Toast t = Toast.makeText(getApplicationContext(),"*Error on Connection*\n" + e.getMessage(), Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });

        findViewById(R.id.btnA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Invio tipo
                    btChannel.sendMessage("A");

                    Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                    intent.putExtra("TYPE", new String[]{"A"});
                    intent.putExtra("BT", btChannel);
                    startActivity(intent);
                }catch (Exception e)
                {
                    Toast t = Toast.makeText(getApplicationContext(),"*Error Onclick button*\n" + e.getMessage(), Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });

        findViewById(R.id.btnB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Invio tipo
                    btChannel.sendMessage("B");

                    Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                    intent.putExtra("TYPE", new String[]{"B"});
                    intent.putExtra("BT", btChannel);
                    startActivity(intent);
                }catch (Exception e){
                    Toast t = Toast.makeText(getApplicationContext(),"*Error Onclick button*\n" + e.getMessage(), Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });

        findViewById(R.id.btnC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Invio tipo
                    btChannel.sendMessage("C");

                    Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                    intent.putExtra("TYPE", new String[]{"C"});
                    intent.putExtra("BT", btChannel);
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

        Toast t = Toast.makeText(getApplicationContext(),"OK", Toast.LENGTH_LONG);
        t.show();

        final UUID uuid = BluetoothUtils.getEmbeddedDeviceDefaultUuid();
        //final UUID uuid = BluetoothUtils.generateUuidFromString(C.bluetooth.BT_SERVER_UUID);

        new ConnectToBluetoothServerTask(serverDevice, uuid, new ConnectionTask.EventListener() {
            @Override
            public void onConnectionActive(final BluetoothChannel channel) {

                ((TextView) findViewById(R.id.statusLabel)).setText("Status : connected to SmartDumpster");


                btChannel = channel;
                btChannel.registerListener(new RealBluetoothChannel.Listener() {
                    @Override
                    public void onMessageReceived(String receivedMessage) {
                        Toast t = Toast.makeText(getApplicationContext(),"*Ricevuto: *\n"+receivedMessage, Toast.LENGTH_LONG);
                        t.show();

                        if(receivedMessage.contains("TIME")){
                            //update timedown
                            Message msg = new Message();
                            msg.obj = receivedMessage;
                            timeUpdateHandler.handleMessage(msg);
                        }
                    }

                    @Override
                    public void onMessageSent(String sentMessage) {
                        if(sentMessage == "DEPOSITED"){
                            try {
                                depositoPost();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Toast t = Toast.makeText(getApplicationContext(),"*Inviato: *\n"+sentMessage, Toast.LENGTH_LONG);
                        t.show();
                    }
                });
            }

            @Override
            public void onConnectionCanceled() {
                ((TextView) findViewById(R.id.statusLabel)).setText("Status : unable to connect");
            }
        }).execute();
    }

    private void depositoGet(){
        final String url = "http://dummy.restapiexample.com/api/v1/employees";

        Http.get(url, response -> {
            if(response.code() == HttpURLConnection.HTTP_OK){
                try {
                    Toast t = Toast.makeText(getApplicationContext(),"*Comunicazione Deposito Avvenuta*\n" + response.contentAsString(), Toast.LENGTH_LONG);
                    t.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Toast t = Toast.makeText(getApplicationContext(),"*WTF*\n", Toast.LENGTH_LONG);
        t.show();
    }

    private void requestTokenGet(){
        final String url = "http://dummy.restapiexample.com/api/v1/employees";

        Http.get(url, response -> {
            if(response.code() == HttpURLConnection.HTTP_OK){
                try{
                    Toast t = Toast.makeText(getApplicationContext(), "*Comunicazione Richiesta Token Avvenuta*\n" + response.contentAsString(), Toast.LENGTH_LONG);
                    t.show();
                    setToken(Boolean.getBoolean(response.contentAsString()));
                }catch (IOException e){

                }
            }
        });
    }

    private void setToken(boolean value){
        token = value;
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
                } catch (Exception e) {
                    Toast t = Toast.makeText(getApplicationContext(),"*Errore HTTP*\n" + e.getMessage(), Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
    }
}
