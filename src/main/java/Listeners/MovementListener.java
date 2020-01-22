package Listeners;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import CallBacks.MotionListenerCallBack;
import Utils.GlobalMainContext;


/**
 * This class detects when the device was moved in any way using its sensors.
 * It's util for the Signal Listener because the phone has to be still.
 */
public class MovementListener implements SensorEventListener {

    //region Private members
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private static MovementListener mInstance;
    private MotionListenerCallBack mMotionListener = null;
    private float mAccelLast;
    private float mAccelCurrent;
    private float mAccel;
    //endregion

    //region Constructor
    private MovementListener(){
        Context mainContext = GlobalMainContext.getMainContext();
        this.mSensorManager = (SensorManager) mainContext.getSystemService(Context.SENSOR_SERVICE);
        this.mAccelerometer =  mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        this.mSensorManager.registerListener(this, mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        this.mAccel = 0.00f;
        this.mAccelLast = SensorManager.GRAVITY_EARTH;
        this.mAccelCurrent = SensorManager.GRAVITY_EARTH;
    }
    //endregion


    //region Public Methods
    public static MovementListener getInstance(){
        if(mInstance == null){
            mInstance = new MovementListener();
        }
        return mInstance;
    }

    public void addListener(MotionListenerCallBack listener){
        this.mMotionListener = listener;
        Context mainContext = GlobalMainContext.getMainContext();
        this.mSensorManager = (SensorManager) mainContext.getSystemService(Context.SENSOR_SERVICE);
        this.mAccelerometer =  mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        this.mSensorManager.registerListener(this, mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        this.mAccel = 0.00f;
        this.mAccelLast = SensorManager.GRAVITY_EARTH;
        this.mAccelCurrent = SensorManager.GRAVITY_EARTH;
    }

    public void stop(){
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        this.mAccelLast = this.mAccelCurrent;
        this.mAccelCurrent = (float)Math.sqrt(x * x + y * y + z * z);
        float delta = mAccelCurrent - mAccelLast;
        this.mAccel = this.mAccel * 0.9f + delta;
        if( this.mAccel > 2.0f ) {
            this.mMotionListener.onMotionDetected(event, mAccel);

            SharedPreferences sharedPreferences = GlobalMainContext.getMainContext().getSharedPreferences("sharedTime",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("lastMovementTime",System.currentTimeMillis());
            editor.commit();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    //endregion
}

