package com.example.shobh.widgetsqli;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText your_name,to_update_id;
    Button submission,view_data,update_data;

    public static DatabaseHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb= new DatabaseHelper(this);

        your_name=findViewById(R.id.name);
        submission=findViewById(R.id.submit);
        view_data=findViewById(R.id.find);
        to_update_id=findViewById(R.id.student_id);
        update_data=findViewById(R.id.delete);

        addData();
        viewAll();
        updateData();
    }

    public void updateData(){
        update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isupdate = mydb.updateData(to_update_id.getText().toString(),your_name.getText().toString());
                if(isupdate==true)
                    Toast.makeText(MainActivity.this,"updated successfully",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"error in updating data",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addData(){
        submission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isinserted= mydb.insertData(your_name.getText().toString());
                if(isinserted==true)
                    Toast.makeText(MainActivity.this,"inserted successfully",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"error in inserting data",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewAll(){
        view_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res= mydb.getData();
                if(res.getCount()==0){
                    //show some message
                    showmesage("error","no data found in database");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("ID : "+ res.getString(0)+"\n");
                    buffer.append("NAME : "+ res.getString(1)+"\n");
                }
                //show all data
                showmesage("data",buffer.toString());
            }
        });
    }
    public void showmesage(String title,String message){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}
