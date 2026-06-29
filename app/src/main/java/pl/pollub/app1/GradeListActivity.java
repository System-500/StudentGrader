package pl.pollub.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import pl.pollub.app1.databinding.ActivityGradeListBinding;

public class GradeListActivity extends AppCompatActivity {
    public static final String SUBJECT_GRADE_NR = "SUBJECT_GRADE_NR_";
    private static final String SUBJECT_LIST_KEY = "SUBJECT_LIST_KEY";
    private ActivityGradeListBinding binding;
private  int LiczbaOcen;
private List<Subject> subjectList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.binding = ActivityGradeListBinding.inflate(this.getLayoutInflater());
        setContentView(this.binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       this.LiczbaOcen = this.getIntent().getIntExtra(MainActivity.NUMBER_OF_GRADES,0);
       String[] subjects = this.getResources().getStringArray(R.array.nazwy_przedmiotow_tab);

        if (savedInstanceState != null) {
            this.subjectList = (List<Subject>)
                    savedInstanceState.getSerializable(SUBJECT_LIST_KEY);
        }
        else {
            this.subjectList = Arrays.stream(subjects)
                    .limit(this.LiczbaOcen)
                    .map(Subject::new)
                    .collect(Collectors.toList());
        }
        GradeListAdapter adapter = new GradeListAdapter(this.subjectList, this.getLayoutInflater());
        this.binding.rvGradeList.setAdapter(adapter);
        this.binding.rvGradeList.setLayoutManager(new LinearLayoutManager(this));
        this.binding.btnCalculateAvg.setOnClickListener(this::goToCalculateAVG);
    }

    private void goToCalculateAVG(View view) {
        Intent finishIntent = new Intent();
        finishIntent.putExtra(MainActivity.NUMBER_OF_GRADES, this.LiczbaOcen);
        Bundle bundle = new Bundle();
        for (int i = 1; i <= this.LiczbaOcen; i++) {
            bundle.putInt(SUBJECT_GRADE_NR + i, this.subjectList.get(i-1).getSubjectGrade());
        }


        finishIntent.putExtras(bundle);
        this.setResult(RESULT_OK, finishIntent);
        this.finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(
                SUBJECT_LIST_KEY,
                (java.io.Serializable) this.subjectList
        );
    }
}