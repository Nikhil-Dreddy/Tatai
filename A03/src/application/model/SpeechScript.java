package application.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.concurrent.Task;

public class SpeechScript extends Task<Void> {
	private int num;
	public SpeechScript(int number) {
		this.num =number;
	}
	@Override
	protected Void call() throws Exception {
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", "source GoSpeech")
				.redirectErrorStream(true);
		pb.directory(new File("HTK/MaoriNumbers"));
		try {			
			Process process = pb.start();
			String line = "";
			InputStream out = process.getInputStream();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(out));

			while ((line = stdout.readLine()) != null ) {	
				if(isCancelled()) {
					break;
				}

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
