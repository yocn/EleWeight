package eleweigh.woxian.com.eleweight.util;

/**
 * Created by Yocn on 16.11.28.
 */

public abstract class RequestCallback {
    public abstract void success(Object o);

    public abstract void fail(int code, String msg);
}
