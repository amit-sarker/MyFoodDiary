package adapter;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moumita.caloriecountergeb.R;

import java.util.List;

public class GoalRecyclerAdapter extends RecyclerView.Adapter<GoalRecyclerAdapter.ViewHolder> {
    List<String> goalName, goalDes,goalStatus;
    List<Integer> goalImages,goalDuration,goalStreak;


    public GoalRecyclerAdapter(List<String> goalName, List<String> goalDes, List<Integer> goalImages,
                               List<Integer> goalDuration, List<Integer> goalStreak, List<String>goalStatus) {
        this.goalName = goalName;
        this.goalDes = goalDes;
        this.goalImages = goalImages;
        this.goalDuration = goalDuration;
        this.goalStreak = goalStreak;
        this.goalStatus = goalStatus;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemName;
        public TextView itemDetail;
        public ProgressBar itemGoalBar;
        public TextView itemDuration, itemStreak;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.goal_image);
            itemName = (TextView)itemView.findViewById(R.id.goal_name);
            itemDetail = (TextView)itemView.findViewById(R.id.goal_details);
            itemGoalBar = itemView.findViewById(R.id.goal_progress_bar);
            itemDuration = itemView.findViewById(R.id.goal_duration);
            itemStreak = itemView.findViewById(R.id.goal_streak);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();



                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemName.setText(goalName.get(i));
        viewHolder.itemDetail.setText(goalDes.get(i));
        viewHolder.itemImage.setImageResource(goalImages.get(i));
        viewHolder.itemGoalBar.setMax((int)goalDuration.get(i));
        viewHolder.itemGoalBar.setProgress((int) goalStreak.get(i));
        viewHolder.itemDuration.setText(goalDuration.get(i) + " " + goalStatus.get(i));
        viewHolder.itemStreak.setText(goalStreak.get(i) + " " + goalStatus.get(i));

    }

    @Override
    public int getItemCount() {
        return goalName.size();
    }
}