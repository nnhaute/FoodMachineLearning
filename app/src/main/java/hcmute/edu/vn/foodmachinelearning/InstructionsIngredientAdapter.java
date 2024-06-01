package hcmute.edu.vn.foodmachinelearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hcmute.edu.vn.foodmachinelearning.model.Ingredient;
import hcmute.edu.vn.foodmachinelearning.model.Step;

public class InstructionsIngredientAdapter extends RecyclerView.Adapter<InstructionsIngredientViewHolder> {


    Context context;
    List<Ingredient> list;
    //hiển thị danh sách các thành phần (ingredient) trong màn hình hướng dẫn nấu ăn
    public InstructionsIngredientAdapter(Context context, List<Ingredient> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    //Tạo ra một InstructionsIngredientViewHolder bằng cách inflating layout list_instructions_step_item.
    public InstructionsIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_instructions_step_item, parent, false);
        return new InstructionsIngredientViewHolder(itemView);    }
    //Nó thiết lập văn bản và hình ảnh của từng thành phần.
    @Override
    public void onBindViewHolder(@NonNull InstructionsIngredientViewHolder holder, int position) {
        holder.tvInstructionItem.setText(list.get(position).name);
        holder.tvInstructionItem.setSelected(true);

        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+list.get(position).image).into(holder.ivInstructionItem);

    }




    @Override
    public int getItemCount() { 
        return list.size();
    }
}
//Là một ViewHolder cho RecyclerView, chứa các view để hiển thị thông tin của một thành phần.
class InstructionsIngredientViewHolder extends RecyclerView.ViewHolder{
    TextView tvInstructionItem;
    ImageView ivInstructionItem;


    public InstructionsIngredientViewHolder(@NonNull View itemView){
        super(itemView );
        tvInstructionItem = itemView.findViewById(R.id.tv_instruction_step_item);
        ivInstructionItem = itemView.findViewById(R.id.iv_instruction_step_item);

    }
}
