package com.design.communicate;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.sound.sampled.AudioSystem;

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
		// Set up
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("6ce30912-e2be-4a99-9e7a-4e712c5d0cd5", "6XZo4JOLKy6W");
		
		// Stream audio
		InputStream audioIn = new AudioSystem.getAudioInputStream(audioURL);
		
		// Transcribe the audio
		RecognizeOptions options = new RecognizeOptions().continuous(true).interimResults(false	);
		
		SpeechResults transcript = service.recognizeWS(audioIn, options, new BaseRecognizeDelegate());
		
		System.out.println(transcript);
		return transcript.toString();
	}
	
	public void toVoice () {
		TextToSpeech service = new TextToSpeech();
		service.setUsernameAndPassword("6ce30912-e2be-4a99-9e7a-4e712c5d0cd5", "6XZo4JOLKy6W");

		List <Voice> voices = service.getVoices();
		System.out.println(voices);
	}
	
	
	
}
