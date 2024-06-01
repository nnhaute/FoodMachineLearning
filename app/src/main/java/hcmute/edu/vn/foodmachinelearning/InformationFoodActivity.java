package hcmute.edu.vn.foodmachinelearning;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foodmachinelearning.Interface.SpoonacularAPI;
import hcmute.edu.vn.foodmachinelearning.model.Recipe;
import hcmute.edu.vn.foodmachinelearning.model.complexSearch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//Hiển thị thông tin về các món ăn dựa trên tên món ăn được truyền từ một màn hình khác
//Nó sử dụng RecyclerView để hiển thị danh sách các món ăn và gọi API Spoonacular để lấy thông tin về các món ăn.
public class InformationFoodActivity extends AppCompatActivity implements FoodAdapter.OnItemClickListener{
//    Button btnRecipe;
//    TextView foodTextView;
//    ImageView imageFood;
    RecyclerView recyclerView;
    FoodAdapter foodAdapter;
    List<Recipe> foodList;

    ArrayList<Recipe> recipes ;

    private LinearLayout linearListMonAn;
    private View loadingView;
    private SpoonacularAPI spoonacularAPI;

    @Override
    public void onItemClick(int RecipeId) {

        Intent intent = new Intent(InformationFoodActivity.this, RecipeFoodActivity.class);

        intent.putExtra("id", RecipeId+"");
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_food);


        //get food name from activity_Main.layout when clicked.
        String foodName = getIntent().getStringExtra("foodName");

        recyclerView = findViewById(R.id.food_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        linearListMonAn = findViewById(R.id.linearListRecipe);
        loadingView = findViewById(R.id.layout_loading);

        // Khởi tạo hàm gọi API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        spoonacularAPI = retrofit.create(SpoonacularAPI.class);

        showLoading();
        recipes = new ArrayList<>();
        callAPI(foodName);

    }


//        btnRecipe = findViewById(R.id.recipe_button);
//        foodTextView = findViewById(R.id.food_name);
//        imageFood = findViewById(R.id.food_image);




//        byte[] byteArray = getIntent().getByteArrayExtra("foodImage");
//        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);



//        foodTextView.setText(foodName);
//        imageFood.setImageBitmap(bitmap);



//        btnRecipe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(InformationFoodActivity.this, RecipeFoodActivity.class);
//
//
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                intent.putExtra("foodImage", byteArray);
//
//                startActivity(intent);
//            }
//        });

    private void showMonAnList(List<Recipe> danhSachMonAn) {
        foodList = new ArrayList<>();
        foodList.addAll(danhSachMonAn);
        System.out.println(foodList.size());
    }

    private void showLoading() {
        linearListMonAn.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        linearListMonAn.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }
    public void callAPI(String query){
        String apiKey = "b4573860b7ab4fda8e3530bc4c807030";
        Call<complexSearch> call = spoonacularAPI.getRecipeSearch(apiKey, query);
        call.enqueue(new Callback<complexSearch>() {
            @Override
            public void onResponse(Call<complexSearch> call, Response<complexSearch> response) {
                if (response.isSuccessful()) {
                    complexSearch recipe = response.body();
                    recipes = recipe.getResult();

                    if (recipes != null && !recipes.isEmpty()) {
                        // Thực hiện các thao tác với danh sách recipe
                        showMonAnList(recipes);
                        hideLoading();

                        // ...
                    } else {

                        System.out.println("Không lấy được thông tin");
                        // Xử lý trường hợp danh sách rỗng
                    }

                    foodAdapter = new FoodAdapter( foodList,    InformationFoodActivity.this);
                    recyclerView.setAdapter(foodAdapter);
//                    Lấy thông tin món đầu tiên
//                  Xử lý kết quả từ API tại đây
//
                }
                else {
                    // Xử lý lỗi từ API tại đây
                    System.out.println(response.message());
                }
            }

            @Override
            public void onFailure(Call<complexSearch> call, Throwable t) {
                // Xử lý lỗi kết nối tại đây
            }
        });

    }

}
