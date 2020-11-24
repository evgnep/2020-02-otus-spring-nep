package ru.otus.home28.util;

public class Util {
    @SafeVarargs
    public static <T> boolean oneOf(T what, T... items) {
        for (var i : items)
            if (what.equals(i))
                return true;
        return false;
    }

    public static byte highPart(byte b) {
        return (byte) ((b >> 4) & 0xF);
    }

    public static byte lowPart(byte b) {
        return (byte) (b & 0xF);
    }

    public static boolean testBit(int mask, int no) {
        return (mask & 1 << no) != 0;
    }

    public static boolean testBit(long mask, int no) {
        return (mask & (1L << no)) != 0;
    }
}
