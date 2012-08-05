package de.christianzunker.android.rundeck.activities;

import java.util.List;

import org.rundeck.api.RundeckClient;
import org.rundeck.api.domain.RundeckExecution;
import org.rundeck.api.domain.RundeckJob;

import de.christianzunker.android.rundeck.utils.RundeckClientFactory;
import de.christianzunker.android.rundeck.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class JobDetailsActivity extends Activity {
	
	RundeckClient rdClient;
	RundeckJob selectedJob;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	RundeckClientFactory rdClientFactory = new RundeckClientFactory();
        rdClient = rdClientFactory.getClient();
    	
    	Intent intent = getIntent();
    	Bundle extraBundle = intent.getExtras();
    	String jobName = (String)extraBundle.get("jobName");
    	String projectName = (String)extraBundle.get("projectName");
    	System.out.println("got jobName: " + jobName);
    	System.out.println("got projectName: " + projectName);
    	setTitle("Details for " + jobName);

    	List<RundeckJob> jobs = rdClient.getJobs(projectName);
    	System.out.println("found # jobs for project " + projectName + ": " + jobs.size());
    	for (RundeckJob rundeckJob : jobs) {
    		System.out.println("current Job: " + rundeckJob.getName());
			if (rundeckJob.getName().equals(jobName)) {
				selectedJob = rundeckJob;
				System.out.println("got selectedJob: " + selectedJob.getName());
				break;
			}
		}
    	
    	setContentView(R.layout.job_details);
    	TextView jobNameText = (TextView)findViewById(R.id.jobName);
    	jobNameText.setText(selectedJob.getName());
    	
    	TextView jobDescriptionText = (TextView)findViewById(R.id.jobDescription);
    	jobDescriptionText.setText(selectedJob.getDescription());
    	
    	
    	Button runButton = (Button)findViewById(R.id.runButton);
    	runButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                System.out.println("call run now");
                runJob();
            }
        });
    }
	
    void runJob() {
    	RundeckExecution rdExec = rdClient.runJob(selectedJob.getId());
    	setContentView(R.layout.job_details);
    	setTitle("Details for " + selectedJob.getName());
    	TextView jobNameText = (TextView)findViewById(R.id.jobName);
    	jobNameText.setText(selectedJob.getName());
    	
    	TextView jobDescriptionText = (TextView)findViewById(R.id.jobDescription);
    	jobDescriptionText.setText(selectedJob.getDescription());
    	
    	TextView jobStatusText = (TextView)findViewById(R.id.jobStatus);
    	jobStatusText.setText(rdExec.getStatus().name() + " (Duration: " + rdExec.getDuration() + ")");
    }
}