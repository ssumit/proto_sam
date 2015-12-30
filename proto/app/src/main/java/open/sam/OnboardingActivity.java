package open.sam;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import javax.inject.Inject;

public class OnboardingActivity extends BaseActivity {
    private TextView _fNameTextView;
    private TextView _lNameTextView;
    private TextView _emailTextView;
    private TextView _nextButton;
    @Inject
    UserInfoHelper _userInfoHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _userInfoHelper = SamApplication.getComponent().provideUserInfoHelper();
        setContentView(R.layout.onboarding_screen);
        _fNameTextView = (TextView) findViewById(R.id.first_name);
        _lNameTextView = (TextView) findViewById(R.id.last_name);
        _emailTextView = (TextView) findViewById(R.id.email);
        _nextButton = (TextView) findViewById(R.id.next_button);
        setupNameTextView();
    }

    private void setupNameTextView() {
        _fNameTextView.setText(_userInfoHelper.getFirstName());
        _lNameTextView.setText(_userInfoHelper.getLastName());
        _fNameTextView.addTextChangedListener(getTextChangedListener());
        _lNameTextView.addTextChangedListener(getTextChangedListener());
    }

    private void setupEMailTextView() {
        _emailTextView.addTextChangedListener(getTextChangedListener());
        insertAccountEMailIntoTextViewIfAvailable();
    }

    private void insertAccountEMailIntoTextViewIfAvailable() {
        String userEmail = _userInfoHelper.getUserEmail();
        if (userEmail != null) {
            _emailTextView.setText(userEmail);
        }
    }

    private TextWatcher getTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int p, int p1, int p2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int p, int p1, int p2) {
                enableNextButtonIfTextFieldsAreNotEmpty();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    private void enableNextButtonIfTextFieldsAreNotEmpty() {
        boolean isEmailEmpty = TextUtils.isEmpty(_emailTextView.getText());
        boolean isFNameEmpty = TextUtils.isEmpty(_fNameTextView.getText());
        boolean isLNameEmpty = TextUtils.isEmpty(_lNameTextView.getText());
        boolean isEnabled = !isEmailEmpty && !isFNameEmpty && !isLNameEmpty;
        _nextButton.setEnabled(isEnabled);
    }
}