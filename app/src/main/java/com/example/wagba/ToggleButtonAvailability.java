package com.example.wagba;

import android.widget.Button;

import java.util.TimerTask;

class ToggleButtonAvailability extends TimerTask
{
    Button button;
    Boolean enable;

    public ToggleButtonAvailability(Button button, Boolean enable) {
        this.button = button;
        this.enable = enable;
    }


    @Override
    public void run() {
        if(enable){
            button.setEnabled(true);
        }else {
            button.setEnabled(false);
        }
    }

}