package application.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import application.Main;
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
	private Numbers maoriWord;
	private ArrayList<String> words;
	private ActionEvent event;
	int score;
	int qNo;
	private static Status status = Status.Pass ;
	private boolean tryAgain;
	RecordController recCon;
	public SpeechScript(int number,int score,int qNo, boolean tryAgain, RecordController recordController) {
		this.num =number;
		words = new ArrayList<>();
		this.numbertoMaori();
		this.score = score;
		this.qNo = qNo;
		this.tryAgain = tryAgain;
		this.recCon = recordController;
	}
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


		return null;
	}

	@Override
	protected void succeeded() {
		super.succeeded();
		recCon.afterResult(words, maoriWord, event);
	}


	@Override
	protected void cancelled() {
		super.cancelled();
		updateMessage("Cancelled!");

	}

	protected void numbertoMaori() {
		if(num == 1) {
			maoriWord = Numbers.tahi;
		}
		else if(num == 2) {
			maoriWord = Numbers.rua;
		}
		else if(num == 3) {
			maoriWord = Numbers.toru;
		}
		else if(num == 4) {
			maoriWord = Numbers.wha;
		}
		else if(num == 5) {
			maoriWord = Numbers.rima;
		}
		else if(num == 6) {
			maoriWord = Numbers.ono;
		}
		else if(num == 7) {
			maoriWord = Numbers.whitu;
		}
		else if(num == 8) {
			maoriWord = Numbers.waru;
		}
		else if(num == 9) {
			maoriWord = Numbers.iwa;
		}

	}
	public void setEvent(ActionEvent event2) {
		event = event2;
	}
	@Override 
	protected void failed() {
		super.failed();
		updateMessage("Failed!");

	}
}
