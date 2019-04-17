package br.com.cvive.chatbot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

import br.com.cvive.model.Resposta;
import br.com.cvive.utils.JsonUtil;

public class ChatBot {
	
public String callWatson(String mensagem) {
		
		
		IamOptions iamOptions = new IamOptions.Builder().apiKey("api_key_watson").build();
		
		Assistant service = new Assistant("2018-12-27",iamOptions);
		
		service.setUsernameAndPassword("username", "password");
		service.setEndPoint("https://gateway.watsonplatform.net/assistant/api");
		String  workspaceId = "workspaceid";
		
		InputData input = new InputData.Builder(mensagem).build();
		MessageOptions options = new MessageOptions.Builder(workspaceId).input(input).build();
		
		MessageResponse response = service.message(options).execute();
		
		String resposta = response.getOutput().getGeneric().get(0).getText();
		
		return resposta;
	}
	
	public void convertTextToVoice(String audioResposta){
		IamOptions iamOptions = new IamOptions.Builder()
			    .apiKey("api_key_watson")
			    .build();

			TextToSpeech textToSpeech = new TextToSpeech(iamOptions);

			textToSpeech.setUsernameAndPassword( "username", "password" );	
			textToSpeech.setEndPoint("https://stream.watsonplatform.net/text-to-speech/api");
			
			try {
				SynthesizeOptions synthesizeOptions =
					    new SynthesizeOptions.Builder()
					      .text(audioResposta)
					      .accept("audio/wav")
					      .voice("pt-BR_IsabelaVoice")
					      .build();

					  InputStream inputStream =
					    textToSpeech.synthesize(synthesizeOptions).execute();
					  InputStream in = WaveUtils.reWriteWaveHeader(inputStream);

					  OutputStream out = new FileOutputStream("src/test/resources/resposta");
					  byte[] buffer = new byte[1024];
					  int length;
					  while ((length = in.read(buffer)) > 0) {
					    out.write(buffer, 0, length);
					  }

					  out.close();
					  in.close();
					  inputStream.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public String convertVoiceToText() {
		IamOptions iamOptions = new IamOptions.Builder()
			    .apiKey("api_key_watson")
			    .build();

			SpeechToText speechToText = new SpeechToText(iamOptions);

			speechToText.setUsernameAndPassword( "username", "password" );	
			speechToText.setEndPoint("https://stream.watsonplatform.net/speech-to-text/api");
			String resposta = null;
			
			try {
				File audio = new File("src/test/resources/ola_mundo.wav");

				RecognizeOptions options = new RecognizeOptions.Builder()
				  .audio(audio)
				  .contentType(HttpMediaType.AUDIO_WAV)
				  .model("pt-BR_BroadbandModel")
				  .build();

				SpeechRecognitionResults transcript = speechToText.recognize(options).execute();
				
				resposta = transcript.getResults().get(0).getAlternatives().get(0).getTranscript();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return resposta;
		
	}

}
