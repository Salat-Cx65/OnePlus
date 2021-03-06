package com.oneplus.lib.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import com.oneplus.commonctrl.R;
import net.oneplus.weather.widget.openglbase.RainDownpour;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class OPNumberPickerDialog extends OPAlertDialog implements OnClickListener {
    private final int OP_NUMBER_PICKER_DEFAULT_MAX_VALUE;
    private final int OP_NUMBER_PICKER_DEFAULT_MIN_VALUE;
    private int mMaxValue;
    private TextView mMin;
    private int mMinValue;
    private NumberPicker mNumberPicker;
    private OnNumberSetListener mNumberSetListener;
    private int mPlurals;
    private int mValue;

    public static interface OnNumberSetListener {
        void onNumberSet(NumberPicker numberPicker, int i);
    }

    public OPNumberPickerDialog(Context context) {
        this(context, 0);
    }

    public OPNumberPickerDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.OP_NUMBER_PICKER_DEFAULT_MIN_VALUE = 1;
        this.OP_NUMBER_PICKER_DEFAULT_MAX_VALUE = 60;
        this.mValue = 1;
        this.mMinValue = 1;
        this.mMaxValue = 60;
    }

    public OPNumberPickerDialog(Context context, OnNumberSetListener listener) {
        this(context, 0, listener);
    }

    public OPNumberPickerDialog(Context context, int themeResId, OnNumberSetListener listener) {
        super(context, themeResId);
        this.OP_NUMBER_PICKER_DEFAULT_MIN_VALUE = 1;
        this.OP_NUMBER_PICKER_DEFAULT_MAX_VALUE = 60;
        this.mValue = 1;
        this.mMinValue = 1;
        this.mMaxValue = 60;
        this.mNumberSetListener = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.op_number_picker_dialog, null);
        this.mNumberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        this.mNumberPicker.setMinValue(1);
        this.mNumberPicker.setMaxValue(RainDownpour.Z_RANDOM_RANGE);
        this.mNumberPicker.setValue(this.mValue);
        this.mMin = (TextView) view.findViewById(R.id.min);
        updateMinutes();
        this.mNumberPicker.setOnValueChangedListener(new OnValueChangeListener() {
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                OPNumberPickerDialog.this.updateMinutes();
            }
        });
        setButton(-1, this.mContext.getString(17039370), this);
        setButton(ListPopupWindow.WRAP_CONTENT, this.mContext.getString(17039360), this);
        setView(view);
        super.onCreate(savedInstanceState);
    }

    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case ListPopupWindow.WRAP_CONTENT:
                cancel();
            case RainSurfaceView.RAIN_LEVEL_DEFAULT:
                if (this.mNumberSetListener != null) {
                    this.mNumberSetListener.onNumberSet(this.mNumberPicker, this.mNumberPicker.getValue());
                }
            default:
                break;
        }
    }

    public void setValue(int value) {
        this.mValue = value;
    }

    public void setMinValue(int minValue) {
        this.mMinValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        this.mMinValue = maxValue;
    }

    public int getValue() {
        return this.mValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public int getMaxValue() {
        return this.mMinValue;
    }

    public void updateNumber(int number) {
        this.mNumberPicker.setValue(number);
    }

    private void updateMinutes() {
        if (this.mPlurals != 0) {
            this.mMin.setText(String.format(this.mContext.getResources().getQuantityText(this.mPlurals, this.mNumberPicker.getValue()).toString(), new Object[0]));
        }
    }

    public void setPlurals(int p) {
        this.mPlurals = p;
    }
}
