package hcmute.edu.vn.foodmachinelearning;

// FoodAdapter.java
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foodmachinelearning.model.Recipe;
// trung gian giữa dữ liệu (danh sách các món ăn) và giao diện người dùng (RecyclerView)
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Recipe> foodList;
    private OnItemClickListener itemClickListener;
// interface để lắng nghe sự kiện nhấp vào một món ăn.
    public interface OnItemClickListener {
        void onItemClick(int RecipeId);
    }
    //danh sách các món ăn (Recipe) cần hiển thị.
    public FoodAdapter(List<Recipe> foodList, OnItemClickListener itemClickListener) {
        this.foodList=  foodList;

        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_recipe, parent, false);
        return new FoodViewHolder(itemView);
    }
//cập nhật thông tin của một món ăn vào ViewHolder tương ứng.
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        String foodName = foodList.get(position).getTitle();
        String imageUrl = foodList.get(position).getImage();
        int RecipeId = foodList.get(position).getId();

        System.out.println(RecipeId);


        holder.textFoodName.setText(foodName);
        Picasso.get().load(imageUrl).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(RecipeId);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        public TextView textFoodName;
        public ImageView image;

        public FoodViewHolder(View itemView) {
            super(itemView);

            textFoodName = itemView.findViewById(R.id.titleTextView);
            image = itemView.findViewById(R.id.imageView);
        }
    }
}
