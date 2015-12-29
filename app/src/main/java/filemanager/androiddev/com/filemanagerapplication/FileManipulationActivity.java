package filemanager.androiddev.com.filemanagerapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import filemanager.androiddev.com.filemanager.FileManager;

public class FileManipulationActivity extends AppCompatActivity implements View.OnClickListener {

    private String mFileLocation;
    private Button mButtonWriteToFile;
    private Button mButtonReadFromFile;
    private Button mButtonClearFileContent;
    private TextView mTextViewFileContent;
    private EditText mEdiTextFileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_write);
        initViews();
    }

    private void initViews() {

        mTextViewFileContent = (TextView) findViewById(R.id.tv_file_content);
        mEdiTextFileContent = (EditText) findViewById(R.id.et_file_content);

        mButtonWriteToFile = (Button) findViewById(R.id.btn_write_to_file);
        mButtonReadFromFile = (Button) findViewById(R.id.btn_read_from_file);
        mButtonClearFileContent = (Button) findViewById(R.id.btn_clear_file_content);

        setListeners();
    }

    private void setListeners() {
        mButtonWriteToFile.setOnClickListener(this);
        mButtonReadFromFile.setOnClickListener(this);
        mButtonClearFileContent.setOnClickListener(this);
    }


    private void writeInFile() {
        if(!mEdiTextFileContent.getText().toString().isEmpty()){
            String content = mEdiTextFileContent.getText().toString();
            mEdiTextFileContent.setText("");
            mFileLocation =  FileManager.writeTextInFile(content, this);
        }else{
            Toast.makeText(this,"Write some text first.", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this,"Content saved in the file", Toast.LENGTH_LONG).show();
    }

    private void readFromFile() {
        mTextViewFileContent.setText("");
        if(mFileLocation!= null){

            String content =    FileManager.readTextFile(mFileLocation);

            mTextViewFileContent.setText(content);
        }else{
            Toast.makeText(this,"Write some text and save it in the file.", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this,"File Content loaded ", Toast.LENGTH_LONG).show();
    }

    private void clearFileContent() {
        if(mFileLocation!=null) {
            FileManager.clearFileContent(mFileLocation);
            Toast.makeText(this,"File content was cleared.", Toast.LENGTH_LONG).show();
            mFileLocation = null;
            mTextViewFileContent.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_read_from_file:
                readFromFile();
                break;
            case R.id.btn_write_to_file:
                writeInFile();
                break;
            case R.id.btn_clear_file_content:
                clearFileContent();
                break;
        }
    }


}
