import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.doomshell.property_bull.R;
import com.doomshell.property_bull.helper.Serverapi;

import retrofit.RestAdapter;

public class HomeLoan_Fag extends Fragment
{
    Context context;
    View convertview;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String Baseurl;
    RestAdapter restAdapter=null;
    Serverapi serverapi;
    // Dialog contactdialog;
    ProgressBar bar;
    AppCompatActivity appCompatActivity;
    ActionBar actionBar;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity().getApplicationContext();
        convertview= inflater.inflate(R.layout.homeloan, container, false);

        appCompatActivity = (AppCompatActivity) getActivity();


        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView screentitle=(TextView)toolbar.findViewById(R.id.screentitle);
        screentitle.setText("Home Loan");




        return convertview;
    }
}
