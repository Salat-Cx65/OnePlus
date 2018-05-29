package net.oneplus.weather.app;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class BaseBarActivity extends Activity {
    private ActionBar bar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bar = getActionBar();
        if (this.bar != null) {
            this.bar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }

    protected void setBarTitle(String title) {
        if (this.bar != null) {
            this.bar.setTitle(title);
        } else {
            setTitle(title);
        }
    }

    protected void setBarTitle(int titleId) {
        if (this.bar != null) {
            this.bar.setTitle(getString(titleId));
        } else {
            setTitle(titleId);
        }
    }
}
