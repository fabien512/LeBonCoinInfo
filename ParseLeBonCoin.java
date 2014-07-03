import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.Descriptor;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;


public class ParseLeBonCoin {
		
		/*
		Get a list of href from the url
		*/
		public  void getUrl(String directory) {
			org.jsoup.nodes.Document doc;
			
			//File file = new File(directory);
			try {
				//doc =  Jsoup.parse(file,null);
				doc = Jsoup.connect("http://www.leboncoin.fr/ventes_immobilieres/offres/ile_de_france/paris/?f=a&th=1&ps=4&pe=31&sqs=1&sqe=16").get();
				Elements url = doc.select(".list-lbc a");
				
				for (int i=0;i<url.size();i++){
					System.out.println(url.get(i).attr("href"));
					//System.out.println(url.size());
				//	System.out.println(url.attr("href"));
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		/*
		Get the list of informations which are interesting to get
		*/
		public String[] getInfos(String directory) {
			org.jsoup.nodes.Document doc;
			String prix = "";
			String ville = "";
			String cp = "";
			String type = "";
			String piece = "";
			String surface = "";
			File file = new File(directory);
			String res[] = new String[7];
			try {
				doc =  Jsoup.parse(file,null);
				//doc = Jsoup.connect("http://www.leboncoin.fr/ventes_immobilieres/514147978.htm?ca=12_s").get();
				Elements url = doc.select("tr");
				Elements descriptionTmp = doc.select(".content");
				String description = descriptionTmp.text().toString();
				
				
				for (int i=0;i<url.size();i++){
					String descriptif = url.get(i).text();
					if(descriptif.contains("Prix")) {
						int posPrix = descriptif.indexOf("Prix");
						prix = descriptif.substring(posPrix+ 6,posPrix+14).replaceAll("\\W","");
					}
					if(descriptif.contains("Ville")) {
						int posVille = descriptif.indexOf("Ville");
						ville = descriptif.substring(posVille+ 8,posVille+13);
					}
					if(descriptif.contains("Code postal")) {
						int posCp = descriptif.indexOf("Code postal");
						cp = descriptif.substring(posCp+ 14,posCp+19);
					}
					if(descriptif.contains("Type de bien")) {
						int posTdb = descriptif.indexOf("Type de bien");
						type = descriptif.substring(posTdb+15,posTdb+26);
					}
					if(descriptif.contains("Pièces")) {
						int posPie = descriptif.indexOf("Pièces");
						piece = descriptif.substring(posPie+9,posPie+10);
					}
					if(descriptif.contains("Surface")) {
						int posSur = descriptif.indexOf("Surface");
						surface = descriptif.substring(posSur+10,posSur+13);
					}
				}
				res[0] = description;
				res[1] = prix;
				res[2] = ville;
				res[3] = cp;
				res[4] = type;
				res[5] = piece;
				res[6] = surface;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;
		}
}
