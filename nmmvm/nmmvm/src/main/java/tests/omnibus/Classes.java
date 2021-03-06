package tests.omnibus;// Copyright 2008 The Android Open Source Project

import java.io.Serializable;
import java.util.Arrays;

import tests.common.MyAssert;

/**
 * Exercise some class-related instructions.
 */
public class Classes {
    int mSome;

    public void subFunc(boolean wantSub) {
        MyAssert.myassert(!wantSub);
    }

    public void subFuncn(boolean wantSub) {

    }

    void checkCast(Object thisRef, Object moreRef, Object nullRef) {
        System.out.println("Classes.checkCast");

        Classes classes;
        MoreClasses more;

        classes = (Classes) thisRef;
        MyAssert.myassert(thisRef instanceof Classes);
        classes = (Classes) moreRef;
        MyAssert.myassert(moreRef instanceof Classes);

        more = (MoreClasses) moreRef;
        MyAssert.myassert(moreRef instanceof MoreClasses);
        MyAssert.myassert(!(thisRef instanceof MoreClasses));

        try {
            more = (MoreClasses) thisRef;
            MyAssert.myassert(false);
        } catch (ClassCastException cce) {
            //System.out.println("  class cast msg: " + cce.getMessage());
            //Dalvik throws terser message than Hotspot VM
//            MyAssert.myassert(cce.getMessage().regionMatches(false, 0, "Classes", 0, 7));
        }
        MyAssert.myassert(!(thisRef instanceof MoreClasses));

        /* hopefully these classes cause a resolve */
        try {
            java.math.RoundingMode mode = (java.math.RoundingMode) thisRef;
            MyAssert.myassert(false);
        } catch (ClassCastException cce) {
            //System.out.println("  class cast msg: " + cce.getMessage());
            //Dalvik throws terser message than Hotspot VM
//            MyAssert.myassert(cce.getMessage().regionMatches(false, 0, "Classes", 0, 7));
        }
        MyAssert.myassert(!(thisRef instanceof java.math.BigDecimal));

        /* try some stuff with a null reference */
        classes = (Classes) nullRef;
        classes = (MoreClasses) nullRef;
        more = (MoreClasses) nullRef;
        MyAssert.myassert(!(nullRef instanceof Classes));

    }

    public native void checkCastn(Object thisRef, Object moreRef, Object nullRef);


    static void xTests(Object x) {
        MyAssert.myassert(x instanceof Classes);
        MyAssert.myassert(!(x instanceof MoreClasses));
    }

    static void yTests(Object y) {
        MyAssert.myassert(y instanceof Classes);
        MyAssert.myassert(y instanceof MoreClasses);
    }

    static void xarTests(Object xar) {
        MyAssert.myassert(xar instanceof Object);
        MyAssert.myassert(!(xar instanceof Classes));
        MyAssert.myassert(xar instanceof Classes[]);
        MyAssert.myassert(!(xar instanceof MoreClasses[]));
        MyAssert.myassert(xar instanceof Object[]);
        MyAssert.myassert(!(xar instanceof Object[][]));
    }

    static void yarTests(Object yar) {
        MyAssert.myassert(yar instanceof Classes[]);
        MyAssert.myassert(yar instanceof MoreClasses[]);
    }

    static void xarararTests(Object xararar) {
        MyAssert.myassert(xararar instanceof Object);
        MyAssert.myassert(xararar instanceof Object[]);
        MyAssert.myassert(!(xararar instanceof Classes));
        MyAssert.myassert(!(xararar instanceof Classes[]));
        MyAssert.myassert(!(xararar instanceof Classes[][]));
        MyAssert.myassert(xararar instanceof Classes[][][]);
        MyAssert.myassert(!(xararar instanceof MoreClasses[][][]));
        MyAssert.myassert(xararar instanceof Object[][][]);
        MyAssert.myassert(xararar instanceof Serializable);
        MyAssert.myassert(xararar instanceof Serializable[]);
        MyAssert.myassert(xararar instanceof Serializable[][]);
        MyAssert.myassert(!(xararar instanceof Serializable[][][]));
    }

    static void yarararTests(Object yararar) {
        MyAssert.myassert(yararar instanceof Classes[][][]);
        MyAssert.myassert(yararar instanceof MoreClasses[][][]);
    }

    static void iarTests(Object iar) {
        MyAssert.myassert(iar instanceof Object);
        MyAssert.myassert(!(iar instanceof Object[]));
    }

