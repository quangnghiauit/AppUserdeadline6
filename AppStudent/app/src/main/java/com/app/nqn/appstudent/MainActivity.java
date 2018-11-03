package com.app.nqn.appstudent;

//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.provider.MediaStore;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;

//import com.app.nqn.appstudent.data.DBManager;
//import com.app.nqn.appstudent.model.User;

//public class MainActivity extends AppCompatActivity {
//
//    ImageView imgAvatar;
//    Button btnChange;
//    int REQUEST_CODE= 123;
//
//    private EditText editName;
//    private EditText editEmail;
//    private EditText editPhoneNumber;
//
//    private Button btnSave;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        imgAvatar = (ImageView) findViewById(R.id.iv_logo);
//        btnChange= (Button) findViewById(R.id.btn_change);
//
//
//
//
//        btnChange.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,REQUEST_CODE);
//
//            }
//        });
//
//
//        final DBManager dbManager = new DBManager(this);
//        editName = (EditText) findViewById(R.id.edt_name);
//        editEmail= (EditText) findViewById(R.id.edt_email);
//        editPhoneNumber=(EditText) findViewById(R.id.edt_phone);
//        btnSave=(Button) findViewById(R.id.btn_save);
//
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                User user = createUser();
//                if(user != null) {
//                    dbManager.addUser(user);
//                }
//
//
//
//
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode==REQUEST_CODE&& resultCode==RESULT_OK) {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imgAvatar.setImageBitmap(bitmap);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private User createUser() {
//        String name = editName.getText().toString();
//        String email= editEmail.getText().toString();
//        String phone = editPhoneNumber.getText().toString();
//
//        User user = new User(name,email,phone);
//
//        return user;
//
//    }
//}



import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final int READ_BLOCK_SIZE = 1000;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    EditText editTextName;
    EditText editTextEmal;
    EditText editTextPhone;
    Button buttonChange;
    Button buttonSave;
    RadioButton radioButtonFemale, radioButtonMale;

    ImageView img;

    Intent intent;
    Uri file;
    public static final int LAUNCH_CAM = 2;
    private static final int REQUEST_ID_IMAGE_CAPTURE = 100;
    String Path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.edt_name);
        editTextEmal = findViewById(R.id.edt_email);
        editTextPhone = findViewById(R.id.edt_phone);

        buttonChange = findViewById(R.id.btn_change);
        buttonSave = findViewById(R.id.btn_save);
        radioButtonMale = findViewById(R.id.rdbMale);
        radioButtonFemale = findViewById(R.id.rdbFemale);
        img = (ImageView) findViewById(R.id.iv_logo);

        buttonSave.setOnClickListener(this);
        buttonChange.setOnClickListener(this);

        ReadData();
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");
        Log.d("FILELOAD",mypath.getPath());
        loadImageFromStorage(mypath.getPath());
    }

    void ReadData() {
        try {
            FileInputStream fileIn = openFileInput("textfile.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();

            String[] ListText = s.split("-");

            editTextName.setText(ListText[0]);
            editTextEmal.setText(ListText[1]);
            editTextPhone.setText(ListText[2]);

            if (ListText[3].toString().equals("1"))
                radioButtonFemale.setChecked(true);
            if (ListText[3].toString().equals("2"))
                radioButtonMale.setChecked(true);

            //  Toast.makeText(this,ListText[3].toString(),Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void WriteData() {
        try {
            FileOutputStream fileout = openFileOutput("textfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            String str = editTextName.getText() + "-" + editTextEmal.getText() + "-" + editTextPhone.getText() + "-";
            if (radioButtonFemale.isChecked() == true)
                str += "1";
            else str += "2";
            outputWriter.write(str);
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String SaveImage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        File mypath = new File(directory, "profile.jpg");
        Log.d("FILE",mypath.getPath());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);

            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(this, "Save image success", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private void loadImageFromStorage(String path) {

        try {
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(path));
            img.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            WriteData();
        }

        if (v.getId() == R.id.btn_change) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


            this.startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                img.setImageBitmap(bp);
                SaveImage(bp);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action canceled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}
