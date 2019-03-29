package com.example.vertonix.loginrequery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vertonix.loginrequery.database.UserDetailsEntity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.requery.Persistable;
import io.requery.reactivex.ReactiveEntityStore;


public class RegisterActivity extends AppCompatActivity {
    EditText firstname, lastname, userid, password,confirmpassword, email;
    Button register;
    private static final String TAG = "RegisterActivity";
    private ReactiveEntityStore<Persistable> data;

    //Password Validation
    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    //Email Validation
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        data=((ProductApplication)getApplication()).getData();


        firstname=(EditText)findViewById(R.id.et_First_Name);
        lastname=(EditText)findViewById(R.id.et_Last_Name);
        userid=(EditText)findViewById(R.id.et_User_Id);
        password=(EditText)findViewById(R.id.et_Password);
        confirmpassword=(EditText)findViewById(R.id.et_Confirm_Password);
        email=(EditText)findViewById(R.id.et_Email);

        register=(Button)findViewById(R.id.bt1register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstname.getText().toString().isEmpty() || firstname.getText().toString()==null){
                    Toast.makeText(RegisterActivity.this,"Please Enter the first Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (lastname.getText().toString().isEmpty() || lastname.getText().toString()==null){
                    Toast.makeText(RegisterActivity.this, "Please enter the last name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (userid.getText().toString().isEmpty() || userid.getText().toString() == null) {
                    Toast.makeText(RegisterActivity.this, "Please Enter the user name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.getText().toString().isEmpty() || password.getText().toString()==null) {
                    Toast.makeText(RegisterActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!password.getText().toString().isEmpty()){
                    if (isValidPassword(password.getText().toString().trim())){
                        if (confirmpassword.getText().toString().isEmpty() || confirmpassword.getText().toString()==null){
                            Toast.makeText(RegisterActivity.this, "Please enter confirm password", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else if (!confirmpassword.getText().toString().isEmpty()){
                            if (isValidPassword(confirmpassword.getText().toString().trim())){
                                if (password.getText().toString().equals(confirmpassword.getText().toString())){
                                    //Toast.makeText(RegisterActivity.this,"Registration successful", Toast.LENGTH_LONG).show();
                                   // Intent loginIntent=new Intent(RegisterActivity.this,MainActivity.class);
                                    //startActivity(loginIntent);
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this,"Password does not match", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                            else{
                                Toast.makeText(RegisterActivity.this,"Confirm Password must contain min 8 chars", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Password must contain min 8 chars", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    saveUserDetails();
                }

                //Email checking
                String emailid= email.getText().toString();
                if(!checkEmail(emailid)){
                    Toast.makeText(RegisterActivity.this,"Please enter valid mail id", Toast.LENGTH_LONG).show();
                    return;
                }
                 if(emailid != ""){
//                    Toast.makeText(RegisterActivity.this,"Already exist", Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });
    }

    //Data storage
    public void saveUserDetails(){
        UserDetailsEntity entity=new UserDetailsEntity();

        entity.setUserId(userid.getText().toString());
        entity.setFirstName(firstname.getText().toString());
        entity.setLastName(lastname.getText().toString());
        entity.setPassword(password.getText().toString());
        entity.setConfirmPassword(confirmpassword.getText().toString());
        entity.setEmail(email.getText().toString());


        data.upsert(entity).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<UserDetailsEntity>()
         {
            @Override
            public void accept(UserDetailsEntity userDetailsEntity) throws Exception {
                Toast.makeText(RegisterActivity.this,"Insert Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
