package cn.cslg.edu.accountbook;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    ListView listView;

    private List<Bill> billList = new ArrayList<>();
    Button button_addBill,button_search;

    Connection connection;
    Thread thread;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(SharedPrefUtility.user==null){
            SharedPrefUtility.setParam(MainActivity.this, SharedPrefUtility.IS_LOGIN, false);
            SharedPrefUtility.removeParam(MainActivity.this, SharedPrefUtility.LOGIN_DATA);
        }
        if (!(Boolean) SharedPrefUtility.getParam(MainActivity.this, SharedPrefUtility.IS_LOGIN, false)) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        user=SharedPrefUtility.user;

        loadBillList();
        while(thread.isAlive()){

        }

        initView();
    }

    private void loadBillList() {
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

                    connection = DriverManager.getConnection(
                            "jdbc:mysql://192.168.137.1:3306/accountbook?autoReconnect=true", "root",
                            "");
                    System.out.println("test");
                    Statement statement=connection.createStatement();
                    if(user!=null){
                        String query="SELECT * FROM bill where email='"+user.getEmail()+"'";
                        System.out.println(query);
                        ResultSet resultSet=statement.executeQuery(query);
                        while(resultSet.next()){
                            String category=resultSet.getString("category");
                            String date=resultSet.getString("date");
                            String explain=resultSet.getString("explains");
                            String amount=resultSet.getString("amount");
                            Bill temp=new Bill(category,amount,date,explain);
                            billList.add(temp);
                        }
                    }

                } catch (SQLException e)
                {
                    e.printStackTrace();
                    System.out.println("数据库连接错误");

                }
            }
        };
        thread=new Thread(networkTask);
        thread.start();
    }

    private void initView() {
        BillsAdapter adapter = new BillsAdapter(MainActivity.this, R.layout.item_bill, billList);
        listView =  findViewById(R.id.main_bills_listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        button_addBill=findViewById(R.id.button_main_addBill);
        button_addBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddBillActivity.class);
                startActivity(intent);
            }
        });

        button_search=findViewById(R.id.button_main_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
