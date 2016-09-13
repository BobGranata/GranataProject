package com.bobgranata.linkservice.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bobgranata.linkservice.Activity.LoginActivity;
import com.bobgranata.linkservice.Database.DatabaseHandler;
import com.bobgranata.linkservice.Models.RolesModel;
import com.bobgranata.linkservice.R;

import java.util.List;

/**
 * Created by BobGranata on 16.07.2016.
 */
public class ListRolesAdapter extends ArrayAdapter<RolesModel> {
    private final Context context;
    private final List<RolesModel> values;

    public ListRolesAdapter(Context context, List<RolesModel> values) {
        super(context, R.layout.roles_list_item, values);
        this.context = context;
        this.values = values;
    }

    // Класс для сохранения во внешний класс и для ограничения доступа
    // из потомков класса
    static class ViewHolder {
        public TextView tvNameRole;
        public TextView tvInnRole;
        public ImageView imageView;
        public RelativeLayout itemLay;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // ViewHolder буферизирует оценку различных полей шаблона элемента

        ViewHolder holder;
        // Очищает сущетсвующий шаблон, если параметр задан
        // Работает только если базовый шаблон для всех классов один и тот же
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.roles_list_item, null, true);
            holder = new ViewHolder();
            holder.tvNameRole = (TextView) rowView.findViewById(R.id.tvNameRole);
            holder.tvInnRole = (TextView) rowView.findViewById(R.id.tvInnRole);
            holder.itemLay = (RelativeLayout) rowView.findViewById(R.id.rlayRolesItem);
            holder.imageView = (ImageView) rowView.findViewById(R.id.ivDeleteRole);

//            Typeface regular = Typeface.createFromAsset(context.getAssets(), "fonts/arial.ttf");
//            holder.tvNameRole.setTypeface(regular);
//            holder.tvInnRole.setTypeface(regular);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.tvNameRole.setText(values.get(position).getName());
        holder.tvInnRole.setText("ИНН: " + values.get(position).getInn());

        holder.imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                String roleId = values.get(position).getID();
                LoginActivity.deleteRole(roleId);
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Удаление");
//                builder.setMessage("Удалить выбранную организацию из списка?");
//                builder.setCancelable(true);
//
//                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int arg1) {
//                        Toast.makeText(context, "Отмена", Toast.LENGTH_SHORT)
//                                .show();
//                    }
//                });
//
//                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        DatabaseHandler db = new DatabaseHandler(context);
//                        String roleId = values.get(position).getID();
//                        db.deleteRole(roleId);
//                        values.remove(position);
//
//                        LoginActivity.updateListView(values);
//                    }
//                });
//
//                AlertDialog dialog = builder.create();
//                dialog.show();
			}
		});

        return rowView;
    }
}
