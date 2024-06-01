package hcmute.edu.vn.foodmachinelearning;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.modeldownloader.CustomModel;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.support.metadata.MetadataExtractor;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foodmachinelearning.Interface.SpoonacularAPI;
import hcmute.edu.vn.foodmachinelearning.ml.LiteModelAiyVisionClassifierFoodV11;
import hcmute.edu.vn.foodmachinelearning.model.Recipe;
import hcmute.edu.vn.foodmachinelearning.model.complexSearch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button selectBtn, predictBtn, captureBtn;
//    TextView result;

    ListView listViewName;
    ImageView imageView;
    Bitmap bitmap;
    Interpreter interpreter;
    List<String>  labels;
    List<String> classes = new ArrayList<>();

    private Drawable selectedImage;




    MappedByteBuffer byteBufferModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectBtn = findViewById(R.id.button_gallery);
        predictBtn = findViewById(R.id.button_identify);
        captureBtn = findViewById(R.id.button_capture);
        listViewName = findViewById(R.id.list_view_name);
        imageView = findViewById(R.id.image_view);

        getPermission();


// Các Button cho phép người dùng chọn ảnh từ gallery, chụp ảnh mới và nhận diện ảnh
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,10);
            }
        });

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 11);
            }
        });

        predictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                //Use model deployed on firebase
                bitmap = Bitmap.createScaledBitmap(bitmap, 192, 192, true);
                bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//
                List<Category> probability = new ArrayList<>();
                try {
                    LiteModelAiyVisionClassifierFoodV11 model = LiteModelAiyVisionClassifierFoodV11.newInstance(MainActivity.this);

                    // Creates inputs for reference.
                    TensorImage image = TensorImage.fromBitmap(bitmap);

                    // Runs model inference and gets result.
                    LiteModelAiyVisionClassifierFoodV11.Outputs outputs = model.process(image);
                    probability = outputs.getProbabilityAsCategoryList();

                   int maxProbabilityIndex = -1;
                    List<Category> resultProbability = new ArrayList<>();
                    resultProbability.add(probability.get(0));  // the first food that have max score
                    resultProbability.add(probability.get(1));  // the second food that have second score
                    resultProbability.add(probability.get(2));  // the third food that have third score

//                  find three result that have highest score

                    for (int i = 0; i < probability.size(); i++) {
                        float currentProbability = probability.get(i).getScore();
                        if (currentProbability > resultProbability.get(0).getScore()) {
                            resultProbability.set(2,resultProbability.get(1));
                            resultProbability.set(1,resultProbability.get(0));
                            resultProbability.set(0,probability.get(i));
                        } else if (currentProbability > resultProbability.get(1).getScore()){
                            resultProbability.set(2,resultProbability.get(1));
                            resultProbability.set(1,probability.get(i));
                        } else if (currentProbability > resultProbability.get(2).getScore()){
                            resultProbability.set(2,probability.get(i));
                        }
                    }
//                    add list result into listView
                    System.out.println(resultProbability);
                    CustomApdapter customApdapter = new CustomApdapter(MainActivity.this, 0, resultProbability);
                    //truyền ressult vào customApdapter class
                    listViewName.setAdapter(customApdapter);

                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException e) {
                    // TODO Handle the exception
                }

//                interpreter.close();

            }
        });

// ListView để hiển thị danh sách các món ăn được nhận diện từ ảnh
        List<Category> list = new ArrayList<>();
        CustomApdapter arrayAdapter = new CustomApdapter(getApplicationContext(),3,list);
        listViewName.setAdapter(arrayAdapter);
        listViewName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                String foodName = selectedCategory.getLabel();

                Intent intent = new Intent(MainActivity.this, InformationFoodActivity.class);
                intent.putExtra("foodName", foodName);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("foodImage", byteArray);

                startActivity(intent);
            }
            
        });


    }


    //Đọc file model và chuyển đổi thành MappedByteBuffer
    public MappedByteBuffer loadModelToByteBuffer(File modelFile) throws IOException {
        // Load the model file as a byte buffer
        FileInputStream inputStream = new FileInputStream(modelFile);
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = 0;
        long declaredLength = modelFile.length();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
// Hàm loadLabelsFromMetadata: Tải các nhãn từ metadata của model TFLite
    public static List<String> loadLabelsFromMetadata(MappedByteBuffer byteBufferModel, String labelFilename) throws IOException {
        // Load the metadata of the TFLite model
        MetadataExtractor metadataExtractor = new MetadataExtractor(byteBufferModel);

        List<String> labels = new ArrayList<>();
        int cnt = 0;
        String[] label = new String [2024];
        try (BufferedReader br =
                     new BufferedReader(
                             new InputStreamReader(
                                     metadataExtractor.getAssociatedFile(labelFilename)))) {
            String line = br.readLine();
            while (line!=null){

//                label[cnt]= line;
                labels.add(line);
                cnt++;
                line = br.readLine();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }



        return labels;
    }
    private int getMax(float[] v) {
        int max = 0 ;
        for (int i = 0 ; i<v.length;i++){
            if (v[i]>v[max]) max = i;
        }
        System.out.println("Max: "+max);
        return max;
    }
// Hàm getPermission: Yêu cầu quyền truy cập camera nếu chưa có
    private void getPermission() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA},11);
        }
    }
// Xử lý kết quả khi yêu cầu quyền truy cập camera
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==11){
            if (grantResults.length>0){
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    this.getPermission();
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
// Xử lý kết quả khi chọn ảnh từ gallery hoặc chụp ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);



                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if ( requestCode==11){
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

}