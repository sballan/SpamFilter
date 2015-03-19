/*
 * BayesScoringSystem is an object containing probability maps for
 * sender, subject, and message body. Each probability map is in 
 * form:
 * 	Map<String wordOrPhrase, double[]{probSpamMessage, probRealMessage} >
 * WHERE:
 *	probSpamMessage = (# spam messages containing word) / (# total messages containing word)
 *	probRealMessage = (# real messages containing word) / (# total messages containing word)
 *
 */
package spam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BayesCommentScoringSystem {
    private static final String BASE_URL = "";
	private static final String BODYMAP_FILE = BASE_URL + "commentBodyMap.csv";
	
	//All words in map are lowercase.
	//Probability map contains: <word, { P(word is in spam message), P(word is in real message) }>
	private Map<String, double[]> bodyProbabilityMap;
	
	//Contains <MappingFileURLString, respectiveProbabilityMap>
	private Map<String, Map<String, double[]>> fileMap = new HashMap<>();
	
	public BayesCommentScoringSystem() {
		initialize();
	}
	
	private void initialize() {
    bodyProbabilityMap = new HashMap<>();
		
		fileMap.put(BODYMAP_FILE, bodyProbabilityMap);
		
		//TEST read CSV word file
		//TODO - implement CSV reader class
		for(String fileName : fileMap.keySet()) {
			Map<String, double[]> probabilityMap = fileMap.get(fileName);
			
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(fileName));
				String line;
				br.readLine();
				
				while((line = br.readLine()) != null) {
					String[] ar = line.split(",");
					double spamMessages = Double.valueOf(ar[1]);
					double realMessages = Double.valueOf(ar[2]);
					double totMessages = spamMessages + realMessages;
					probabilityMap.put(ar[0], new double[]{spamMessages/totMessages, realMessages/totMessages});
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if(br != null) {
					try {
						br.close();
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void write() {
	    
		for(String fileName : fileMap.keySet()) {
			//Write new word map to file
			BufferedWriter bufferedWriter = null;
			Map<String, double[]> probabilityMap = fileMap.get(fileName);
                    
			try {
				bufferedWriter = new BufferedWriter(new FileWriter(
						new File(fileName)));
                
				//Write headers
				bufferedWriter.write("Word,SpamMessages,RealMessages\n");
                
				for(String w : probabilityMap.keySet()) {
                    
					bufferedWriter.write(w + "," + probabilityMap.get(w)[0]
							+ "," + probabilityMap.get(w)[1] + "\n" );
				}
                
			} catch(IOException e) {
				e.printStackTrace();
			}  finally {
				if(bufferedWriter != null) {
					try {
						bufferedWriter.close();
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
        
	}
	
	
  
	public void setBodyProbabilityMap(Map<String, double[]> bodyProbabilityMap) {
		this.bodyProbabilityMap = bodyProbabilityMap;
	}
  
	public Map<String, double[]> getBodyProbabilityMap() {
		return bodyProbabilityMap;
	}
  
}
