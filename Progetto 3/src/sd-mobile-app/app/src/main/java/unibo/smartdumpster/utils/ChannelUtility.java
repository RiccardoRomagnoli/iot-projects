package unibo.smartdumpster.utils;

import java.util.Optional;

import unibo.btlib.BluetoothChannel;
import unibo.btlib.CommChannel;
import unibo.btlib.RealBluetoothChannel;

public class ChannelUtility {

    private static Optional<BluetoothChannel> btChannel = Optional.empty();

    public static void setChannel(BluetoothChannel channel){
        btChannel = Optional.ofNullable(channel);
    }

    public static void sendMessage(String msg){
        if(btChannel.isPresent()) {
            btChannel.get().sendMessage(msg);
        }
    }

    public static void registerListener(CommChannel.Listener listener){
        if(btChannel.isPresent()){
            btChannel.get().registerListener(listener);
        }
    }
}
