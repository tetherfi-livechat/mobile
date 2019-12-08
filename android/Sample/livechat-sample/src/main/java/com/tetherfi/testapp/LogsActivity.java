package com.tetherfi.testapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import tetherfi.livechat.Log;
import tetherfi.livechat.logger.LvLogger;

public class LogsActivity extends Activity  {

    public static String m_LogMessages = "";

    EditText m_TextView;

    public LogsActivity()
    {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);
        m_TextView = findViewById( R.id.txtLogs );
    }

    public void onClickClear(View v)
    {
        m_LogMessages = "";
        m_TextView.setText( m_LogMessages );
    }

}

