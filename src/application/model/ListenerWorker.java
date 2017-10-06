package application.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;

public class ListenerWorker extends Task<Void>  {

	@Override
	protected Void call() throws Exception {
		System.out.println("eskit");
		ProcessBuilder pb3 = new ProcessBuilder("bash", "-c", "aplay foo.wav")
				.redirectErrorStream(true);
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
