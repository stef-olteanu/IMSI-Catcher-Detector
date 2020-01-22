package CallBacks;

import android.hardware.SensorEvent;

public interface MotionListenerCallBack {
    void onMotionDetected(SensorEvent event, float acceleration);
}
