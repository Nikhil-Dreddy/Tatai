package application.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import application.MainApp;
import application.view.PraticeRecordController;
import application.view.RecordController;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpeechScript extends Task<Void> {

	private ArrayList<String> words = new ArrayList<String>();
	private ActionEvent event;
	RecordController recordController = new RecordController();;
	PraticeRecordController praticeController = new PraticeRecordController();
	public SpeechScript(RecordController rC) {
		recordController = rC;
	}
	public SpeechScript(PraticeRecordController rC) {
		praticeController = rC;
	}

	/**
	 * Records the user and passes on the recording to the Hvite which the converts the audio into the maori words spoken 
	 * in text format.
	 */
	@Override
	protected Void call() throws Exception {
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", "arecord -d 2 -r 22050 -c 1 -i -t wav -f s16_LE foo.wav")
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

		ProcessBuilder pb2 = new ProcessBuilder("bash", "-c","HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  -w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  user/dictionaryD user/tiedList foo.wav")
				.redirectErrorStream(true);
		pb2.directory(new File("HTK/MaoriNumbers"));
		try {			
			Process process = pb2.start();
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

	/*
	 * Reads file produced and returns array list
	 */
	@Override
	protected void succeeded() {
		super.succeeded();
		recordController.makeButtonsVisible();
		praticeController.makeButtonsVisible();
	}

	@Override
	protected void cancelled() {
		super.cancelled();
		updateMessage("Cancelled!");
		System.out.println("Eskit");
	}


	public void setEvent(ActionEvent event2) {
		event = event2;
	}

	
	@Override 
	protected void failed() {
		super.failed();
		updateMessage("Failed!");
		System.out.println("Failed");
	}

	/**
	 * The following method reads the file which contains the output of the recording file after the recording
	 * has been passed into Hvite
	 * @throws IOException
	 */
	public void readAnswerFile() throws IOException {
		FileReader fr = new FileReader("HTK/MaoriNumbers/recout.mlf");
		BufferedReader br = new BufferedReader(fr);
		String sCurrentLine;
		boolean pro = false;
		
		while ((sCurrentLine = br.readLine()) != null) {
			if(!sCurrentLine.equals("sil") & pro & !sCurrentLine.equals(".")) {	
				System.out.println(sCurrentLine);
				words.add(sCurrentLine);
			}
			if(sCurrentLine.equals("sil")) {
				pro = true;
			}
		}
//		return words;
	}
	
	/**
	 * The following method use the aplay bash command to call the .wav file which contains 
	 * the recording of the user.
	 */
	public void playRecording() {
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
				if(isCancelled()) {
					break;
				}

				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Getter method for the words arraylist
	public ArrayList<String> getWords(){
		return words;
	}
}
