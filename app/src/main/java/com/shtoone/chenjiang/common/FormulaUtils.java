package com.shtoone.chenjiang.common;

import java.text.DecimalFormat;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class FormulaUtils {
    private static final String TAG = FormulaUtils.class.getSimpleName();

    private static FormulaUtils formula;

    private float back1;
    private float back2;

    private float front1;
    private float front2;

    private float Rback1;
    private float Rback2;

    private float Rfront1;
    private float Rfront2;

    private float beforeBackValue;
    private float beforeFrontValue;

    private float beforeGaoCheng;

    private FormulaUtils() {
    }

    public static FormulaUtils getInstance() {
        return formula;
    }

    public static FormulaUtils getInstance(String back1, String back2, String front1, String front2, String Rback1, String Rback2
            , String Rfront1, String Rfront2, String beforeBackValue, String beforeFrontValue, String beforeGaoCheng) {
        return FormulaUtils.getInstance(Float.parseFloat(back1), Float.parseFloat(back2), Float.parseFloat(front1), Float.parseFloat(front2),
                Float.parseFloat(Rback1), Float.parseFloat(Rback2), Float.parseFloat(Rfront1),
                Float.parseFloat(Rfront2), Float.parseFloat(beforeBackValue), Float.parseFloat(beforeFrontValue),
                Float.parseFloat(beforeGaoCheng));
    }

    public static FormulaUtils getInstance(float back1, float back2, float front1, float front2, float Rback1, float Rback2
            , float Rfront1, float Rfront2, float beforeBackValue, float beforeFrontValue, float beforeGaoCheng) {
        if (formula == null) {
            formula = new FormulaUtils();
            formula.initData(back1, back2, front1, front2, Rback1, Rback2, Rfront1,
                    Rfront2, beforeBackValue, beforeFrontValue, beforeGaoCheng);
        } else {
            formula.initData(back1, back2, front1, front2, Rback1, Rback2, Rfront1,
                    Rfront2, beforeBackValue, beforeFrontValue, beforeGaoCheng);
        }
        return formula;
    }

    private void initData(float back1, float back2, float front1, float front2, float Rback1, float Rback2
            , float Rfront1, float Rfront2, float beforeBackValue, float beforeFrontValue, float beforeGaoCheng) {
        this.back1 = round(back1, 3);
        this.back2 = round(back2, 3);
        this.front1 = round(front1, 3);
        this.front2 = round(front2, 3);
        this.Rback1 = round(Rback1, 5);
        this.Rback2 = round(Rback2, 5);
        this.Rfront1 = round(Rfront1, 5);
        this.Rfront2 = round(Rfront2, 5);
        this.beforeBackValue = round(beforeBackValue, 3);
        this.beforeFrontValue = round(beforeFrontValue, 3);
        this.beforeGaoCheng = round(beforeGaoCheng, 5);
    }

    //后视距和
    public float getBackDistance() {
        return round((beforeBackValue + back1 + back2) / 2, 3);
    }

    //前视距和
    public float getFrontDistance() {
        return round((beforeFrontValue + front1 + front2) / 2, 3);
    }

    //高差1
    public float getDifferenceHeighte1() {
        return Rback1 - Rfront1;
    }

    //高差2
    public float getDifferenceHeighte2() {
        return Rback2 - Rfront2;
    }

    //测站高差
    public float getCeZhanDifferenceHeighte() {
        return round((getDifferenceHeighte1() + getDifferenceHeighte2()) / 2, 5);
    }

    //FR读数差
    public float getFRReadDifference() {
        return Rfront1 - Rfront2;
    }

    //BR读数差
    public float getBRReadDifference() {
        return Rback1 - Rback2;
    }

    //高程值
    public float getElevationValue() {
        return beforeGaoCheng + getCeZhanDifferenceHeighte();
    }

    public float round(float value, int b) {
        String str = "";
        while (b > 0) {
            str += "0";
            b--;
        }
        return Float.parseFloat(new DecimalFormat("#." + str).format(value));
    }
}
