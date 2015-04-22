package f2.spw;
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
 
public class ModifyScore{
	private String score;
 
	public ModifyScore(){
 		score = null;
	}
	public String getScore(){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("./f2/spw/score.txt"));

			score = br.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if(score != null){
			return score;
		}else {
			return "0";
		}
	}

	public void setScore(long highScore){
		try { 
			File file = new File("./f2/spw/score.txt");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Long.toString(highScore));
			bw.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}