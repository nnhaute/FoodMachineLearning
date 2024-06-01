package hcmute.edu.vn.foodmachinelearning;

// FoodAdapter.java
// quản lý dữ liệu và các view cho RecyclerView hiển thị danh sách các nguyên liệu của một món ăn. Nó sử dụng thư viện Picasso để tải ảnh nguyên liệu
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

import hcmute.edu.vn.foodmachinelearning.model.ExtendedIngredient;
import hcmute.edu.vn.foodmachinelearning.model.Measures;
import hcmute.edu.vn.foodmachinelearning.model.Recipe;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {


    Context context;
    List<ExtendedIngredient> listIngredients;

    private List<Recipe> foodList;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int RecipeId);
    }

    public IngredientsAdapter(Context context, List<ExtendedIngredient> listIngredients) {
        this.context=  context;
        this.listIngredients = listIngredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extented_ingredient, parent, false);
        return new IngredientViewHolder(itemView);
    }


//liên kết dữ liệu với các view trong IngredientViewHolder. Nó đặt văn bản của tên nguyên liệu, văn bản gốc và đo lường metric, và tải ảnh nguyên liệu bằng thư viện Picasso.
    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
       holder.txtTitleIngredients.setText(listIngredients.get(position).getName());
       holder.txtOrginal.setText(listIngredients.get(position).getOriginal());


       String imgUrl = "https://spoonacular.com/cdn/ingredients_100x100/"+listIngredients.get(position).getImage();
       Picasso.get().load(imgUrl).into(holder.imageIngredient);

       Measures measures = listIngredients.get(position).getMeasures();
       String metric = measures.getMetric().getAmount() +" "+ measures.getMetric().getUnitShort();
       holder.txtMetric.setText(metric);

    }

    @Override
    public int getItemCount() {
        return listIngredients.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitleIngredients,txtMetric,txtOrginal;
        public ImageView imageIngredient;


        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitleIngredients = itemView.findViewById(R.id.txt_ingredients);
            txtMetric = itemView.findViewById(R.id.txt_metric);
            txtOrginal = itemView.findViewById(R.id.txt_orginal);
            imageIngredient =itemView.findViewById(R.id.imageIngredient);
        }
    }
}
