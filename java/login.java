package mobapde.mobapdemp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    EditText email, pass;
    Button login, reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);
        reg =  (Button) findViewById(R.id.SignUp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account a = new Account();
                a.setEmail(email.getText().toString());
                a.setPassword(pass.getText().toString());

                DatabaseHelper dbh = new DatabaseHelper(getBaseContext());

                Account login = dbh.login(a);

                if(login!=null){
                    Intent i = new Intent();
                    i.setClass(getBaseContext(), success_login.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getBaseContext(),"Invalid Email / Password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), register.class);

                startActivity(i);
            }
        });

    }
}
