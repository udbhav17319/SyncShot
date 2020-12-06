package com.example.syncshot.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.syncshot.InvitePeople;
import com.example.syncshot.MainActivity;
import com.example.syncshot.R;
import com.example.syncshot.ui.login.LoginViewModel;
import com.example.syncshot.ui.login.LoginViewModelFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import okhttp3.ConnectionSpec;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    TextView txtString;

    public String url= "https://10.0.2.2";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });


        txtString= (TextView)findViewById(R.id.ok_text);


        Button button = findViewById(R.id.ok_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doGetRequest();
                    }
                }).start();
            }
        });


        Button button2=findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        domultipartrequest();
                    }
                }).start();
            }
        });
    }

    private void domultipartrequest(){
        String path= Environment.getExternalStorageDirectory().toString()+"/Pictures";
        Log.d("folder path","="+ path);
        File f=new File(path);
        Log.d("f","="+ f);
        File[] files =f.listFiles();
        Log.d("files","="+ files);
        for (int i=0;i<files.length;i++){
            if(files[i].isFile()){
                Log.d("filename:", "="+ files[i].getName());
                sendImage(files[i]);
                break;
            }
        }

    }
    private void sendImage(File file){
        Log.d("api calling funct","function called");
        String url="https://api.remove.bg/v1.0/removebg/";
        OkHttpClient client = new OkHttpClient();
        Log.d("client created","client created");
        RequestBody body= new MultipartBody.Builder()
                .addFormDataPart("image",file.getName(),RequestBody.create(MediaType.parse("image/jpeg"),file))
                .build();
        Log.d("request body created","request body created");
        Request newRequest = new Request.Builder()
                .addHeader("X-Api-Key", "gpe797ndqNcuhwrvtPE7Cufv")
                .url(url)
                .post(body)
                .build();
        Log.d("request created","request created");
        try {
            Response response= client.newCall(newRequest).execute();
            Log.d("got response",response.body().string());
//            InputStream inpstream=response.body().byteStream();
//            Bitmap btmap= BitmapFactory.decodeStream(inpstream);
            FileOutputStream fos = new FileOutputStream("d:/bgremovedimage.png");
            fos.write(response.body().bytes());
            fos.close();
            Log.d("got response","written to storage");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("getrequest Failed","getrequest");
        }
    }

    private void  doGetRequest(){
        Log.d("getrequest_funct_called","function called");
        String url="http://10.0.2.2:5000/";
        OkHttpClient client = new OkHttpClient();
        Log.d("client created","client created");
        Request newRequest = new Request.Builder()
                .url(url)
                .build();
        Log.d("request created","request created");
        try {
            Response response= client.newCall(newRequest).execute();
            Log.d("got response",response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("getrequest Failed","getrequest");
        }
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}