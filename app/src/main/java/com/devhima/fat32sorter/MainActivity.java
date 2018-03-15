//By DevHima
package com.devhima.fat32sorter;

import android.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		final RadioButton r_btn = (RadioButton) findViewById(R.id.r_inmem);
		r_btn.setChecked(true);
		
		//button sort click event
		Button clickButton = (Button) findViewById(R.id.btnStart);
		clickButton.setOnClickListener( new OnClickListener() {

				@Override
				public void onClick(View v) {
					RelativeLayout l_load = (RelativeLayout) findViewById(R.id.lay_load);
					final TextView mtv = (TextView) findViewById(R.id.mtv1);
					final ProgressBar prg = (ProgressBar) findViewById(R.id.prg1);
					mtv.setText("Loading ...");
					prg.setVisibility(View.VISIBLE);
					l_load.setVisibility(View.VISIBLE);
					AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
					dlgAlert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								if(r_btn.isChecked() == true){ //check where to backup files
									FATSorter.SortFiles(true);
								} else {
									FATSorter.SortFiles(false);
								}
								mtv.setText("Done :)");
								prg.setVisibility(View.INVISIBLE);
							}
						});
					dlgAlert.setMessage("Click OK to start sorting .. It will take a time, be patient :)");
					dlgAlert.setTitle("FAT32Sorter");
					dlgAlert.create().show();
				}
			});
    }
	
}
