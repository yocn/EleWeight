package eleweigh.woxian.com.eleweight.util;

import android.content.Context;

/**
 * Created by Yocn on 16.11.29.
 */

public class MyToast {
    public static void show(Context context, String content) {
        android.widget.Toast.makeText(context, content, android.widget.Toast.LENGTH_SHORT).show();
    }
}
