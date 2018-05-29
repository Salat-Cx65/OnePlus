package com.oneplus.lib.preference;

public class CharSequences {

    static class AnonymousClass_1 implements CharSequence {
        final /* synthetic */ byte[] val$bytes;

        AnonymousClass_1(byte[] bArr) {
            this.val$bytes = bArr;
        }

        public char charAt(int index) {
            return (char) this.val$bytes[index];
        }

        public int length() {
            return this.val$bytes.length;
        }

        public CharSequence subSequence(int start, int end) {
            return CharSequences.forAsciiBytes(this.val$bytes, start, end);
        }

        public String toString() {
            return new String(this.val$bytes);
        }
    }

    static class AnonymousClass_2 implements CharSequence {
        final /* synthetic */ byte[] val$bytes;
        final /* synthetic */ int val$end;
        final /* synthetic */ int val$start;

        AnonymousClass_2(byte[] bArr, int i, int i2) {
            this.val$bytes = bArr;
            this.val$start = i;
            this.val$end = i2;
        }

        public char charAt(int index) {
            return (char) this.val$bytes[this.val$start + index];
        }

        public int length() {
            return this.val$end - this.val$start;
        }

        public CharSequence subSequence(int newStart, int newEnd) {
            newStart -= this.val$start;
            newEnd -= this.val$start;
            CharSequences.validate(newStart, newEnd, length());
            return CharSequences.forAsciiBytes(this.val$bytes, newStart, newEnd);
        }

        public String toString() {
            return new String(this.val$bytes, this.val$start, length());
        }
    }

    public static CharSequence forAsciiBytes(byte[] bytes) {
        return new AnonymousClass_1(bytes);
    }

    public static CharSequence forAsciiBytes(byte[] bytes, int start, int end) {
        validate(start, end, bytes.length);
        return new AnonymousClass_2(bytes, start, end);
    }

    static void validate(int start, int end, int length) {
        if (start < 0) {
            throw new IndexOutOfBoundsException();
        } else if (end < 0) {
            throw new IndexOutOfBoundsException();
        } else if (end > length) {
            throw new IndexOutOfBoundsException();
        } else if (start > end) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static boolean equals(CharSequence a, CharSequence b) {
        if (a.length() != b.length()) {
            return false;
        }
        int length = a.length();
        for (int i = 0; i < length; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static int compareToIgnoreCase(CharSequence me, CharSequence another) {
        int end;
        int myPos;
        int anotherPos;
        int myLen = me.length();
        int anotherLen = another.length();
        if (myLen < anotherLen) {
            end = myLen;
        } else {
            end = anotherLen;
        }
        int anotherPos2 = 0;
        int myPos2 = 0;
        while (myPos2 < end) {
            myPos = myPos2 + 1;
            anotherPos = anotherPos2 + 1;
            int result = Character.toLowerCase(me.charAt(myPos2)) - Character.toLowerCase(another.charAt(anotherPos2));
            if (result != 0) {
                return result;
            }
            anotherPos2 = anotherPos;
            myPos2 = myPos;
        }
        anotherPos = anotherPos2;
        myPos = myPos2;
        return myLen - anotherLen;
    }
}
