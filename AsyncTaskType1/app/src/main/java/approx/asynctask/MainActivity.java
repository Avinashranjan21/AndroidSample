package approx.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    TextView display_text_view;
    Button async_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display_text_view= (TextView) findViewById(R.id.display_text_view);
        async_button= (Button) findViewById(R.id.async_task_button);
        async_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PrimeTask(display_text_view).execute(500);
            }
        });
    }

    private class PrimeTask extends AsyncTask<Integer,Void,BigInteger>{

        private TextView resultView;


        public PrimeTask(TextView resultView){
            this.resultView=resultView;
        }


        @Override
        protected BigInteger doInBackground(Integer... params) {
            int n = params[0];
            BigInteger prime = new BigInteger("2");
            for (int i=0; i<n; i++) {
                prime = prime.nextProbablePrime();
            }
            return prime;
        }

        @Override
        protected void onPostExecute(BigInteger bigInteger) {
            resultView.setText(String.valueOf(bigInteger));
        }
    }


}
