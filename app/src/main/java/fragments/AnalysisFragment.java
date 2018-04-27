package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.moumita.caloriecountergeb.AnalysisActivity;
import com.example.moumita.caloriecountergeb.GroupBarChart;
import com.example.moumita.caloriecountergeb.R;


public class AnalysisFragment extends Fragment {

    private Button analysisButton;

    public AnalysisFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);
        //return inflater.inflate(R.layout.fragment_analysis, container, false);

        analysisButton = view.findViewById(R.id.analysis_btn);

        analysisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AnalysisActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
