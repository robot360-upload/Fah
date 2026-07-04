package hook.call;

import android.app.Activity;
import android.util.Log;
import com.sunmiscunsafe.isVip;
/*
 * Created by aantik
 * 6/07/2026 1:20 PM
 *
 *   ⋆    ႔ ႔
 *     ᠸ^ ^ ⸝⸝
 *       |、˜〵
 *      じしˍ,)⁐̤ᐷ
 *
 * Fox Mode 🍺
 */

public class Hooker {

    public static final String RevDex = "ANTIK";

    // hook for fake vip 🍻🍺
    public static boolean isFakeVip() {
        return true;
    }

    public static void onMethodCalled(Activity activity) {

        try {

            java.lang.reflect.Method source = Hooker.class.getDeclaredMethod("isFakeVip");
            java.lang.reflect.Method target = isVip.class.getDeclaredMethod("isVip");
            Reflector.cloneMethod(source, target);

        } catch (Exception e) {
            Log.d(RevDex, "onMethodCalled: " + e.getMessage());;
        }

    }
}

