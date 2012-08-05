package de.christianzunker.android.rundeck.activities;

import java.util.List;
import java.util.Vector;

import org.rundeck.api.RundeckClient;
import org.rundeck.api.domain.RundeckJob;

import de.christianzunker.android.rundeck.R;
import de.christianzunker.android.rundeck.utils.RundeckClientFactory;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class JobsActivity extends ListActivity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	RundeckClientFactory rdClientFactory = new RundeckClientFactory();
        RundeckClient rdClient = rdClientFactory.getClient();
    	
    	Intent intent = getIntent();
    	Bundle extraBundle = intent.getExtras();
    	final String projectName = (String)extraBundle.get("projectName");
    	System.out.println("got projectName: " + projectName);
    	setTitle("Jobs for " + projectName);
    	
    	List<RundeckJob> rdJobsByProject = rdClient.getJobs(projectName);
    	
    	List<String> rdJobNames = new Vector<String>();
        
        for (RundeckJob rundeckJob : rdJobsByProject) {
        	rdJobNames.add(rundeckJob.getName());
        	System.out.println("job found: " + rundeckJob.getName()); 
        	System.out.println("job id found: " + rundeckJob.getId());
		}
        
        setListAdapter(new ArrayAdapter<String>(this, R.layout.jobs, rdJobNames));
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
    	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    	      Intent intent = new Intent(view.getContext(), JobDetailsActivity.class);
              intent.putExtra("jobName", ((TextView)view).getText());
              intent.putExtra("projectName", projectName);
              startActivity(intent);
    	    }
    	});
    }

}
