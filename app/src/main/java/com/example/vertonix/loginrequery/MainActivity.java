package com.example.vertonix.loginrequery;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.example.vertonix.loginrequery.database.UserDetailsEntity;
        import io.requery.Persistable;
        import io.requery.reactivex.ReactiveEntityStore;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button login;
    TextView Register;
    private ReactiveEntityStore<Persistable> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=(EditText)findViewById(R.id.et_User_Name);
        password=(EditText)findViewById(R.id.et_Password);
        login=(Button)findViewById(R.id.bt1_Login);
        Register=(TextView)findViewById(R.id.tv1);
        data=((ProductApplication)getApplication()).getData();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || username.getText().toString() == null){
                    Toast.makeText(MainActivity.this, "Please Enter the User name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (password.getText().toString().isEmpty() || password.getText().toString() == null) {
                    Toast.makeText(MainActivity.this, "Please Enter the Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                login();
            }
        });

//For Register page
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

    }
    //Method for
    public void login(){
        UserDetailsEntity entities = data.select(UserDetailsEntity.class).get().firstOrNull();
        if (entities.getUserId().equals(username.getText().toString()) && entities.getPassword().equals(password.getText().toString())){
            Intent registerIntent = new Intent(MainActivity.this, NavigationDrawer.class);
            startActivity(registerIntent);
        }
        else {
            Toast.makeText(MainActivity.this, "Please Enter valid credentials ", Toast.LENGTH_SHORT).show();
        }
    }
}
