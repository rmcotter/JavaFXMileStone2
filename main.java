import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class main {
	public static void main(String[] args) throws IOException
    {
		String fileName = "foodItems.txt";
	// System.out.println("Enter the json filename:");
    // Scanner s1 = new Scanner(System.in);
	//String jsonFileName = s1.nextLine();
     parseFile(fileName);
     //s1.close();
    }
	public static void parseFile(String filePath) throws IOException{
		//List<Long> targetLongList = sourceLongList.stream().collect(Collectors.toList());
		List<String> food = (List) Files.lines(Paths.get(filePath))
				// .map(String::trim)
				// .map(String::toLowerCase)
				// .filter(line -> line.contains("filter"))
				// .count();
				.collect(Collectors.toList());
		for (String var : food) 
		{ 
			 System.out.println(var);
		}
		}
	//whaddup bros
	///this is another staged file
				
}