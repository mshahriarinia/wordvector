package kb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * cat freebase-rdf-latest | sed -n
 * 's;<http://rdf.freebase.com/ns/\([^>]*\)>\s*<http://rdf.freebase.
 * com/ns/\([^>]*\)>\s*<http://rdf.freebase.com/ns/\([^>]*\).*;\1 \2 \3;p' | head
 * 
 * @author morteza
 *
 */
public class Freebase {

	public static void main(String[] args) throws Exception {

		// Scanner sc = new Scanner(new File(""));
		//
		// String s = "";
		// while (sc.hasNextLine()) {
		// s = sc.nextLine();
		// // s.split(regex)
		// }

		String url = "<http://rdf.freebase.com/ns/american_football.football_player.footballdb_id>";
		String record;
		record	= "<http://rdf.freebase.com/ns/astronomy.astronomical_observatory.discoveries>     <http://rdf.freebase.com/ns/type.object.name>       \"Discoveries\"@en ";

		record = "<http://rdf.freebase.com/ns/american_football.football_player.footballdb_id>    <http://www.w3.org/1999/02/2│2-rdf-syntax-ns#type>   <http://www.w3.org/2002/07/owl#FunctionalProperty>      .               ";
		
		record = "<http://rdf.freebase.com/ns/american_football.football_player.footballdb_id>    <http://www.w3.org/2000/01/r│df-schema#domain>       <http://rdf.freebase.com/ns/american_football.football_player>  .    ";
		
		String[] sarr = record.split(">");
		for (int i = 0; i < sarr.length; i++) {
			System.out.println(sarr[i].trim());
		}

		int index = url.lastIndexOf("/");
		String f = url.substring(index + 1, url.length() - 1);
		String base = url.substring(1, index);
		System.out.println(f);
		System.out.println(base);
	}

}
