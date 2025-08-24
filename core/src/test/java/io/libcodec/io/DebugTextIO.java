package io.libcodec.io;

public class DebugTextIO {
    public static void main(String[] args) {
        byte[] buf = new byte[20];
        int result = TextIO.writeInt(buf, 0, Integer.MIN_VALUE);
        System.out.println("Result: " + result);
        System.out.print("Buffer: ");
        for (int i = 0; i < buf.length; i++) {
            if (buf[i] == 0) {
                System.out.print("0 ");
            } else {
                System.out.print((char) buf[i] + " ");
            }
        }
        System.out.println();
    }
}