    static void iararTests(Object iarar) {
        MyAssert.myassert(iarar instanceof Object);
        MyAssert.myassert(iarar instanceof Object[]);
        MyAssert.myassert(!(iarar instanceof Object[][]));
    }

    /*
     * Exercise filled-new-array and test instanceof on arrays.
     *
     * We call out instead of using "instanceof" directly to avoid
     * compiler optimizations.
     */
    static void arrayInstance() {
        System.out.println("Classes.arrayInstance");

        Classes x = new Classes();
        Classes[] xar = new Classes[1];
        Classes[][] xarar = new Classes[1][1];
        Classes[][][] xararar = new Classes[1][2][3];
        MoreClasses y = new MoreClasses();
        MoreClasses[] yar = new MoreClasses[3];
        MoreClasses[][] yarar = new MoreClasses[2][3];
        MoreClasses[][][] yararar = new MoreClasses[1][2][3];
        int[] iar = new int[1];
        int[][] iarar = new int[1][1];
        Object test;

        xTests(x);
        yTests(y);
        xarTests(xar);
        yarTests(yar);
        xarararTests(xararar);
        yarararTests(yararar);
        iarTests(iar);
        iararTests(iarar);

        yararar[0] = yarar;
        yararar[0][0] = yar;
        yararar[0][1] = yar;
        yararar[0][0][0] = y;
        yararar[0][0][1] = y;
        yararar[0][0][2] = y;
        yararar[0][1][0] = y;
        yararar[0][1][1] = y;
        yararar[0][1][2] = y;

        String strForm;

        String[][][][] multi1 = new String[2][3][2][1];
        multi1[0] = new String[2][3][2];
        multi1[0][1] = new String[3][2];
        multi1[0][1][2] = new String[2];
        multi1[0][1][2][1] = "HELLO-1";
        strForm = Arrays.deepToString(multi1);

        String[][][][][] multi2 = new String[5][2][3][2][1];
        multi2[0] = new String[5][2][3][2];
        multi2[0][1] = new String[5][2][3];
        multi2[0][1][2] = new String[5][2];
        multi2[0][1][2][1] = new String[5];
        multi2[0][1][2][1][4] = "HELLO-2";
        strForm = Arrays.deepToString(multi2);


        String[][][][][][] multi3 = new String[2][5][2][3][2][1];
        multi3[0] = new String[2][][][][];
        multi3[0][1] = new String[3][][][];
        multi3[0][1][2] = new String[2][][];
        multi3[0][1][2][1] = new String[5][];
        multi3[0][1][2][1][4] = new String[2];
        multi3[0][1][2][1][4][1] = "HELLO-3";
        strForm = Arrays.deepToString(multi3);

        // build up pieces
        String[][][][][][] multi4 = new String[1][][][][][];
        multi4[0] = new String[2][][][][];
        multi4[0][1] = new String[3][][][];
        multi4[0][1][2] = new String[2][][];
        multi4[0][1][2][1] = new String[5][];
        multi4[0][1][2][1][4] = new String[2];
        multi4[0][1][2][1][4][1] = "HELLO-4";
        strForm = Arrays.deepToString(multi4);

        /* this is expected to fail; 1073921584 * 4 overflows 32 bits */
        try {
            String[][][][][] multiX = new String[5][2][3][2][1073921584];
            MyAssert.myassert(false);
        } catch (Error e) {
            //System.out.println("  Got expected failure: " + e);
        }

    }
    public native static void arrayInstancen();

    public static void run() {
        Classes classes = new Classes();
        MoreClasses more = new MoreClasses();
        classes.checkCast(classes, more, null);

        more.subFunc(true);
        more.superFunc(false);
        arrayInstance();
    }

    public static void run2() {
        Classes classes = new Classes();
        MoreClasses more = new MoreClasses();
//        classes.checkCastn(classes, more, null);

        more.subFuncn(true);
        more.superFunc(false);
//        arrayInstance();
    }

    public native static void runn();
}

class MoreClasses extends Classes {
    int mMore;

    public MoreClasses() {
    }

    public void subFunc(boolean wantSub) {
        MyAssert.myassert(wantSub);
    }
    public native void subFuncn(boolean wantSub);

    public void superFunc(boolean wantSub) {
        super.subFunc(wantSub);
    }
}
