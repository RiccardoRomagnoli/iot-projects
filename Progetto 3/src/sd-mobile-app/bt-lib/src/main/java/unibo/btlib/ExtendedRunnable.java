package unibo.btlib;

import java.io.Serializable;

public interface ExtendedRunnable extends Runnable {
    void write(byte[] bytes);
    void cancel();
}
