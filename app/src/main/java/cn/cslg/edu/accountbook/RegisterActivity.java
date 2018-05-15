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
import android.widget.RadioButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextName,editTextTel,editTextEmail,editTextPassword,editTextPasswordConfirm;
    Button buttonSubmit,buttonCancel;
    RadioButton radioButtonMale,radioButtonFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

    public boolean checkError(String inputName,String inputGender,String inputTel,String inputEmail,String inputPassword,String inputPasswordConfirm){
        // 有输入为空时
        if(checkEmpty(new String[]{inputName,inputGender,inputTel,inputEmail,inputPassword,inputPasswordConfirm})){
            Toast.makeText(RegisterActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
            return true;
        }

        // 检查两次密码输入是否不同
        if(!inputPassword.equals(inputPasswordConfirm)){
            return true;
        }

        // 检查邮箱格式是否合法
        if(!isEmail(inputEmail)){
            return true;
        }

        return false;
    }

    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

    public boolean checkEmpty(String[] strList){
        for (String str:strList
                ) {
            if(str.equals("")||str==null)
                return true;
        }
        return false;
    }

    private void initView() {
        editTextName=findViewById(R.id.editText_register_name);
        editTextTel=findViewById(R.id.editText_register_tel);
        editTextEmail=findViewById(R.id.editText_register_email);
        editTextPassword=findViewById(R.id.editText_register_password);
        editTextPasswordConfirm=findViewById(R.id.editText_register_password_confirm);
        radioButtonMale=findViewById(R.id.radio_register_male);
        radioButtonFemale=findViewById(R.id.radio_register_female);

        buttonSubmit=findViewById(R.id.button_register_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String inputName=editTextName.getText().toString();
                final String inputGender;
                if(radioButtonMale.isChecked()){
                    inputGender="男";
                }else if (radioButtonFemale.isChecked()){
                    inputGender="女";
                }else{
                    Toast.makeText(RegisterActivity.this,"请选择性别",Toast.LENGTH_SHORT).show();
                    return;
                }
                final String inputTel=editTextTel.getText().toString();
                final String inputEmail=editTextEmail.getText().toString();
                final String inputPassword=editTextPassword.getText().toString();
                String inputPasswordConfirm=editTextPasswordConfirm.getText().toString();

                // 检查是否有错
                if(checkError(inputName,inputGender,inputTel,inputEmail,inputPassword,inputPasswordConfirm)){
                    return;
                }
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
                            String query="INSERT INTO user VALUES("+"'"+inputName+"',"+"'"+inputEmail
                                    +"',"+"'"+inputTel+"',"+"'"+inputPassword+"',"+"'"+inputGender+"'"+")";
                            System.out.println(query);
                            statement.executeUpdate(query);
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
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
