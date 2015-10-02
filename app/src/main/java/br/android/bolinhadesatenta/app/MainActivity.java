package br.android.bolinhadesatenta.app;

import br.android.bolinhadesatenta.app.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.orbotix.ConvenienceRobot;
import com.orbotix.Sphero;
import com.orbotix.classic.DiscoveryAgentClassic;
import com.orbotix.common.DiscoveryAgent;
import com.orbotix.common.DiscoveryAgentEventListener;
import com.orbotix.common.DiscoveryException;
import com.orbotix.common.Robot;
import com.orbotix.common.RobotChangedStateListener;

import java.util.List;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainActivity extends Activity implements DiscoveryAgentEventListener,
        RobotChangedStateListener {

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    private DiscoveryAgent _currentDiscoveryAgent;

    private ConvenienceRobot _connectedRobot;

    ImageButton txtEsq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//                            // If the ViewPropertyAnimator API is available
//                            // (Honeycomb MR2 and later), use it to animate the
//                            // in-layout UI controls at the bottom of the
//                            // screen.
//                            if (mControlsHeight == 0) {
//                                mControlsHeight = controlsView.getHeight();
//                            }
//                            if (mShortAnimTime == 0) {
//                                mShortAnimTime = getResources().getInteger(
//                                        android.R.integer.config_shortAnimTime);
//                            }
//                            controlsView.animate()
//                                    .translationY(visible ? 0 : mControlsHeight)
//                                    .setDuration(mShortAnimTime);
//                        } else {
//                            // If the ViewPropertyAnimator APIs aren't
//                            // available, simply show or hide the in-layout UI
//                            // controls.
//                            controlsView.setVisibility(View.GONE);
//                        }
//
//                        if (visible) {
//                            // Schedule a hide().
//                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
//                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSystemUiHider.hide();
            }
        });

        txtEsq = (ImageButton) findViewById(R.id.esquerda);

        _currentDiscoveryAgent = DiscoveryAgentClassic.getInstance();
        startDiscovery();

        txtEsq.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                _connectedRobot.drive(0, 2);
//                try {
//                    wait(500);
//                }catch (Exception ignore){}
                _connectedRobot.stop();
                return false;
            }
        });
    }

    public void goTop(View view){
        _connectedRobot.drive(0, 2);
        try {
            wait(500);
        }catch (Exception ignore){}
        _connectedRobot.stop();
    }

    public void goRight(View view){
        _connectedRobot.drive(90, 2);
        try {
            wait(500);
        }catch (Exception ignore){}
        _connectedRobot.stop();
    }

    public void goLeft(View view){
        _connectedRobot.drive(270, 2);
        try {
            wait(500);
        }catch (Exception ignore){}
        _connectedRobot.stop();
    }

    public void goBottom(View view){
        _connectedRobot.drive(180, 2);
        try {
            wait(500);
        }catch (Exception ignore){}
        _connectedRobot.stop();
    }

    public void setYellow(View view){
        _connectedRobot.setLed(Color.red(R.color.yellow), Color.green(R.color.yellow), Color.blue(R.color.yellow));
    }

    public void setPink(View view){
        _connectedRobot.setLed(Color.red(R.color.pink), Color.green(R.color.pink), Color.blue(R.color.pink));
    }

    public void setPurple(View view){
        _connectedRobot.setLed(Color.red(R.color.purple), Color.green(R.color.purple), Color.blue(R.color.purple));
    }

    public void setOrange(View view){
        _connectedRobot.setLed(Color.red(R.color.orange), Color.green(R.color.orange), Color.blue(R.color.orange));
    }

    public void setInitialColor(View view){
        this.setInitialColor();
    }

    private void setInitialColor(){
        _connectedRobot.setLed(Color.red(R.color.initial), Color.green(R.color.initial), Color.blue(R.color.initial));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void handleRobotsAvailable(List<Robot> list) {
        if (_currentDiscoveryAgent instanceof DiscoveryAgentClassic) {
            // If we are using the classic discovery agent, and therefore using Sphero, we'll just connect to the first
            // one available that we get. Note that "available" in classic means paired to the phone and turned on.
            _currentDiscoveryAgent.connect(list.get(0));
        }
    }

    @Override
    public void handleRobotChangedState(Robot robot, RobotChangedStateNotificationType robotChangedStateNotificationType) {
        switch (robotChangedStateNotificationType) {
            // A robot was connected, and is ready for you to send commands to it.
            case Online:
                // When a robot is connected, this is a good time to stop discovery. Discovery takes a lot of system
                // resources, and if left running, will cause your app to eat the user's battery up, and may cause
                // your application to run slowly. To do this, use DiscoveryAgent#stopDiscovery().
                _currentDiscoveryAgent.stopDiscovery();
                // It is also proper form to not allow yourself to re-register for the discovery listeners, so let's
                // unregister for the available notifications here using DiscoveryAgent#removeDiscoveryListener().
                _currentDiscoveryAgent.removeDiscoveryListener(this);
                // Don't forget to turn on UI elements

                // Depending on what was connected, you might want to create a wrapper that allows you to do some
                // common functionality related to the individual robots. You can always of course use the
                // Robot#sendCommand() method, but Ollie#drive() reads a bit better.
                _connectedRobot = new Sphero(robot);
                Log.d("OK", "Sphero conectado");

                _connectedRobot.setZeroHeading();

                this.setInitialColor();

                break;
            case Disconnected:
                Log.d("DESCONECTADO", "Sphero foi desconectado");
                break;
            default:
                Log.v("ERRO", "Not handling state change notification: " + robotChangedStateNotificationType);
                break;
        }
    }

    private void startDiscovery() {
        try {
            // You first need to set up so that the discovery agent will notify you when it finds robots.
            // To do this, you need to implement the DiscoveryAgentEventListener interface (or declare
            // it anonymously) and then register it on the discovery agent with DiscoveryAgent#addDiscoveryListener()
            _currentDiscoveryAgent.addDiscoveryListener(this);
            // Second, you need to make sure that you are notified when a robot changes state. To do this,
            // implement RobotChangedStateListener (or declare it anonymously) and use
            // DiscoveryAgent#addRobotStateListener()
            _currentDiscoveryAgent.addRobotStateListener(this);
            // Then to start looking for a Sphero, you use DiscoveryAgent#startDiscovery()
            // You do need to handle the discovery exception. This can occur in cases where the user has
            // Bluetooth off, or when the discovery cannot be started for some other reason.
            _currentDiscoveryAgent.startDiscovery(this);
            Log.d("ACHOU", "Encontrado sphero");
        } catch (DiscoveryException e) {
            Log.e("ERRO", "Could not start discovery. Reason: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
