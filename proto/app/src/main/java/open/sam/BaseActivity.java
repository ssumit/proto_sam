package open.sam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

public abstract class BaseActivity extends ActionBarActivity {

    @Inject
    OnboardingService _onboardingService;
    Logger logger = Logger.getLogger(BaseActivity.class.getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.log(Level.INFO, "starting " + this.getClass().getSimpleName());
        _onboardingService = SamApplication.getComponent().provideOnboardingService();
        boolean onboardingComplete = _onboardingService.isOnboardingComplete();
        if (!onboardingComplete && !(this.getClass().equals(OnboardingActivity.class))) {
            Intent intent = new Intent(this, OnboardingActivity.class);
            startActivity(intent);
            finish();
        }
    }
}