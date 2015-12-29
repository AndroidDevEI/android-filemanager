package filemanager.androiddev.com.filemanagerapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button fileSystemOperations = (Button) findViewById(R.id.btn_file_system);
        Button fileManipulations = (Button) findViewById(R.id.btn_file_manipulation);

        fileSystemOperations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FileSystemOperationsActivity.class);
                startActivity(intent);


            }
        });

        fileManipulations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FileManipulationActivity.class);
                startActivity(intent);
            }
        });



    }
}
