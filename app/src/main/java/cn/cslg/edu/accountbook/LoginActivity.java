package cn.cslg.edu.accountbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {
    Button button_login_register,button_login_login;
    EditText editTextEmail,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
    }

    private void initView() {
        button_login_register=findViewById(R.id.button_login_register);
        button_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        editTextEmail=findViewById(R.id.editText_login_email);
        editTextPassword=findViewById(R.id.editText_login_password);

        button_login_login=findViewById(R.id.button_login_login);
        button_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable networkTask = new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            Class.forName("com.mysql.jdbc.Driver");
                            System.out.println("数据库驱动成功");
                        } catch (ClassNotFoundException e1)
                        {
                            e1.printStackTrace();
                            System.out.println("加载数据库引擎失败");
                        }

                        try
                        {
                            Connection connection = DriverManager.getConnection(
                                    "jdbc:mysql://192.168.137.1:3306/accountbook" +
                                            "?useUnicode=true&amp;characterEncoding=utf-8", "root",
                                    "");
                            Statement statement=connection.createStatement();
                            String inputEmail=editTextEmail.getText().toString();
                            String inputPassword=editTextPassword.getText().toString();
                            String query="SELECT * FROM user WHERE email='"+inputEmail+"' AND" +
                                    " password='"+inputPassword+"'";
                            System.out.println(query);
                            ResultSet resultSet=statement.executeQuery(query);

                            while(resultSet.next()){
                                try{
                                    String name=resultSet.getString("name");
                                    String email=resultSet.getString("email");
                                    String gender=resultSet.getString("gender");
                                    String tel=resultSet.getString("tel");
                                    //保存登录状态
                                    SharedPrefUtility.setParam(LoginActivity.this, SharedPrefUtility.IS_LOGIN, true);
                                    //保存登录个人信息
                                    SharedPrefUtility.setParam(LoginActivity .this, SharedPrefUtility.LOGIN_DATA, name);
                                    SharedPrefUtility.user=new User(name,email,gender,tel);
                                }catch (Exception e){
                                    return;
                                }
                            }
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        } catch (SQLException e)
                        {
                            e.printStackTrace();
                            System.out.println("数据库连接错误");
                        }
                    }
                };
                Thread thread=new Thread(networkTask);
                thread.start();
            }
        });
    }

}
