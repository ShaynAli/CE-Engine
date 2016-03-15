package com.design.communicate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class VoiceProcessing {
	
	public static String getString (File audio) {
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("6ce30912-e2be-4a99-9e7a-4e712c5d0cd5", "6XZo4JOLKy6W");

		SpeechResults transcript = service.recognize(audio, HttpMediaType.AUDIO_WAV);
		System.out.println(transcript);
		return transcript.toString();
	}
	
	public static void processAudio(URL audioURL, String streamToURL)
	{
		// Set up
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("6ce30912-e2be-4a99-9e7a-4e712c5d0cd5", "6XZo4JOLKy6W");
		service.setEndPoint(streamToURL);
		
		// Stream audio

		InputStream audioIn = null;
		try {
			audioIn = audioURL.openStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Transcribe the audio
		RecognizeOptions options = new RecognizeOptions().continuous(true).interimResults(false	);
		options.contentType("audio/wav");
		
	
		
		service.recognizeUsingWebSockets(audioIn, options, new BaseRecognizeDelegate()
		{
			@Override
			public void onMessage(SpeechResults speech) {
				System.out.println(speech);
				speech.toString();
			}
		});
	}
	
	public void toVoice () {
		TextToSpeech service = new TextToSpeech();
		service.setUsernameAndPassword("6ce30912-e2be-4a99-9e7a-4e712c5d0cd5", "6XZo4JOLKy6W");

		List <Voice> voices = service.getVoices();
		System.out.println(voices);
	}
	
	
	
}