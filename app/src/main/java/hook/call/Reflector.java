package hook.call;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import androidx.annotation.Keep;
/*
 * Created by aantik
 * 6/07/2026 1:10 PM
 *
 *   ⋆    ႔ ႔
 *     ᠸ^ ^ ⸝⸝
 *       |、˜〵
 *      じしˍ,)⁐̤ᐷ
 *
 * Fox Mode 🍺
 */

@Keep
public class Reflector {

    public static class CalibratorStubs {

        public static int stub1() {
            return 1;
        }

        public static int stub2() {
            return 2;
        }
    }

    public static void cloneMethod(Method sourceMethod, Method targetMethod) {
        try {
                                                         ////// Access sun.misc.Unsafe instance
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
            Field artMethodField = Method.class.getSuperclass().getDeclaredField("artMethod");
            Method firstStub = CalibratorStubs.class.getDeclaredMethod("stub1");
            Method secondStub = CalibratorStubs.class.getDeclaredMethod("stub2");
            Method getLongMethod = unsafeClass.getMethod("getLong", Object.class, long.class);
            Method putLongMethod = unsafeClass.getMethod("putLong", long.class, long.class);
            Method addressSizeMethod = unsafeClass.getMethod("addressSize");


            artMethodField.setAccessible(true);
            unsafeField.setAccessible(true);
            Object unsafeInstance = unsafeField.get(null);


            int addressSize = ((Integer) addressSizeMethod.invoke(unsafeInstance)).intValue();
            int pointerStep = addressSize == 8 ? 8 : 4;
            long sourceArtMethod = artMethodField.getLong(sourceMethod);
            long targetArtMethod = artMethodField.getLong(targetMethod);
            long firstStubArtMethod = artMethodField.getLong(firstStub);
            long secondStubArtMethod = artMethodField.getLong(secondStub);
            long artMethodDistance = Math.abs(secondStubArtMethod - firstStubArtMethod);
            long entryPointOffset = artMethodDistance - pointerStep;

            for (long offset = artMethodDistance - pointerStep; offset >= 0; offset -= pointerStep)
            {
                long firstValue = ((Long) getLongMethod.invoke(unsafeInstance, null, Long.valueOf(firstStubArtMethod + offset))).longValue();
                long secondValue = ((Long) getLongMethod.invoke(unsafeInstance, null, Long.valueOf(secondStubArtMethod + offset))).longValue();
                if (firstValue != 0 && secondValue != 0 && firstValue != secondValue)
                {
                    entryPointOffset = offset;
                    break;
                }
            }

            long sourceEntryPoint = ((Long) getLongMethod.invoke(unsafeInstance, null, Long.valueOf(sourceArtMethod + entryPointOffset))).longValue();
            putLongMethod.invoke(unsafeInstance, Long.valueOf(targetArtMethod + entryPointOffset), Long.valueOf(sourceEntryPoint));

        } catch (Throwable error) {
            error.printStackTrace();

        }
    }
}
