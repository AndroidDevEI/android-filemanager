package filemanager.androiddev.com.filemanagerapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FileSystemOperationsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mButtonDelete;
    private Button mButtonCreate;
    private Button mButtonRename;

    private EditText mEditTextCreateFile;
    private EditText mEditTextCurrentName;
    private EditText mEditTextNewName;
    private EditText mEditTextDeleteFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_system_operations);

        initViews();

    }

    private void initViews(){

        mEditTextCreateFile = (EditText) findViewById(R.id.et_create_file);
        mEditTextDeleteFile = (EditText) findViewById(R.id.et_delete_file);
        mEditTextCurrentName = (EditText) findViewById(R.id.et_rename_file_current_name);
        mEditTextNewName = (EditText) findViewById(R.id.et_rename_file_new_name);

        mButtonCreate = (Button) findViewById(R.id.btn_create);
        mButtonDelete = (Button) findViewById(R.id.btn_delete);
        mButtonRename = (Button) findViewById(R.id.btn_rename);

        setListeners();
    }

    private void setListeners(){
        mButtonCreate.setOnClickListener(this);
        mButtonDelete.setOnClickListener(this);
        mButtonRename.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btn_create:
                createFile();
                break;
            case R.id.btn_delete:
                 deleteFile();
                break;
            case R.id.btn_rename:
                renameFile();
                break;
        }
    }


    private void createFile(){
        Toast.makeText(this, "File Created", Toast.LENGTH_LONG).show();
    }

    private void deleteFile(){
        Toast.makeText(this, "File Deleted", Toast.LENGTH_LONG).show();
    }

    private void renameFile(){
        Toast.makeText(this, "File Renamed", Toast.LENGTH_LONG).show();
    }



}
