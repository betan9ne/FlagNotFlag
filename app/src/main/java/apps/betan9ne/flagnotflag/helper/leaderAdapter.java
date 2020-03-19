package apps.betan9ne.flagnotflag.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import apps.betan9ne.flagnotflag.R;

public class leaderAdapter extends RecyclerView.Adapter<leaderAdapter.MyViewHolder>{

     Context _context;

    private List<leader_item> moviesList;
     public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, score;

        public MyViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.textView);
            score =   view.findViewById(R.id.textView2);
        }

    }


    public leaderAdapter( List<leader_item> moviesList) {
        this.moviesList = moviesList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        leader_item movie = moviesList.get(position);
        holder.name.setText(movie.getName());
        holder.score.setText(movie.getScore()+" pts");
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
