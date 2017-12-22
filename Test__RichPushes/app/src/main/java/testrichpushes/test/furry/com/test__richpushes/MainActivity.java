package testrichpushes.test.furry.com.test__richpushes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gameofwhales.sdk.GameOfWhales;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameOfWhales.Init(this, "58ff3e2e30bb6d070389ef0f", GameOfWhales.STORE_GOOGLEPLAY, null);
        GameOfWhales.SetAndroidProjectID("672610198143");
    }
}
