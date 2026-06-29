package pl.pollub.app1;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GradeRowViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvSubjectName;
    private final RadioGroup rgSubjectGrade;
    private  Subject currentSubject;


    public GradeRowViewHolder(@NonNull View itemView){
        super(itemView);
        this.tvSubjectName = itemView.findViewById(R.id.tv_subject_name);
        this.rgSubjectGrade = itemView.findViewById(R.id.rg_subject_grade);
        this.rgSubjectGrade.setOnCheckedChangeListener((radioGroup, i) -> {
            if(this.currentSubject != null){
                int checked = radioGroup.getCheckedRadioButtonId();
                if(checked == R.id.rb_5){
                    this.currentSubject.setSubjectGrade(5);
                }
                else if(checked == R.id.rb_4){
                    this.currentSubject.setSubjectGrade(4);
                }
                else if(checked == R.id.rb_3){
                    this.currentSubject.setSubjectGrade(3);
                }
                else{
                    this.currentSubject.setSubjectGrade(2);
                }

            }
        });

    }



    public void setCurrentSubject(Subject currentSubject) {
        this.currentSubject = currentSubject;
    }

    public TextView getTvSubjectName() {
        return tvSubjectName;
    }

    public RadioGroup getRgSubjectGrade() {
        return rgSubjectGrade;
    }
}
