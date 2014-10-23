package com.testingtechs.ColorPicker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.List;

public class PickingColors extends Activity implements
        NumberPicker.OnValueChangeListener {
    //NumberPickers
    NumberPicker redNp, greenNp, blueNp;
    //Number picker values for appropriate colors
    int red, green, blue;
    //View to display updated colors.
    View view;

    // Tags for saving instance state
    private final String RED_VAL = "Red Value";
    private final String GREEN_VAL = "Green Value";
    private final String BLUE_VAL = "Blue Value";
    private final String RGB = "RGB";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //List of number pickers from build loop.
        List<NumberPicker> nps = buildNumberPickers();
        redNp = nps.get(0);
        greenNp = nps.get(1);
        blueNp = nps.get(2);
        view = findViewById(R.id.colorView);

        if (savedInstanceState != null) {
            Log.d("CP savedInstanceState ~~> ", savedInstanceState.toString());
            Log.d("Restored Red -->",
                    savedInstanceState.get(RED_VAL).toString());
            Log.d("Restored Green -->",
                    savedInstanceState.get(GREEN_VAL).toString());
            Log.d("Restored Blue -->",
                    savedInstanceState.get(BLUE_VAL).toString());

            setRed((Integer) savedInstanceState.get(RED_VAL));
            setGreen((Integer) savedInstanceState.get(GREEN_VAL));
            setBlue((Integer) savedInstanceState.get(BLUE_VAL));

            redNp.setValue((Integer) savedInstanceState.get(RED_VAL));
            greenNp.setValue((Integer) savedInstanceState.get(GREEN_VAL));
            blueNp.setValue((Integer) savedInstanceState.get(BLUE_VAL));

            view.setBackgroundColor(Color.rgb(getRed(), getGreen(), getBlue()));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(RED_VAL, getRed());
        savedInstanceState.putInt(GREEN_VAL, getGreen());
        savedInstanceState.putInt(BLUE_VAL, getBlue());

        Log.d("Saved Red --> ",
                savedInstanceState.get(RED_VAL).toString());
        Log.d("Saved Green --> ",
                savedInstanceState.get(GREEN_VAL).toString());
        Log.d("Saved Blue --> ",
                savedInstanceState.get(BLUE_VAL).toString());
    }

    /**
     * Set up the number pickers and return them as a list.
     *
     * @return list of built number pickers.
     */
    public List<NumberPicker> buildNumberPickers() {
        NumberPicker redNumPicker =
                (NumberPicker) findViewById(R.id.RedSpinner);
        NumberPicker blueNumPicker =
                (NumberPicker) findViewById(R.id.BlueSpinner);
        NumberPicker greenNumPicker =
                (NumberPicker) findViewById(R.id.GreenSpinner);

        //Block soft-keyboard
        redNumPicker.setDescendantFocusability
                (NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        blueNumPicker.setDescendantFocusability
                (NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        greenNumPicker.setDescendantFocusability
                (NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        List<NumberPicker> numberPickers = new ArrayList<NumberPicker>();
        numberPickers.add(redNumPicker);
        numberPickers.add(greenNumPicker);
        numberPickers.add(blueNumPicker);

        //Loop to set up number pickers
        for (NumberPicker numberPicker : numberPickers) {
            numberPicker.setEnabled(true);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(255);
            numberPicker.setOnValueChangedListener(this);
        }
        return numberPickers;
    }

    /**
     * Implemented from NumberPicker.OnValueChangeListener
     *
     * @param np     NumberPicker
     * @param oldVal previous value of number picker (not used here)
     * @param newVal new value to adjust color view
     */
    public void onValueChange(NumberPicker np, int oldVal, int newVal) {
        if (np.getId() == getRedNumPickId()) {
            setRed(newVal);
        } else if (np.getId() == getGreenNumPickId()) {
            setGreen(newVal);
        } else if (np.getId() == getBlueNumPickId()) {
            setBlue(newVal);
        }
        view.setBackgroundColor(Color.rgb(getRed(), getGreen(), getBlue()));
        view.invalidate();
        Log.d("onValueChange", "Value change for " + np.getId() + ". " +
                "Old value was: " + oldVal +
                ". New Value is: " + newVal + ".");
    }

    /**
     * Once the user has selected the color the button will be pressed which
     * will send back the color to be filled in on the color blending app.
     *
     * @param view
     */
    public void sendColor(View view) {
        if (view.getId() == R.id.button) {
            Intent i = new Intent();
            i.putExtra(RGB,
                    new int[]{getRed(), getGreen(), getBlue()});
            setResult(RESULT_OK, i);
            finish();
        } else {
            Log.e("sendColor -->", "Unknown button clicked");
        }
    }

    /**
     * Set the value for red to be passed for background color change from
     * onValueChange.
     *
     * @param newVal new red value
     */
    public void setRed(int newVal) {
        this.red = newVal;
    }

    /**
     * Get the red number to change background color.
     *
     * @return the value of the red number picker
     */
    public int getRed() {
        return this.red;
    }

    /**
     * Set the value for green to be passed for background color change.
     *
     * @param newVal new green value
     */
    public void setGreen(int newVal) {
        this.green = newVal;
    }

    /**
     * Get the green number to change background color.
     *
     * @return the value of the green number picker.
     */
    public int getGreen() {
        return this.green;
    }

    /**
     * Set the value for blue to be passed for background color change.
     *
     * @param newVal new blue value
     */
    public void setBlue(int newVal) {
        this.blue = newVal;
    }

    /**
     * Get the blue number to change background color.
     *
     * @return the value of the blue number picker.
     */
    public int getBlue() {
        return this.blue;
    }

    /**
     * Get id of NumberPicker so that new value from value change can be set for
     * Red.
     *
     * @return id value for red number picker.
     */
    public int getRedNumPickId() {
        return this.redNp.getId();
    }

    /**
     * Get id of NumberPicker so that new value from value change can be set for
     * Green.
     *
     * @return id value for green number picker.
     */
    public int getGreenNumPickId() {
        return this.greenNp.getId();
    }

    /**
     * Get id of NumberPicker so that new value from value change can be set for
     * Blue.
     *
     * @return id value for blue number picker.
     */
    public int getBlueNumPickId() {
        return this.blueNp.getId();
    }
}