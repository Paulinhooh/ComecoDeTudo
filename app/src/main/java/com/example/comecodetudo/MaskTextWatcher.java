package com.example.comecodetudo;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MaskTextWatcher implements TextWatcher {

    private final String mask; // ex: "##/##/####" ou "###.###.###-##"
    private final EditText editText;
    private boolean isUpdating = false;
    private String old = "";

    public MaskTextWatcher(EditText editText, String mask) {
        this.mask = mask;
        this.editText = editText;
    }

    private String onlyDigits(String s) {
        return s.replaceAll("[^0-9]", "");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* não usado */ }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isUpdating) {
            old = onlyDigits(s.toString());
            isUpdating = false;
            return;
        }

        String digits = onlyDigits(s.toString());
        StringBuilder formatted = new StringBuilder();

        int digitIndex = 0;
        for (int i = 0; i < mask.length(); i++) {
            char m = mask.charAt(i);
            if (m == '#') {
                if (digitIndex < digits.length()) {
                    formatted.append(digits.charAt(digitIndex));
                    digitIndex++;
                } else {
                    break; // sem mais dígitos
                }
            } else {
                // caractere literal do formato (., /, -)
                if (digitIndex <= digits.length()) {
                    formatted.append(m);
                }
            }
        }

        isUpdating = true;
        editText.setText(formatted.toString());
        // posiciona o cursor no final (útil e simples)
        try {
            editText.setSelection(formatted.length());
        } catch (Exception e) {
            // caso dê problema, ignora
        }
    }

    @Override
    public void afterTextChanged(Editable s) { /* não usado */ }
}
