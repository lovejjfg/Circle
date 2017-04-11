package com.lovejjfg.circle.view;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Joe on 2017/4/2..
 * Email lovejjfg@gmail.com
 */


public class ViewUtils {
    public static void disableButton(final TextView button, final EditText... texts) {
        button.setEnabled(false);
        for (final EditText text : texts) {
            text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s.toString())) {
                        button.setEnabled(false);
                    } else {
                        for (EditText text : texts) {
                            if (TextUtils.isEmpty(text.getText().toString())) {
                                button.setEnabled(false);
                                return;
                            }
                            button.setEnabled(true);
                        }
                    }
                }
            });
        }
    }
}
