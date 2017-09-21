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
import application.view.RecordController;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpeechScript extends Task<Void> {
	private int num;

	private ArrayList<String> words;
	private ActionEvent event;
	RecordController recCon;
	
	public SpeechScript(int number,RecordController recordController) {
		words = new ArrayList<>();
		this.recCon = recordController;
	}

	// records voice
	// converts audio to txt
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

	@Override
	protected void succeeded() {
		super.succeeded();
		try {
			this.readAnswerFile();
			// hands control back to recordController
			recCon.afterResult(words, event);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void cancelled() {
		super.cancelled();
		updateMessage("Cancelled!");

	}


	public void setEvent(ActionEvent event2) {
		event = event2;
	}

	@Override 
	protected void failed() {
		super.failed();
		updateMessage("Failed!");

	}


	public void readAnswerFile() throws IOException {
		FileReader fr = new FileReader("HTK/MaoriNumbers/recout.mlf");
		BufferedReader br = new BufferedReader(fr);
		String sCurrentLine;
		boolean pro = false;
		int count = 0;
		while ((sCurrentLine = br.readLine()) != null) {
			if(!sCurrentLine.equals("sil") & pro & !sCurrentLine.equals(".")) {		
				words.add(sCurrentLine);
				System.out.println(sCurrentLine);
			}
			if(sCurrentLine.equals("sil")) {
				pro = true;
			}
		}
	}
	
	public void playRecording() {
		ProcessBuilder pb3 = new ProcessBuilder("bash", "-c", "aplay foo.wav")
				.redirectErrorStream(true);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
