package de.christianzunker.android.rundeck.activities;

import java.util.List;
import java.util.Vector;

import org.rundeck.api.RundeckClient;
import org.rundeck.api.domain.RundeckJob;
import org.rundeck.api.domain.RundeckProject;

import de.christianzunker.android.rundeck.R;
import de.christianzunker.android.rundeck.utils.RundeckClientFactory;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RundeckAndroidActivity extends ListActivity {
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
		try {
	        super.onCreate(savedInstanceState);
	        
	        RundeckClientFactory rdClientFactory = new RundeckClientFactory();
	        RundeckClient rdClient = rdClientFactory.getClient();
	        System.out.println("got client");
	        List<RundeckProject> rdProjects = rdClient.getProjects();
	        System.out.println("got project list");
	        System.out.println("size: " + rdProjects.size()); 
	        List<String> rdProjectNames = new Vector<String>();
	        
	        for (RundeckProject rundeckProject : rdProjects) {
	        	rdProjectNames.add(rundeckProject.getName());
	        	System.out.println("project found: " + rundeckProject.getName()); 
			}
	        
	        setListAdapter(new ArrayAdapter<String>(this, R.layout.projects, rdProjectNames));
	        
	        ListView lv = getListView();
	        lv.setOnItemClickListener(new OnItemClickListener() {
	    	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    	      Intent intent = new Intent(view.getContext(), JobsActivity.class);
	              intent.putExtra("projectName", ((TextView)view).getText());
	              System.out.println("call JobsActivity with Button 1");
	              startActivity(intent);
	    	    }
	    	});
	        
		}
		catch (Exception ex) {
			Throwable cause = ex.getCause();
			// UnknownHostException
			// HttpHostConnectionException
			ex.printStackTrace();
		}
    }
}