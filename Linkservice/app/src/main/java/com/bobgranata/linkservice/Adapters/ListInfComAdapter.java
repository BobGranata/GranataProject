package com.bobgranata.linkservice.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bobgranata.linkservice.Models.InfComModel;
import com.bobgranata.linkservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BobGranata on 26.07.2016.
 */
public class ListInfComAdapter  extends ArrayAdapter<InfComModel> implements Filterable {
    private final Context context;
    private List<InfComModel> values;
    List<InfComModel> allInfCom;

    public ListInfComAdapter(Context context, List<InfComModel> values) {
        super(context, R.layout.infcom_list_item, values);
        this.context = context;
        this.values = values;
        this.allInfCom = new ArrayList<InfComModel>(values);
    }

    // Класс для сохранения во внешний класс и для ограничения доступа
    // из потомков класса
    static class ViewHolder {
        public TextView tvNameInfCom;

        public RelativeLayout itemLay;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public InfComModel getItem(int index) {
        return values.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // ViewHolder буферизирует оценку различных полей шаблона элемента
        ViewHolder holder;
        // Очищает сущетсвующий шаблон, если параметр задан
        // Работает только если базовый шаблон для всех классов один и тот же
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.infcom_list_item, null, true);
            holder = new ViewHolder();
            holder.tvNameInfCom = (TextView) rowView.findViewById(R.id.tvNameInfCom);
            holder.itemLay = (RelativeLayout) rowView.findViewById(R.id.rLayInfComItem);

//            Typeface regular = Typeface.createFromAsset(context.getAssets(), "fonts/arial.ttf");
//            holder.tvNameRole.setTypeface(regular);
//            holder.tvInnRole.setTypeface(regular);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.tvNameInfCom.setText(values.get(position).getName());
//        if (position <= values.size() - 1) {
//            holder.tvNameInfCom.setText(values.get(position).getName());
//        }

        return rowView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0) {

                    filterResults.values = allInfCom;
                    filterResults.count = allInfCom.size();
                } else {
                    ArrayList<InfComModel>
                            filteredList = new ArrayList<InfComModel>();
                    for(InfComModel j: allInfCom){
                        if(j.getName().toLowerCase().contains(constraint.toString().toLowerCase()))
                            filteredList.add(j);
                    }
                    filterResults.values = filteredList;
                    filterResults.count = filteredList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    values = (List<InfComModel>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};

        return filter;
    }
}
