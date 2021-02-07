package io.github.javaherobrine.utils;

import java.lang.reflect.Array;

public final class ArrayUtils {
    private ArrayUtils() {
    }

    public static <T> T[] merge(T[] arr1, T... arr2) {
        int total = arr1.length + arr2.length;
        T[] result = (T[]) Array.newInstance(arr1[0].getClass(), total);
        for (int i = 0; i < arr1.length; i++) {
            result[i] = arr1[i];
        }
        int now = 0;
        for (int i = arr1.length; i < total; i++) {
            result[now] = arr2[i];
            now++;
        }
        return result;
    }

    public static <T> T[] except(T[] arr, int start, int end) {
        if (end < start) throw new IllegalArgumentException("end can't < start");
        int sub = end - start + 1;
        int total = arr.length - sub;
        T[] result = (T[]) Array.newInstance(arr[0].getClass(), total);
        for (int i = 0; i < total; i++) {
            if (i < start) {
                result[i] = arr[i];
            } else {
                result[i] = arr[i + sub];
            }
        }
        return result;
    }
}
