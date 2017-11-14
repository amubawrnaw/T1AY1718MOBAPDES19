package mobapde.mobapdemp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {
    EditText name, pass, email;
    Button ca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.reg_name);
        pass = (EditText) findViewById(R.id.reg_pass);
        email = (EditText) findViewById(R.id.reg_email);

        ca = (Button) findViewById(R.id.createaccount);
        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbh = new DatabaseHelper(getBaseContext());
                Account a = new Account();
                a.setEmail(email.getText().toString());
                a.setName(name.getText().toString());
                a.setPassword(pass.getText().toString());

                long l = dbh.addAccount(a);

                if(l!=-1){
                    Intent i = new Intent();
                    i.setClass(getBaseContext(), success_login.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getBaseContext(), "Error: Email Already Used", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
