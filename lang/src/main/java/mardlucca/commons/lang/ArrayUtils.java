/*
 * File: ArrayUtils.java
 *
 * Copyright 2019 Marcio D. Lucca
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mardlucca.commons.lang;

public class ArrayUtils {
    public static boolean[] toPrimitiveArray(Boolean[] aInArray) {
        if (aInArray == null) { return null; }
        boolean[] lResult = new boolean[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static byte[] toPrimitiveArray(Byte[] aInArray) {
        if (aInArray == null) { return null; }
        byte[] lResult = new byte[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static char[] toPrimitiveArray(Character[] aInArray) {
        if (aInArray == null) { return null; }
        char[] lResult = new char[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static double[] toPrimitiveArray(Double[] aInArray) {
        if (aInArray == null) { return null; }
        double[] lResult = new double[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static float[] toPrimitiveArray(Float[] aInArray) {
        if (aInArray == null) { return null; }
        float[] lResult = new float[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static int[] toPrimitiveArray(Integer[] aInArray) {
        if (aInArray == null) { return null; }
        int[] lResult = new int[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static long[] toPrimitiveArray(Long[] aInArray) {
        if (aInArray == null) { return null; }
        long[] lResult = new long[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static short[] toPrimitiveArray(Short[] aInArray) {
        if (aInArray == null) { return null; }
        short[] lResult = new short[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static Boolean[] toWrapperArray(boolean[] aInArray) {
        if (aInArray == null) { return null; }
        Boolean[] lResult = new Boolean[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static Byte[] toWrapperArray(byte[] aInArray) {
        if (aInArray == null) { return null; }
        Byte[] lResult = new Byte[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static Character[] toWrapperArray(char[] aInArray) {
        if (aInArray == null) { return null; }
        Character[] lResult = new Character[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static Double[] toWrapperArray(double[] aInArray) {
        if (aInArray == null) { return null; }
        Double[] lResult = new Double[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static Float[] toWrapperArray(float[] aInArray) {
        if (aInArray == null) { return null; }
        Float[] lResult = new Float[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static Integer[] toWrapperArray(int[] aInArray) {
        if (aInArray == null) { return null; }
        Integer[] lResult = new Integer[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static Long[] toWrapperArray(long[] aInArray) {
        if (aInArray == null) { return null; }
        Long[] lResult = new Long[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }

    public static Short[] toWrapperArray(short[] aInArray) {
        if (aInArray == null) { return null; }
        Short[] lResult = new Short[aInArray.length];
        for (int i = 0; i < aInArray.length; i++) {
            lResult[i] = aInArray[i];
        }
        return lResult;
    }
}
