package com.bobgranata.linkservice.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bobgranata.linkservice.Database.DatabaseHandler;
import com.bobgranata.linkservice.Adapters.ListRolesAdapter;
import com.bobgranata.linkservice.Models.RolesModel;
import com.bobgranata.linkservice.R;
import com.bobgranata.linkservice.Tasks.LoginTask;

import java.util.List;

//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//AppCompatActivity
public class LoginActivity extends Activity {

    EditText mmEtLogin;
    ProgressBar mmProgressBar;
    static ListView mmLvRoleList;
    List<RolesModel> mmListRoles;
    TextView mmTvLoginChecker;
    TextView mmTvNoOneRole;
    static Context mmContext;

    boolean mmLooongClick;

    public final static String ACESS_KEY = "ACCESS_KEY";
    public final static String ID_ROLE = "ID_ROLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mmContext = LoginActivity.this;

        mmLooongClick = false;
        // получаем экземпляр элемента ListView
        mmLvRoleList = (ListView)findViewById(R.id.lvRoleList);

        mmLvRoleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                if (!mmLooongClick) {
                    Intent intent = new Intent(LoginActivity.this,
                            InfComActivity.class);
                    String sAccessKey = mmListRoles.get(position).getAccessKey();
                    String sIdRole = mmListRoles.get(position).getID();
                    intent.putExtra(ACESS_KEY, sAccessKey);
                    intent.putExtra(ID_ROLE, sIdRole);
                    startActivity(intent);
                }
            }
        });

//        mmLvRoleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                mmLooongClick = true;
//                AlertDialog.Builder builder = new AlertDialog.Builder(mmContext);
//                builder.setTitle("Удаление");
//                builder.setMessage("Удалить выбранную организацию?");
//                builder.setCancelable(true);
//                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss(); // Отпускает диалоговое окно
//                        mmLooongClick = false;
//                    }
//                });
//                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int arg1) {
//                        mmLooongClick = false;
//                    }
//                });
//                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    public void onCancel(DialogInterface dialog) {
//                        mmLooongClick = false;
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//                return false;
//            }
//        });

        mmEtLogin = (EditText) findViewById(R.id.etLogin);
        mmEtLogin.addTextChangedListener(loginWatcher);
        mmTvLoginChecker = (TextView) findViewById(R.id.tvLoginChecker);
        mmTvNoOneRole = (TextView) findViewById(R.id.tvNoOneRole);
        mmProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mmProgressBar.setVisibility(View.INVISIBLE);

        DatabaseHandler db = new DatabaseHandler(this);
        if (db.getRolesCount() > 0) {
            mmTvNoOneRole.setVisibility(View.GONE);
            mmListRoles = db.getAllRoles();
            // используем кастомный адаптер данных
            ListRolesAdapter adapter = new ListRolesAdapter(this, mmListRoles);

            mmLvRoleList.setAdapter(adapter);
        } else {
            mmTvNoOneRole.setVisibility(View.VISIBLE);
        }
    }

    private final TextWatcher loginWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mmTvLoginChecker.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                mmTvLoginChecker.setVisibility(View.GONE);
            } else if (s.length() < 8 || s.length() > 8) {
                mmTvLoginChecker.setText("Длинна кода должна быть 8 символов");
            } else {
                mmTvLoginChecker.setVisibility(View.GONE);
            }
        }
    };

    public void btnLoginClick(View view) {

        String loginKey = mmEtLogin.getText().toString();
        if (!loginKey.equals("")) {
            mmProgressBar.setVisibility(View.VISIBLE);
            LoginTask loginTask = new LoginTask(this);
            loginTask.execute(loginKey);
        } else {
            mmTvLoginChecker.setVisibility(View.VISIBLE);
            mmTvLoginChecker.setText("Заполните поле");
        }
    }

    public void updateRoleList(List<RolesModel> listRoles) {
        mmProgressBar.setVisibility(View.INVISIBLE);

        mmEtLogin.setText("");

        DatabaseHandler db = new DatabaseHandler(this);

        if (listRoles.size() != 0) {
            mmTvNoOneRole.setVisibility(View.GONE);
            for (RolesModel role : listRoles) {
                db.addRole(role);
                String log = "Id: "+role.getID()+" ,Name: " + role.getName() + " ,AccessKey: " + role.getAccessKey();
                System.out.println(log);

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Организация " + role.getName() + " добавлена в список" , Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Организация с таким кодом не найдена." , Toast.LENGTH_SHORT);
            toast.show();
        }

//        db.deleteAll();

        mmListRoles = db.getAllRoles();
        // используем кастомный адаптер данных
        ListRolesAdapter adapter = new ListRolesAdapter(this, mmListRoles);

        mmLvRoleList.setAdapter(adapter);

        mmLvRoleList.requestFocus();
    }

    public static void updateListView(List<RolesModel> updateListToles) {
          ListRolesAdapter adapter = new ListRolesAdapter(mmContext, updateListToles);
          mmLvRoleList.setAdapter(adapter);
    }

    public static void deleteRole(final String idRole) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mmContext);
        builder.setTitle("Удаление");
        builder.setMessage("Удалить выбранную организацию из списка?");
        builder.setCancelable(true);

        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(mmContext, "Отмена", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHandler db = new DatabaseHandler(mmContext);
                db.deleteRole(idRole);
                ListRolesAdapter adapter = new ListRolesAdapter(mmContext, db.getAllRoles());
                mmLvRoleList.setAdapter(adapter);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
