package com.example.pbg__assignment1;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Switch.OnCheckedChangeListener, SensorEventListener, View.OnClickListener {

    private Switch accelo_Switch, gyro_Switch, comp_Switch, orient_Switch;
    private TextView acc_X, acc_Y, acc_Z, gyro_X, gyro_Y, gyro_Z, comp_X, comp_Y, comp_Z, accPitch_View,
            accRoll_View, accYaw_View, gyro_Roll_View, gyro_Pitch_View, gyro_Yaw_View, fused_Pitch, fused_Roll, fused_Yaw;
    private Float accX = 0.0f, accY = 0.0f, accZ = 0.0f, gyX = 0.0f, gyY = 0.0f, gyZ = 0.0f, cmX = 0.0f, cmY = 0.0f, cmZ = 0.0f;
    private SensorManager SM;
    private Sensor Accelo, Gyro, Compass;
    private Float acc_Pitch = 0.0f, acc_Roll = 0.0f, acc_Yaw = 0.0f;
    private Float gyro_Pitch = 0.0f, gyro_Roll = 0.0f, gyro_Yaw = 0.0f;
    private long gyro_TimeStamp = 0, interval =0;
    private double  fPitch=0, fRoll=0, fYaw=0;

    private ImageView mobileImg;
    private Button calibrate_Btn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    @Override
    protected void onResume() {
        super.onResume();

        acc_X = (TextView) findViewById(R.id.Acc_X);
        acc_Y = (TextView) findViewById(R.id.Acc_Y);
        acc_Z = (TextView) findViewById(R.id.Acc_Z);

        gyro_X = (TextView) findViewById(R.id.Gyro_X);
        gyro_Y = (TextView) findViewById(R.id.Gyro_Y);
        gyro_Z = (TextView) findViewById(R.id.Gyro_Z);

        comp_X = (TextView) findViewById(R.id.Comp_X);
        comp_Y = (TextView) findViewById(R.id.Comp_Y);
        comp_Z = (TextView) findViewById(R.id.Comp_Z);

        accPitch_View = (TextView)findViewById(R.id.acc_Pitch_View);
        accRoll_View = (TextView)findViewById(R.id.acc_Roll_View);
        accYaw_View = (TextView)findViewById(R.id.acc_Yaw_View);

        gyro_Pitch_View = (TextView)findViewById(R.id.gy_Pitch_View);
        gyro_Roll_View = (TextView)findViewById(R.id.gy_Roll_View);
        gyro_Yaw_View = (TextView)findViewById(R.id.gy_Yaw_View);

        fused_Pitch = (TextView)findViewById(R.id.f_Pitch);
        fused_Roll = (TextView)findViewById(R.id.f_Roll);
        fused_Yaw = (TextView)findViewById(R.id.f_Yaw);


        accelo_Switch = (Switch) findViewById(R.id.Accelo_Switch);
        gyro_Switch = (Switch) findViewById(R.id.Gyro_Switch);
        comp_Switch = (Switch) findViewById(R.id.Comp_Switch);
        orient_Switch = (Switch)findViewById(R.id.Orient_Switch);

        accelo_Switch.setOnCheckedChangeListener(this);
        gyro_Switch.setOnCheckedChangeListener(this);
        comp_Switch.setOnCheckedChangeListener(this);
        orient_Switch.setOnCheckedChangeListener(this);

        mobileImg = (ImageView)findViewById(R.id.mobileImage);
        calibrate_Btn = (Button)findViewById(R.id.calibrate_btn);

        calibrate_Btn.setOnClickListener(this);

        SM = (SensorManager) getSystemService(SENSOR_SERVICE);

        Accelo = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Gyro = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Compass = SM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);





        if(Accelo == null){
            Toast.makeText(getApplicationContext(), "Accelerometer Doesn't exist on the phone" + ": Sorry !!! ", Toast.LENGTH_LONG).show();

        }

        if(Gyro == null){
            Toast.makeText(getApplicationContext(), "Gyroscope Doesn't exist on the phone" + ": Sorry !!! ", Toast.LENGTH_LONG).show();
        }

        if(Compass == null){
            Toast.makeText(getApplicationContext(), "Magnetometer (i.e Compass) Doesn't exist on the phone" + ": Sorry !!! ", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton sV, boolean isChecked) {

        switch (sV.getId()) {
            case R.id.Accelo_Switch: {
                if (isChecked == true) {
                    SM.registerListener(this, Accelo, SM.SENSOR_DELAY_NORMAL);
                    Toast.makeText(getApplicationContext(), "SensorManager registered to Accelerometer !", Toast.LENGTH_LONG).show();

                } else if (isChecked == false) {
                    SM.unregisterListener(this, Accelo);
                    acc_X.setText("Not Registered!");
                    acc_Y.setText("Not Registered!");
                    acc_Z.setText("Not Registered!");
                    Toast.makeText(getApplicationContext(), "Accelerometer Unregistered !!", Toast.LENGTH_LONG).show();
                }

                break;
            }
            case R.id.Gyro_Switch: {
                if (isChecked == true) {
                    SM.registerListener(this, Gyro, SM.SENSOR_DELAY_NORMAL);
                    Toast.makeText(getApplicationContext(), "SensorManager registered to Gyroscope !", Toast.LENGTH_LONG).show();
                } else if (isChecked == false) {
                    SM.unregisterListener(this, Gyro);
                    gyro_X.setText("Not Registered!");
                    gyro_Y.setText("Not Registered!");
                    gyro_Z.setText("Not Registered!");
                    Toast.makeText(getApplicationContext(), "Gyroscope Unregistered !!", Toast.LENGTH_LONG).show();
                }

                break;
            }
            case R.id.Comp_Switch: {
                if (isChecked == true) {
                    SM.registerListener(this, Compass, SM.SENSOR_DELAY_NORMAL);
                    Toast.makeText(getApplicationContext(), "SensorManager registered to Magnetometer !", Toast.LENGTH_LONG).show();
                } else if (isChecked == false) {
                    SM.unregisterListener(this, Compass);
                    comp_X.setText("Not Registered!");
                    comp_Y.setText("Not Registered!");
                    comp_Z.setText("Not Registered!");
                    Toast.makeText(getApplicationContext(), "Magnetometer Unregistered !!", Toast.LENGTH_LONG).show();
                }

                break;
            }
            case R.id.Orient_Switch: {
                if (isChecked == true) {
                    if (accelo_Switch.isChecked() == false) {

                        accelo_Switch.setChecked(true);
                        SM.registerListener(this, Accelo, SM.SENSOR_DELAY_NORMAL);
                        Toast.makeText(getApplicationContext(), "SensorManager registered to Accelerometer !", Toast.LENGTH_LONG).show();

                    }
                    if (gyro_Switch.isChecked() == false) {

                        gyro_Switch.setChecked(true);
                        SM.registerListener(this, Gyro, SM.SENSOR_DELAY_NORMAL);
                        Toast.makeText(getApplicationContext(), "SensorManager registered to Gyroscope !", Toast.LENGTH_LONG).show();

                    }
                    if (comp_Switch.isChecked() == false) {

                        comp_Switch.setChecked(true);
                        SM.registerListener(this, Compass, SM.SENSOR_DELAY_NORMAL);
                        Toast.makeText(getApplicationContext(), "SensorManager registered to Magnetometer !", Toast.LENGTH_LONG).show();

                    }
                    calibrate_gyroOrientation();

                } else {
                    SM.unregisterListener(this, Accelo);
                    accelo_Switch.setChecked(false);
                    Toast.makeText(getApplicationContext(), "Accelerometer Unregistered !!", Toast.LENGTH_LONG).show();

                    SM.unregisterListener(this, Gyro);
                    gyro_Switch.setChecked(false);
                    Toast.makeText(getApplicationContext(), "Gyroscope Unregistered !!", Toast.LENGTH_LONG).show();

                    SM.unregisterListener(this, Compass);
                    comp_Switch.setChecked(false);
                    Toast.makeText(getApplicationContext(), "Magnetometer Unregistered !!", Toast.LENGTH_LONG).show();

                    SM.unregisterListener(this);
                    accPitch_View.setText("Unregistered from Sensors!");
                    accRoll_View.setText("Unregistered from Sensors!");
                    accYaw_View.setText("Unregistered from Sensors!");
                    gyro_Pitch_View.setText("Unregistered from Sensors!");
                    gyro_Roll_View.setText("Unregistered from Sensors!");
                    gyro_Yaw_View.setText("Unregistered from Sensors!");

                    fPitch=0;
                    fRoll=0;
                    fYaw=0;

                }

                break;
            }
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        switch (event.sensor.getType()){
            case (Sensor.TYPE_ACCELEROMETER):{
                if (accelo_Switch.isChecked() == true)
                {
                    accX = event.values[0];
                    accY = event.values[1];
                    accZ = event.values[2];
                    acc_X.setText(accX.toString());
                    acc_Y.setText(accY.toString());
                    acc_Z.setText(accZ.toString());

                    if(orient_Switch.isChecked() == true) {
                        acc_Pitch = getAcc_Pitch(accX, accY, accZ);
                        acc_Roll = getAcc_Roll(accX, accY, accZ);
                        acc_Yaw = getAcc_Yaw(cmX, cmY, cmZ, acc_Pitch, acc_Roll);
                        accPitch_View.setText(acc_Pitch.toString());
                        accRoll_View.setText(acc_Roll.toString());
                        accYaw_View.setText(acc_Yaw.toString());
                    }
                }

                break;
            }
            case (Sensor.TYPE_GYROSCOPE):{
                if (gyro_Switch.isChecked() == true)
                {
                    gyX = event.values[0];
                    gyY = event.values[1];
                    gyZ = event.values[2];

                    gyro_X.setText(gyX.toString());
                    gyro_Y.setText(gyY.toString());
                    gyro_Z.setText(gyZ.toString());
                }

                if (orient_Switch.isChecked() == true) {
                    interval = event.timestamp - gyro_TimeStamp;
                    if (gyro_TimeStamp <= 1) {
                        gyro_TimeStamp = event.timestamp;
                        break;
                    }

                    gyro_TimeStamp = event.timestamp;
                    float dT = interval * 0.000000001f; // nanosec to sec

                    gyro_Pitch = (float) ((gyro_Pitch) + ((-gyX) * dT * 57.3f)) % 360;
                    gyro_Roll = (float) ((gyro_Roll) + ((gyY) * dT * 57.3f)) % 360;
                    gyro_Yaw = (float) ((gyro_Yaw) + (-gyZ * dT * 57.3f)) % 360;

                    gyro_Pitch_View.setText(gyro_Pitch.toString());
                    gyro_Roll_View.setText(gyro_Roll.toString());
                    gyro_Yaw_View.setText(gyro_Yaw.toString());

                }

                break;

                }
            case (Sensor.TYPE_MAGNETIC_FIELD):{
                if (comp_Switch.isChecked() == true)
                {
                    cmX = event.values[0];
                    cmY = event.values[1];
                    cmZ = event.values[2];

                    comp_X.setText(cmX.toString());
                    comp_Y.setText(cmY.toString());
                    comp_Z.setText(cmZ.toString());

                }

                break;
            }

        }

        if (orient_Switch.isChecked() == true){
            complimentaryFiler();
            mobileImg.setRotationX((float) fPitch);
            mobileImg.setRotationY((float) fRoll);
            mobileImg.setRotation((float) fYaw);

        }else if(orient_Switch.isChecked() == false){
            fPitch=0;
            fRoll=0;
            fYaw=0;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private float getAcc_Pitch(Float accX, Float accY, Float accZ){
        Float magnitude = (float) Math.abs(Math.sqrt(Math.pow(accX, 2) + Math.pow(accY, 2) + Math.pow(accZ, 2)));
        Float pitch = (float) Math.toDegrees(Math.asin((-accY) / magnitude));
        return pitch;
    }

    private float getAcc_Roll(Float accX, Float accY, Float accZ){

        Float magnitude = (float) Math.abs(Math.sqrt(Math.pow(accX, 2) + Math.pow(accY, 2) + Math.pow(accZ, 2)));
        Float roll = -(float) Math.toDegrees(Math.asin(accX / magnitude));
        return roll;

    }

    private float getAcc_Yaw(Float cmX, Float cmY, Float cmZ, Float acc_Pitch, Float acc_Roll){
        Float Y = (float) ((float) ((-cmX) * Math.cos(acc_Roll)) + (cmZ * Math.sin(acc_Roll)));
        Float X = (float) ((float) (cmX * Math.sin(acc_Pitch) * Math.sin(acc_Roll)) + (cmY * Math.cos(acc_Pitch)) + (cmZ * Math.sin(acc_Pitch) * Math.cos(acc_Roll)) );
        Float Yaw = (float) Math.toDegrees(Math.atan2(Y,X));

        if (Yaw == null){
            Yaw = 0.0f;
        }
        return Yaw;
    }

    private void complimentaryFiler(){
        fPitch = (gyro_Pitch * 0.98) + (acc_Pitch * 0.02);
        fRoll = (gyro_Roll * 0.98) + (acc_Roll * 0.02);
        fYaw = (gyro_Yaw * 0.98) + (acc_Yaw * 0.02);

        int fP = (int) Math.round(fPitch);
        int fR = (int) Math.round(fRoll);
        int fY = (int) Math.round(fYaw);

        fused_Pitch.setText(Integer.toString(fP));
        fused_Roll.setText(Integer.toString(fR));
        fused_Yaw.setText(Integer.toString(fY));
    }

    private void calibrate_gyroOrientation(){
        gyro_Pitch = acc_Pitch ;
        gyro_Roll = acc_Roll;

        accX = 0.0f; accY = 0.0f; accZ = 0.0f;
        gyX = 0.0f; gyY = 0.0f; gyZ = 0.0f;
        cmX = 0.0f; cmY = 0.0f; cmZ = 0.0f;


        fPitch=0; fRoll=0; fYaw=0;
        acc_Pitch = 0.0f; acc_Roll = 0.0f; acc_Yaw = 0.0f;
        gyro_Pitch = 0.0f; gyro_Roll = 0.0f; gyro_Yaw = 0.0f;

        gyro_TimeStamp = 0; interval =0;

        mobileImg.setRotationX(0.0f);
        mobileImg.setRotationY(0.0f);
        mobileImg.setRotation(0.0f);
        //gyro_Yaw = acc_Yaw ;
    }

    @Override
    public void onClick(View v) {
        calibrate_gyroOrientation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SM.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SM.unregisterListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SM.unregisterListener(this);
    }


}

