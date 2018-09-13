package com.meizu.cloud.pushsdk.base;

import java.nio.charset.Charset;

class EncryptBase64 {
    private static final char[] base64_table = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final char last2byte = ((char) Integer.parseInt("00000011", 2));
    private static final char last4byte = ((char) Integer.parseInt("00001111", 2));
    private static final char last6byte = ((char) Integer.parseInt("00111111", 2));
    private char[] mBase64Table;
    private int offset = 0;
    private String private_key;

    public EncryptBase64(String key) {
        this.private_key = key;
        initPrivateTable();
    }

    public String encode(byte[] contents) {
        if (contents == null || contents.length == 0) {
            return null;
        }
        int i;
        StringBuilder _sb = new StringBuilder(((contents.length + 2) / 3) * 4);
        int len = contents.length;
        int i2 = 0;
        while (i2 < len) {
            i = i2 + 1;
            int b1 = contents[i2] & 255;
            if (i == len) {
                _sb.append(this.mBase64Table[b1 >>> 2]);
                _sb.append(this.mBase64Table[(last2byte & b1) << 4]);
                _sb.append("==");
                break;
            }
            i2 = i + 1;
            int b2 = contents[i] & 255;
            if (i2 == len) {
                _sb.append(this.mBase64Table[b1 >>> 2]);
                _sb.append(this.mBase64Table[((last2byte & b1) << 4) | (b2 >>> 4)]);
                _sb.append(this.mBase64Table[(last4byte & b2) << 2]);
                _sb.append("=");
                i = i2;
                break;
            }
            i = i2 + 1;
            int b3 = contents[i2] & 255;
            _sb.append(this.mBase64Table[b1 >>> 2]);
            _sb.append(this.mBase64Table[((last2byte & b1) << 4) | (b2 >>> 4)]);
            _sb.append(this.mBase64Table[((last4byte & b2) << 2) | (b3 >>> 6)]);
            _sb.append(this.mBase64Table[last6byte & b3]);
            i2 = i;
        }
        i = i2;
        return _sb.toString();
    }

    public byte[] decode(byte[] data, int len) {
        StringBuilder _sb = new StringBuilder((len * 3) / 4);
        int i = 0;
        int[] b = new int[4];
        while (i < len) {
            int j = 0;
            int i2 = i;
            while (j < 4) {
                i = i2 + 1;
                b[j] = base64_to_256(data[i2]);
                if (b[j] == 64) {
                }
                j++;
                i2 = i;
            }
            _sb.append((char) ((b[0] << 2) | (b[1] >>> 4)));
            if (b[2] != 64) {
                _sb.append((char) (((b[1] & last4byte) << 4) | (b[2] >>> 2)));
            }
            if (b[3] != 64) {
                _sb.append((char) (((b[2] & last2byte) << 6) | b[3]));
                i = i2;
            } else {
                i = i2;
            }
        }
        return _sb.toString().getBytes(Charset.forName("ISO8859-1"));
    }

    private void initPrivateTable() {
        char[] private_base64_table = new char[base64_table.length];
        this.offset = this.private_key.charAt(0) % 13;
        for (int i = 0; i < base64_table.length; i++) {
            private_base64_table[i] = base64_table[(this.offset + i) % base64_table.length];
        }
        this.mBase64Table = private_base64_table;
    }

    private int base64_to_256(byte base64) {
        if (base64 >= (byte) 65 && base64 <= (byte) 90) {
            return ((base64 - 65) + (base64_table.length - this.offset)) % base64_table.length;
        }
        if (base64 >= (byte) 97 && base64 <= (byte) 122) {
            return (((base64 - 97) + 26) + (base64_table.length - this.offset)) % base64_table.length;
        }
        if (base64 >= (byte) 48 && base64 <= (byte) 57) {
            return (((base64 - 48) + 52) + (base64_table.length - this.offset)) % base64_table.length;
        }
        if (base64 == (byte) 43) {
            return ((base64_table.length - this.offset) + 62) % base64_table.length;
        }
        if (base64 == (byte) 47) {
            return ((base64_table.length - this.offset) + 63) % base64_table.length;
        }
        return 64;
    }
}
