package com.approx.togglebutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    ToggleButton mToggleButton_1,mToggleButton_2;
    Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToggleButton_1= (ToggleButton) findViewById(R.id.toggle_button_1);
        mToggleButton_2= (ToggleButton) findViewById(R.id.toggle_button_2);
        mButton= (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer result = new StringBuffer();
                result.append("Toggle Button 1:").append(mToggleButton_1.getText())
                        .append("\nToggle Button2:").append(mToggleButton_2.getText());
                Toast.makeText(getApplicationContext(),result.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }


}
