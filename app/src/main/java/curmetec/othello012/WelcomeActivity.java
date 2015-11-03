package curmetec.othello012;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final OthelloButton btnStart      = (OthelloButton) findViewById(R.id.btn_start);
        btnStart.setButtonface(R.drawable.button_face_start);

        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent myIntent = new Intent(WelcomeActivity.this, GameActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                WelcomeActivity.this.startActivity(myIntent);
            }
        });
    }
}
