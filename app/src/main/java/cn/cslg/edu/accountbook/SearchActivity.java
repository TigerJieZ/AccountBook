package cn.cslg.edu.accountbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class SearchActivity extends AppCompatActivity {

    Button button_search_submit,button_search_cancel;
    EditText inputCategory,inputAmount,inputCreateDate,inputExplain;
    List<Bill> billList=new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
    }

    private void initView() {
        inputCategory=findViewById(R.id.editText_search_category);
        inputAmount=findViewById(R.id.editText_search_amount);
        inputCreateDate=findViewById(R.id.editText_search_createDate);
        inputExplain=findViewById(R.id.editText_search_explain);

        button_search_submit=findViewById(R.id.button_search_submit);
        button_search_submit.setOnClickListener(new View.OnClickListener() {
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
                                    "jdbc:mysql://192.168.137.1:3306/accountbook?autoReconnect=true", "root",
                                    "");
                            String query="SELECT * FROM bill WHERE ";
                            boolean flag=false;
                            String category_m=inputCategory.getText().toString();
                            if(category_m!=null&&!category_m.equals("")) {
                                query += "category LIKE '%" + category_m + "%' ";
                                flag = true;
                            }
                            String amount_m=inputAmount.getText().toString();
                            if(amount_m!=null&&!amount_m.equals("")){
                                if(flag){
                                    query+="AND ";
                                    flag=false;
                                }
                                query+="amount = '"+amount_m+"' ";
                                flag=true;
                            }
                            String date_m=inputCreateDate.getText().toString();
                            if(date_m!=null&&!date_m.equals("")) {
                                if(flag){
                                    query+="AND ";
                                    flag=false;
                                }
                                query += "date LIKE '%" + date_m + "%' ";
                                flag=true;
                            }
                            String explain_m=inputExplain.getText().toString();
                            if(explain_m!=null&&!explain_m.equals("")){
                                if(flag){
                                    query+="AND ";
                                }
                                query+="explain LIKE '%"+explain_m+"%'";
                            }
                            System.out.println(query);
                            Statement statement=connection.createStatement();
                            ResultSet resultSet=statement.executeQuery(query);
                            while(resultSet.next()){
                                String category=resultSet.getString("category");
                                String date=resultSet.getString("date");
                                String explain=resultSet.getString("explains");
                                String amount=resultSet.getString("amount");
                                Bill temp=new Bill(category,amount,date,explain);
                                billList.add(temp);
                            }
                        } catch (SQLException e)
                        {
                            e.printStackTrace();
                            System.out.println("数据库连接错误");

                        }
                    }
                };
                Thread thread=new Thread(networkTask);
                thread.start();
                while(thread.isAlive()){
                }
                initListView();
            }
        });
        button_search_cancel=findViewById(R.id.button_search_cancel);
        button_search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void initListView(){
        BillsAdapter adapter = new BillsAdapter(SearchActivity.this, R.layout.item_bill, billList);
        listView =  findViewById(R.id.listView_search);
        listView.setAdapter(adapter);
    }
}
