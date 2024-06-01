package hcmute.edu.vn.foodmachinelearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.foodmachinelearning.model.Instructions;
import hcmute.edu.vn.foodmachinelearning.model.Step;

public class InstructionsStepAdapter extends RecyclerView.Adapter<InstructionsStepViewHolder> {


    Context context;
    List<Step> list;
    // Khởi tạo adapter với context và danh sách các bước hướng dẫn
    public InstructionsStepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
//tạo một view holder mới. Nó inflates một layout list_instructions_step.xml và trả về một instance của InstructionsStepViewHolder.
    public InstructionsStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng mục trong RecyclerView
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_instructions_step, parent, false);
        return new InstructionsStepViewHolder(itemView);    }
//Liên kết dữ liệu của một mục với view holder tương ứng.
    @Override
    public void onBindViewHolder(@NonNull InstructionsStepViewHolder holder, int position) {
        // Lấy thông tin của bước hướng dẫn tại vị trí position
        // Hiển thị số thứ tự và tên bước hướng dẫn
        holder.tvNumber.setText(String.valueOf(list.get(position).number));
        holder.tvStepName.setText(list.get(position).step);
        // Cấu hình và gắn adapter con cho RecyclerView hiển thị nguyên liệu
        holder.rvInstructionsIngredient.setHasFixedSize(true);
        holder.rvInstructionsIngredient.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        InstructionsIngredientAdapter instructionsIngredientAdapter =new InstructionsIngredientAdapter(context,list.get(position).ingredients);
        holder.rvInstructionsIngredient.setAdapter(instructionsIngredientAdapter);
// Cấu hình và gắn adapter con cho RecyclerView hiển thị thiết bị
        holder.rvInstructionsEquipment.setHasFixedSize(true);
        holder.rvInstructionsEquipment.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        InstructionsEquipmentAdapter instructionsEquipmentAdapter =new InstructionsEquipmentAdapter(context,list.get(position).equipment);
        holder.rvInstructionsEquipment.setAdapter(instructionsEquipmentAdapter);


    }




    @Override
    public int getItemCount() { 
        return list.size();
    }
}
class InstructionsStepViewHolder extends RecyclerView.ViewHolder{
    TextView tvStepName,tvEquipment,tvIngredient,tvNumber;

    RecyclerView rvInstructionsEquipment,rvInstructionsIngredient;
    // ViewHolder để giữ các view của một mục trong RecyclerView
    public InstructionsStepViewHolder(@NonNull View itemView){
        super(itemView );
        tvStepName = itemView.findViewById(R.id.tv_step_name);
        tvEquipment=itemView.findViewById(R.id.tv_equipment);
        tvIngredient=itemView.findViewById(R.id.tv_ingredient);
        tvNumber= itemView.findViewById(R.id.tv_number);

        rvInstructionsEquipment = itemView.findViewById(R.id.rv_instructions_equipment);
        rvInstructionsIngredient = itemView.findViewById(R.id.rv_instructions_ingredient);
    }
}
