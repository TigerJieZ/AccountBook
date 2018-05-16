package cn.cslg.edu.accountbook;

import java.security.PublicKey;
import java.util.List;

import android.R.layout;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BillsAdapter extends ArrayAdapter{
	private final int resourceId;
	  
    @SuppressWarnings("unchecked")
	public BillsAdapter(Context context, int textViewResourceId, List<Bill> objects) {
        super(context, textViewResourceId, objects);  
        resourceId = textViewResourceId;  
    }  
    @SuppressLint({ "ViewHolder", "InflateParams" })
	@Override  
    public View getView(int position, View convertView, ViewGroup parent) {
        Bill bill = (Bill) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_bill, null);

        TextView textViewCategory =  view.findViewById(R.id.textView_bill_category);
        TextView textViewAmount=view.findViewById(R.id.textView_bill_amount);
        TextView textViewDate=view.findViewById(R.id.textView_bill_createDate);
        TextView textViewExplaiin=view.findViewById(R.id.textView_bill_explain);

        textViewCategory.setText(bill.getCategory());
        textViewAmount.setText("￥"+bill.getAmount());
        textViewDate.setText(bill.getDate());
        textViewExplaiin.setText(bill.getExplain());

        ImageView imageView=view.findViewById(R.id.item_bill_image);
        switch (bill.getCategory()){
            case "sports":
                imageView.setImageResource(R.drawable.img_bill_sports);
                break;
            case "food":
                imageView.setImageResource(R.drawable.img_bill_food);
                break;
            case "book":
                imageView.setImageResource(R.drawable.img_bill_book);
                break;
            case "traffic":
                imageView.setImageResource(R.drawable.img_bill_traffic);
                break;
            default:
                imageView.setImageResource(R.drawable.img_bill_default);
        }
        
        return view;  
    }  
}
