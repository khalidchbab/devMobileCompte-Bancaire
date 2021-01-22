package comkhalid.bankmanagement.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import comkhalid.bankmanagement.R;

import java.util.ArrayList;

public class OperationsController extends ArrayAdapter<Operation> {

    private Context mContext;
    private ArrayList<Operation> operations;
    private int mResource;

    public OperationsController(@NonNull Context context,int resource,@NonNull ArrayList<Operation> operations){
        super(context,resource,operations );
        this.mContext = context;
        this.mResource = resource;
        this.operations = operations;
    }

    @Nullable
    @Override
    public Operation getItem(int position) {
        return operations.get(position);
    }

    @Override
    public int getCount() {
        return operations.size();
    }

    @Override
    public int getPosition(@Nullable Operation item) {
        return operations.indexOf(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        if ( convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource,parents,false);
        }

        TextView num = (TextView) convertView.findViewById(R.id.v_num);
        TextView montant = (TextView) convertView.findViewById(R.id.v_montant);
        TextView date = (TextView) convertView.findViewById(R.id.v_date);
        num.setText(getItem(position).getNum().toString());
        montant.setText(getItem(position).getMontant().toString());
        date.setText(getItem(position).getDate().toString());

        return convertView;
    }
}
