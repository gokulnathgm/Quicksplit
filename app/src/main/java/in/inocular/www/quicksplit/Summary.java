package in.inocular.www.quicksplit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class Summary extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView txtView = (TextView) findViewById(R.id.textView);
        txtView.setText("Summary details");
    }
}
