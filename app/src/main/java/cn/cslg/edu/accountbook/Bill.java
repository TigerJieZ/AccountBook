package cn.cslg.edu.accountbook;

/**
 * Created by 14446 on 2018/5/15.
 */

public class Bill {
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    String amount;
    String category;
    String explain;

    public Bill(String category,String amount,String date,String explain){
        setCategory(category);
        setExplain(explain);
        setAmount(amount);
        setDate(date);
    }
}
