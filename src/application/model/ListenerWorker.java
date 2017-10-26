package application.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import application.view.PraticeRecordController;
import application.view.RecordController;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;

public class ListenerWorker extends Task<Void>  {

	/**
	 * The following method use the aplay bash command to call the .wav file which contains 
	 * the recording of the user.
	 */
	private RecordController a;
	private PraticeRecordController b;
	public ListenerWorker(RecordController c) {
		this.a= c;
	}
	public ListenerWorker(PraticeRecordController praticeRecordController) {
		b = praticeRecordController;
	}
	public ListenerWorker() {
		
	}
	@Override
	protected Void call() throws Exception {
		ProcessBuilder pb3 = new ProcessBuilder("bash", "-c", "aplay foo.wav")
				.redirectErrorStream(true);
		//change directory to the directory which contains the recording
		pb3.directory(new File("HTK/MaoriNumbers"));
		try {			
			Process process = pb3.start();
			String line = "";
			InputStream out = process.getInputStream();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(out));

			while ((line = stdout.readLine()) != null ) {	
				System.out.println(line);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
	
	@Override
	protected void succeeded() {
		if(a != null ) {
			a.makeButtonsVisible();
		}
		super.succeeded();
		updateMessage("Success!");
	}

	@Override
	protected void cancelled() {
		super.cancelled();
		updateMessage("Cancelled!");

	}

	@Override 
	protected void failed() {
		super.failed();
		updateMessage("Failed!");

	}
}
