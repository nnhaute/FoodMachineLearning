package hcmute.edu.vn.foodmachinelearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.tensorflow.lite.support.label.Category;

import java.util.ArrayList;
import java.util.List;
//trung gian giữa dữ liệu (danh sách các loại thực phẩm) và giao diện người dùng
public class CustomApdapter extends ArrayAdapter<Category> {
    private Context context;
    private List<Category> categoryList;



//    public CustomAdapter(Context context, int resource, ArrayList<Category> category) {
//        super(context, 0, category);
//        this.context = context;
//        this.categoryList = categoryList;
//    }

    public CustomApdapter(@NonNull Context context, int resource, List<Category> category) {
        super(context, 0, category);
        this.context = context;
        this.categoryList = category;
    }

//tạo ra một View cho phần tử tương ứng.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_food_list_item, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.text_view_name);
        TextView textViewScore = convertView.findViewById(R.id.text_view_score);

        Category category = categoryList.get(position);

        textViewName.setText(category.getLabel());
        textViewScore.setText(String.valueOf(category.getScore()));

        return convertView;
    }
}