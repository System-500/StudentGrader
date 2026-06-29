package pl.pollub.app1;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import pl.pollub.app1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String NUMBER_OF_GRADES = "NUMBER_OF_GRADES";
    private ActivityMainBinding binding ;
    private boolean isFirstNameValid;
    private boolean isLastNameValid;
    private boolean isOcenyValid;
    private double avg;
    private ActivityResultLauncher<Intent> gradeListActivityLauncher;
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("firstName", binding.etFirstName.getText().toString());
        outState.putString("lastName", binding.etLastName.getText().toString());
        outState.putString("gradesCount", binding.etLiczbaOcen.getText().toString());
        outState.putDouble("avg", avg);
        outState.putBoolean("wynikBtnVisible", binding.wynikBtn.getVisibility() == VISIBLE);
        outState.putBoolean("avgOcenyVisible", binding.avgOceny.getVisibility() == VISIBLE);
    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(this.binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.binding.etFirstName.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) return;
            String firstName = this.binding.etFirstName.getText().toString().trim();
            validateFirstName(firstName);
        });
        prepateFirstnameTextWather();

        this.binding.etLastName.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) return;
            String lastName = this.binding.etLastName.getText().toString().trim();
            validateLastName(lastName);
        });
        this.binding.etLastName.addTextChangedListener(lastnametextWather());

        this.binding.etLiczbaOcen.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) return;
            String numberOfGradesStr = this.binding.etLiczbaOcen.getText().toString().trim();
            NumberosGradesValidate(numberOfGradesStr);
        });
        this.binding.etLiczbaOcen.addTextChangedListener(LiczbaocenWather());
        this.gradeListActivityLauncher = this.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::gradeListActivityCallback);
        this.binding.btnOceny.setOnClickListener(this::lauchGradeListActivity);
        this.binding.wynikBtn.setOnClickListener(wynikButton());

        if (savedInstanceState != null) {
            binding.etFirstName.setText(savedInstanceState.getString("firstName"));
            binding.etLastName.setText(savedInstanceState.getString("lastName"));
            binding.etLiczbaOcen.setText(savedInstanceState.getString("gradesCount"));

            avg = savedInstanceState.getDouble("avg", 0);

            binding.wynikBtn.setVisibility(savedInstanceState.getBoolean("wynikBtnVisible") ? VISIBLE : INVISIBLE);
            binding.avgOceny.setVisibility(savedInstanceState.getBoolean("avgOcenyVisible") ? VISIBLE : INVISIBLE);

            if (binding.avgOceny.getVisibility() == VISIBLE) {
                binding.avgOceny.setText(getString(R.string.avg) + String.format("%.2f", avg));
            }

            if (binding.wynikBtn.getVisibility() == VISIBLE) {
                binding.wynikBtn.setText(avg >= 3 ? R.string.success : R.string.fail);
            }
        }

    }

    private View.OnClickListener wynikButton() {
        return v -> {
            if (avg >= 3) {
                Toast.makeText(this, R.string.success_end, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.fail_end, Toast.LENGTH_LONG).show();
            }
            finishAffinity();
        };
    }

    private void lauchGradeListActivity(View view){

        int numberofGrades = Integer.parseInt(this.binding.etLiczbaOcen.getText().toString());
        Intent gradeListActivityIntent = new Intent(this, GradeListActivity.class);
        gradeListActivityIntent.putExtra(NUMBER_OF_GRADES, numberofGrades);
        this.gradeListActivityLauncher.launch(gradeListActivityIntent);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void calcAvg(int[] table){
        double suma = 0;
        for(int i = 1 ; i<= table.length; i++){
            suma += table[i - 1];


        }
        avg = suma / table.length;

        if(avg >= 3){

            binding.wynikBtn.setText(R.string.success);
        }
        else{
            binding.wynikBtn.setText(R.string.fail);
        }
        binding.wynikBtn.setVisibility(VISIBLE);
        binding.avgOceny.setVisibility(VISIBLE);
        binding.avgOceny.setText(getString(R.string.avg) + String.format("%.2f", avg));

    }


    private void gradeListActivityCallback(ActivityResult o){
        int result = o.getResultCode();
        if(result == RESULT_OK){
            Intent intent = o.getData();
            if(intent!= null) {
                int LICZBAocen = intent.getIntExtra(MainActivity.NUMBER_OF_GRADES, 0);
                Bundle bundle = intent.getExtras();
                int[] gradesArray = new int [LICZBAocen];
                for (int i = 1; i <= LICZBAocen; i++) {
                    gradesArray[i-1] = bundle.getInt(GradeListActivity.SUBJECT_GRADE_NR+i, 0 );
                }

                calcAvg(gradesArray);

            }

        }
    }


    @NonNull
    private TextWatcher LiczbaocenWather() {
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String liczbaOcen = s.toString().trim();
                MainActivity.this.NumberosGradesValidate(liczbaOcen);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        };
    }

    private void NumberosGradesValidate(String numberOfGradesStr) {

        if(TextUtils.isEmpty(numberOfGradesStr)){
            this.binding.etLiczbaOcen.setError(getString(R.string.et_number_grades_text));
            this.isOcenyValid = false;
        }
        else{
        try{
            int numberOfGrades = Integer.parseInt(numberOfGradesStr);
            if(numberOfGrades < 5 || numberOfGrades > 15){
                this.binding.etLiczbaOcen.setError(getString(R.string.et_number_grades_text2));
                this.isOcenyValid = false;

            }
            else{
                this.isOcenyValid = true;
            }
        }catch (NumberFormatException ef){
            this.binding.etLiczbaOcen.setError(getString(R.string.et_number_grades_text3));
            this.isOcenyValid = false;

        }
    }
        this.chekSubmitBTNvis();
    }


    @NonNull
    private TextWatcher lastnametextWather() {
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String lastName = s.toString().trim();
                MainActivity.this.validateLastName(lastName);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        };
    }

    private void validateLastName(String lastName) {
        if(TextUtils.isEmpty(lastName)){
            this.binding.etLastName.setError(getString(R.string.tv_last_name_error_text));
            this.isLastNameValid = false;
        }
         else {
            this.isLastNameValid = true;
        }
        this.chekSubmitBTNvis();
    }

    private void prepateFirstnameTextWather() {
        this.binding.etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String firstName = s.toString().trim();
                MainActivity.this.validateFirstName(firstName);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

    }


    private void chekSubmitBTNvis(){
        final boolean isAllValid = this.isFirstNameValid && this.isLastNameValid && this.isOcenyValid;
        this.binding.btnOceny.setVisibility(isAllValid ? VISIBLE : INVISIBLE);
    }
    private void validateFirstName(String firstName) {
        if(TextUtils.isEmpty(firstName)){
            this.binding.etFirstName.setError(getString(R.string.tv_first_name_error_text));
            this.isFirstNameValid = false;

        }
        else{
            this.isFirstNameValid = true;
        }
        this.chekSubmitBTNvis();
    }

}