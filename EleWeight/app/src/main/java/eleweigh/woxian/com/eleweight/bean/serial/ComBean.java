package eleweigh.woxian.com.eleweight.bean.serial;

import java.text.SimpleDateFormat;

/**
 * @author 串口数据
 */
public class ComBean {
    public byte[] bRec = null;
    public String sRecTime = "";
    public String sComPort = "";

    public ComBean(String sPort, byte[] buffer, int size) {
        sComPort = sPort;
        bRec = new byte[size];
        for (int i = 0; i < size; i++) {
            bRec[i] = buffer[i];
        }
        SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm:ss");
        sRecTime = sDateFormat.format(new java.util.Date());
    }
}