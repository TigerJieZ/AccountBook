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
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddBillActivity extends AppCompatActivity {
    EditText inputCategory,inputAmount,inputExplain;
    Button button_submit,button_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        initView();
    }

    private void initView() {
        inputCategory=findViewById(R.id.editText_bill_category);
        inputAmount=findViewById(R.id.editText_bill_amount);
        inputExplain=findViewById(R.id.editText_bill_explain);

        button_submit=findViewById(R.id.button_addBill_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String category=inputCategory.getText().toString();
                final String amount=inputAmount.getText().toString();
                final String explain=inputExplain.getText().toString();
                Date day=new Date();
                //2018-05-16 22:58:06
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final String createDate=df.format(day);
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
                            String query="INSERT INTO bill(email,date,category,explain,amount) VALUES("+"'"+SharedPrefUtility.user.getEmail()+"',"+"'"+createDate
                                    +"',"+"'"+category+"',"+"'"+explain+"',"+"'"+amount+"'"+")";
                            System.out.println(query);
                            statement.executeUpdate(query);
                            Intent intent=new Intent(AddBillActivity.this,MainActivity.class);
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

        button_cancel=findViewById(R.id.button_addBill_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddBillActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
