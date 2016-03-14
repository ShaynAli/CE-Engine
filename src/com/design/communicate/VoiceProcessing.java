package com.design.communicate;

import java.io.File;
import java.util.List;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

public class VoiceProcessing {
	
	public String getString (File audio) {
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("6ce30912-e2be-4a99-9e7a-4e712c5d0cd5", "6XZo4JOLKy6W");


		SpeechResults transcript = service.recognize(audio, HttpMediaType.AUDIO_WAV);
		System.out.println(transcript);
		return transcript;
	}
	
	public void toVoice () {
		TextToSpeech service = new TextToSpeech();
		service.setUsernameAndPassword("6ce30912-e2be-4a99-9e7a-4e712c5d0cd5", "6XZo4JOLKy6W");

		List <Voice> voices = service.getVoices();
		System.out.println(voices);
	}
	
	
	
}
