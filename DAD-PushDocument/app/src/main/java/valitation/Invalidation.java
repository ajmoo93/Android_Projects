package valitation;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.util.Patterns;

/**
 * Created by EM9310LI on 2017-09-25.
 */

public class Invalidation {
    private Context context;

    public Invalidation(Context context){this.context = context;}
    public boolean IsInputTextFilled(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message){
        String value = textInputEditText.getText().toString().trim();
        if(value.isEmpty()){
            textInputEditText.setError(message);
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    public boolean isInputTextEmail(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message){
        String value = textInputEditText.getText().toString().trim();
        if(value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            textInputEditText.setError(message);
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    public boolean isInputTextMatching(TextInputEditText textInputEditText, TextInputEditText textInputEditText2, TextInputLayout textInputLayout, String message){
        String value = textInputEditText.getText().toString().trim();
        String value2 = textInputEditText2.getText().toString().trim();
        if(!value.contains(value2)){
            textInputEditText.setError(message);
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    public boolean isPasswordNotHave(TextInputEditText textInputEditText,TextInputLayout textInputLayout, String message){
        String value = textInputEditText.getText().toString().trim();
        try {
            for (int i = 0; i < value.length(); i++)
                if (!Character.isLetterOrDigit(i)) {
                    textInputEditText.setError(message);
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
        }catch (Exception e){
            e.getStackTrace();
        }
        return true;
    }
    public boolean isPasswordLongerThenEight(TextInputEditText textInputEditText,TextInputLayout textInputLayout, String message){

        String value = textInputEditText.getText().toString().toString();
       try {
           if(value.length() < 8){
               textInputEditText.setError(message);
               return false;
           }else{
               textInputLayout.setErrorEnabled(false);
           }

       }catch (Exception e){
           e.getStackTrace();
       }
        return true;
    }
}
