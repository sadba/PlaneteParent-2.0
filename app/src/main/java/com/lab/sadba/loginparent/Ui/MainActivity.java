package com.lab.sadba.loginparent.Ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.sadba.loginparent.Common.Common;
import com.lab.sadba.loginparent.Model.PostRegisterUser;
import com.lab.sadba.loginparent.Model.PostUser;
import com.lab.sadba.loginparent.Model.PostVerifUser;
import com.lab.sadba.loginparent.Model.RegisterUser;
import com.lab.sadba.loginparent.Model.User;
import com.lab.sadba.loginparent.Model.VerifUser;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Remote.IMyAPI;
import com.rengwuxian.materialedittext.MaterialEditText;

import dmax.dialog.SpotsDialog;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView txt_register, txt_verif;
    EditText edt_ien, edt_password;
    Button btn_login;
    SharedPreferences sp;
    SharedPreferences sp1;
    IMyAPI mService;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init Service
        mService = Common.getAPI();

        Realm.init(this);

        //Init View
        txt_verif = findViewById(R.id.txt_verif);
        edt_ien = findViewById(R.id.edt_ien);
        //edt_ien.setTextIsSelectable(true);
       // edt_ien.setFocusable(false);
       // edt_ien.setFocusableInTouchMode(false);
        //edt_ien.setFocusableInTouchMode(false);
        edt_password = findViewById(R.id.edt_password);
       // edt_password.setTextIsSelectable(true);
       // edt_password.setFocusable(false);
       // edt_password.setFocusableInTouchMode(false);
        btn_login = findViewById(R.id.btn_login);



        sp = getSharedPreferences("btn_login", MODE_PRIVATE);
        sp1 = getSharedPreferences("ien_parent", MODE_PRIVATE);

        if (sp.getBoolean("logged", false)){
            gotToHomeActivity();
        }

        //Event
        txt_verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, VerifActivity.class));
                showVerifDialog();
            }
        });



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ien = edt_ien.getText().toString();
                String password = edt_password.getText().toString();
                if (TextUtils.isEmpty(password)){
                    edt_password.setError("Mot de passe ne pas etre vide");
                } else if (TextUtils.isEmpty(ien)){
                    edt_ien.setError("Ien ne doit pas etre vide");
                } else {
                    authenticateUser(ien, password);
                    edt_ien.getText().clear();
                    edt_password.getText().clear();
                }

            }
        });
    }

    private void showVerifDialog() {
        View view = getLayoutInflater().inflate(R.layout.verif_layout, null);

        final MaterialEditText edt_ien = (MaterialEditText) view.findViewById(R.id.edt_ienChild);
        final MaterialEditText edt_cni = (MaterialEditText) view.findViewById(R.id.edt_id_card);

        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setView(view)
                .setTitle("Verification des données")
                .setPositiveButton("VERIFIER", null)
                .setNegativeButton("ANNULER", null)
                .create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonPositive = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //alertDialog.dismiss();
                        String ien = edt_ien.getText().toString();
                        String cni = edt_cni.getText().toString();

                        if (TextUtils.isEmpty(ien)){
                            edt_ien.setError("L'IEN ne doit pas etre vide");
                           // Toast.makeText(MainActivity.this, "yup", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(cni)){
                            edt_cni.setError("Le CNI ne doit pas etre vide");
                            return;
                        } else {
                            PostVerifUser postVerifUser = new PostVerifUser();
                            postVerifUser.setIen(ien);
                            postVerifUser.setCni(cni);

                            final android.app.AlertDialog watingDialog = new SpotsDialog(MainActivity.this);
                            watingDialog.show();
                            watingDialog.setTitle("En cours...");
                            mService.verifUser(postVerifUser)
                                    .enqueue(new Callback<VerifUser>() {
                                        @Override
                                        public void onResponse(Call<VerifUser> call, Response<VerifUser> response) {
                                            VerifUser result = response.body();
                                            if (result.getCode().equals("1")){
                                                //progressDoalog.dismiss();
                                                Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();
                                            } else {
                                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("ien_Parent", result.getIen_parent());
                                                editor.apply();
                                                alertDialog.dismiss();
                                                watingDialog.dismiss();
                                                showRegisterDialog();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<VerifUser> call, Throwable t) {
                                            // progressDoalog.dismiss();
                                            watingDialog.dismiss();
                                            Toast.makeText(MainActivity.this, "Veuillez vérifier votre connection", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    }
                });

                Button buttonNegative = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
            }
        });
        alertDialog.show();
    }




    private void showRegisterDialog() {

        View view = getLayoutInflater().inflate(R.layout.register_layout, null);

        final AlertDialog alertDialog1 = new AlertDialog.Builder(MainActivity.this)
                .setView(view)
                .setPositiveButton("VERIFIER", null)
                .setNegativeButton("ANNULER", null)
                .create();

        final MaterialEditText edt_ien = (MaterialEditText) view.findViewById(R.id.edt_ien_parent);
        final MaterialEditText edt_code = (MaterialEditText) view.findViewById(R.id.edt_sms_code);
        final MaterialEditText edt_password = (MaterialEditText) view.findViewById(R.id.edt_password_register);
        final MaterialEditText edt_confirm = (MaterialEditText) view.findViewById(R.id.edt_confirm_register);

        alertDialog1.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonPositive = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //alertDialog1.dismiss();
                        String ien = edt_ien.getText().toString();
                        String code_verif = edt_code.getText().toString();
                        String password = edt_password.getText().toString();
                        String confirm_password = edt_confirm.getText().toString();
                        if (TextUtils.isEmpty(ien)){
                            edt_ien.setError("L'IEN ne doit pas etre vide");
                            return;
                        } else if (TextUtils.isEmpty(code_verif)){
                            edt_code.setError("Le CNI ne doit pas etre vide");
                            return;
                        } else if (TextUtils.isEmpty(password) && !isPasswordValid(password)){
                            edt_password.setError("Mot de passe invalide");
                            return;
                        } else if (!password.equals(confirm_password)) {
                            edt_confirm.setError("Les mots de passe ne sont pas identiques");
                            return;
                        } else {
                            PostRegisterUser postRegisterUser = new PostRegisterUser();
                            postRegisterUser.setIen(ien);
                            postRegisterUser.setCode_verif(code_verif);
                            postRegisterUser.setPassword(password);

                            final android.app.AlertDialog watingDialog = new SpotsDialog(MainActivity.this);
                            watingDialog.show();
                            watingDialog.setTitle("En cours...");
                            // progressDoalog = new ProgressDialog(RegisterActivity.this);
                            // progressDoalog.setMessage("Verification des donnees...");
                            //progressDoalog.show();
                            mService.registerUser(postRegisterUser)
                                    .enqueue(new Callback<RegisterUser>() {
                                        @Override
                                        public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {

                                            RegisterUser result = response.body();
                                            if (result.getCode().equals("1")) {
                                                //progressDoalog.dismiss();
                                                Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();
                                            } else {

                                                watingDialog.dismiss();
                                                //startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                                //show.dismiss();
                                                alertDialog1.dismiss();
                                                Toast.makeText(MainActivity.this, "Compte crée avec Succes", Toast.LENGTH_SHORT).show();

                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<RegisterUser> call, Throwable t) {
                                            //progressDoalog.dismiss();
                                            watingDialog.dismiss();
                                            Toast.makeText(MainActivity.this, "Veuillez vérifier votre connection", Toast.LENGTH_LONG).show();

                                        }
                                    });

                        }
                    }
                });

                Button buttonNegative = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog1.cancel();
                    }
                });
            }
        });
        alertDialog1.show();




    }



    private void gotToHomeActivity() {
        Intent i = new Intent(this, EnfantActivity.class);
        startActivity(i);
    }



    private void authenticateUser(String ien, String password) {
        PostUser postUser = new PostUser();
        postUser.setPassword(password);
        postUser.setIen(ien);
        mService.loginUser(postUser)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        User result = response.body();
                        if (result.getCode().equals("1")) {
                            Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            //startActivity(new Intent(MainActivity.this, EnfantActivity.class));

                            gotToHomeActivity();
                            sp.edit().putBoolean("logged", true).apply();
                            //SharedPreferences.Editor editor = sp.edit();
                            //editor.remove("logged");
                           // editor.commit();
                            //finish();
                            //sp1.edit().putString("ien_parent", "").apply();
                            realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            realm.copyToRealm(response.body());
                            realm.commitTransaction();

                        }


                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean isPasswordValid(String password) {

        return password.length() >= 6;
    }
}
