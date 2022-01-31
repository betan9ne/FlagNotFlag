package apps.betan9ne.flagnotflag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import apps.betan9ne.flagnotflag.helper.AppConfig;

import apps.betan9ne.flagnotflag.helper.leaderAdapter;
import apps.betan9ne.flagnotflag.helper.leader_item;

public class LeaderboardFragment extends BottomSheetDialogFragment {

    RecyclerView list;
    TextView left, right;
    TextView counter;
  String item;
    private leaderAdapter mAdapter;
    private ArrayList<leader_item> leader_items;
    public LeaderboardFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.leaderboard, container, false);
        list = v.findViewById(R.id.list);
        right = v.findViewById(R.id.right);
        left= v.findViewById(R.id.left);
        counter= v.findViewById(R.id.counter);

        leader_items = new ArrayList<>();

        final ArrayList<String> aList = new ArrayList<>();
        aList.add("10");
        aList.add("15");
        aList.add("30");
        aList.add("35");
        final ListIterator<String> listIterator = aList.listIterator();
        item = aList.get(0);
        counter.setText(aList.get(0) + "");
        left.setVisibility(View.INVISIBLE);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!listIterator.hasNext())
                {
                    right.setVisibility(View.INVISIBLE);
                }
                else {
                    left.setVisibility(View.VISIBLE);
                    item = listIterator.next();
                    counter.setText(item);
                    getLeaderboard(item);
                }
            }
        });
        left.setVisibility(View.INVISIBLE);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!listIterator.hasPrevious())
                {
                    left.setVisibility(View.INVISIBLE);
                }
                else
                {
                    right.setVisibility(View.VISIBLE);
                    item = listIterator.previous();
                    counter.setText(item+"");
                    getLeaderboard(item);
                }
            }
        });
        mAdapter = new leaderAdapter(leader_items);
        LinearLayoutManager layoutManageri = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManageri);
        list.setAdapter(mAdapter);
        getBoard(aList.get(0) + "");
        return v;
    }

    public void getLeaderboard(final String id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("board").document(id+"").collection("scores").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult()){
                                leader_item s = document.toObject(leader_item.class);
//                                leader_item item = new leader_item();
//                                item.setName(document.getData().f_name);
////                                item.setScore(document.getData().("score"));
                           leader_items.add(s);
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                        }
                        else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getBoard(final String id)
    {
        leader_items.clear();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                AppConfig.leader,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                try {
                                    JSONArray feedArray = jObj.getJSONArray(("item"));
                                    if (feedArray.length() == 0) {
                                        Toast.makeText(getContext(), "No one has  set a score yet", Toast.LENGTH_SHORT).show();
                                    } else {
                                        for (int i = 0; i < feedArray.length(); i++) {
                                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                                            //     Toast.makeText(ViewReceiptActivity.this, ""+ feedObj.length() , Toast.LENGTH_SHORT).show();
                                            leader_item item = new leader_item();
                                            item.setName(feedObj.getString("f_name"));
                                            item.setScore(feedObj.getInt("score"));
                                            leader_items.add(item);
                                        }
                                    }
                                    mAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    Toast.makeText(getContext(), "Please connect to the internet" , Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(getContext(), "Please connect to the internet"  , Toast.LENGTH_SHORT).show();
                            }
                        }
                        //	pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //	pDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }
}
