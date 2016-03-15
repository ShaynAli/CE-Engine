package com.design.communicate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import javax.json.JsonObject;

import org.json.JSONObject;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

public class VoiceProcessing {
	
	public static String getString (File audio) {
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("6ce30912-e2be-4a99-9e7a-4e712c5d0cd5", "6XZo4JOLKy6W");

		SpeechResults transcript = service.recognize(audio, HttpMediaType.AUDIO_WAV);
		System.out.println(transcript);
		return transcript.toString();
	}
	
	public static String processAudio(URL audioURL)
	{
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("6ce30912-e2be-4a99-9e7a-4e712c5d0cd5", "6XZo4JOLKy6W");
		
		InputStream audioIn = null;
		try {
			audioIn = audioURL.openStream();
			System.out.println(audioIn.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File audio = null;
		try {
			audio = File.createTempFile(audioURL.getFile(), ".wav");
			OutputStream stream = new FileOutputStream(audio);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = audioIn.read(bytes)) != -1) {
				stream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RecognizeOptions op = new RecognizeOptions();
		op.contentType("audio/wav");
		op.model("en-US_NarrowbandModel");
		
		SpeechResults transcript = service.recognize(audio, op);
		audio.deleteOnExit();
		JSONObject obj = new JSONObject(transcript.toString());
		System.out.println(obj.getJSONObject("results").getJSONObject("alternative").getJSONObject("transcript").toString());
		return obj.getJSONObject("results").getJSONObject("alternative").getJSONObject("transcript").toString();
		

	}
	
//	public static void processAudio(URL audioURL, String streamToURL)
//	{
//		// Set up
//		SpeechToText service = new SpeechToText();
//		service.setUsernameAndPassword("6ce30912-e2be-4a99-9e7a-4e712c5d0cd5", "6XZo4JOLKy6W");
//		service.setEndPoint(streamToURL);
//		
//		// Stream audio
//
//		InputStream audioIn = null;
//		try {
//			audioIn = audioURL.openStream();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		// Transcribe the audio
//		RecognizeOptions options = new RecognizeOptions().continuous(true).interimResults(false	);
//		options.contentType("audio/wav");
//		
//	
//		
//		service.recognizeUsingWebSockets(audioIn, options, new BaseRecognizeDelegate()
//		{
//			@Override
//			public void onMessage(SpeechResults speech) {
//				System.out.println(speech);
//				speech.toString();
//			}
//		});
//	}
	
	public void toVoice () {
		TextToSpeech service = new TextToSpeech();
		service.setUsernameAndPassword("6ce30912-e2be-4a99-9e7a-4e712c5d0cd5", "6XZo4JOLKy6W");

		List <Voice> voices = service.getVoices();
		System.out.println(voices);
	}
	
	
	
}