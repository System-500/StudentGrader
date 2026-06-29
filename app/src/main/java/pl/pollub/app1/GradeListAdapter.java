    package pl.pollub.app1;
    
    import android.view.LayoutInflater;
    import android.view.ViewGroup;
    
    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;
    
    import java.util.List;
    
    public class GradeListAdapter extends RecyclerView.Adapter<GradeRowViewHolder> {
    
        private final List<Subject> subjectList;
        private final LayoutInflater inflater;
    
        public GradeListAdapter(List<Subject> subjectList, LayoutInflater inflater) {
            this.subjectList = subjectList;
            this.inflater = inflater;
        }
    
        @NonNull
        @Override
        public GradeRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new GradeRowViewHolder(
                    this.inflater.inflate(R.layout.grade_list_row, parent, false)
            );
        }
    
        @Override
        public void onBindViewHolder(@NonNull GradeRowViewHolder holder, int position) {
    
            Subject currentSubject = this.subjectList.get(position);
            holder.setCurrentSubject(currentSubject);
            holder.getTvSubjectName().setText(
                    currentSubject.getSubjectName()
            );
    
            int subjectGrade = currentSubject.getSubjectGrade();
    
            switch (subjectGrade) {
                case 5:
                    holder.getRgSubjectGrade().check(R.id.rb_5);
                    break;
    
                case 4:
                    holder.getRgSubjectGrade().check(R.id.rb_4);
                    break;
    
                case 3:
                    holder.getRgSubjectGrade().check(R.id.rb_3);
                    break;
    
                default:
                    holder.getRgSubjectGrade().check(R.id.rb_2);
                    break;
            }
    
    
        }
    
    
    
    
        @Override
        public int getItemCount() {
            return this.subjectList.size();
        }
    }