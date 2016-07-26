package scene_structure;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;



public class GlobalVariables extends Variables {

	private static String VARFILE = "data/vars.dat";
	
	public GlobalVariables() {
		super();
		//System.out.println("globalvars");
	}
	
	public void load() throws IOException {
		File varFile = new File(VARFILE);
		Scanner sc = new Scanner(varFile);
		String[] var;
		while(sc.hasNextLine()) {
			var = sc.nextLine().split(" ");
			if(var.length > 1)
				this.addVar(var[0], var[1]);
			else
				System.err.println("Błąd w pliku vars.dat");
				//System.exit(-1);
		}
	}
	
	public void save() throws IOException {
		Writer writer = null;
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(VARFILE), "utf-8"));
		    for(String key : vars.keySet()) {
		    	writer.write(key+" "+vars.get(key)+"\n");
		    }
		} catch (IOException ex){
		  System.err.println("Błąd podczas zapisu zmiennych globalnych.");
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}

	}
	
	
}

