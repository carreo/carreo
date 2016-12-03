package fr.squareprod.vrranking.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import fr.squareprod.vrranking.model.Rank;
import fr.squareprod.vrranking.repo.RankRepository;

@Component
public class RankService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String USER_AGENT = "Mozilla/5.0";

	@Autowired
	private RankRepository rankRepo;

	@Scheduled(fixedDelay = 600000)
	public void getRankingInfosFromVr() {
		String url = "http://vendeeglobe.virtualregatta.com/core/Service/ServiceCaller.php?service=SearchUsers&id_user=2478807&pseudo=carreo&checksum=430da539caf32b8e876b2b2413940cc92c651455";
		try {
			URL obj = new URL(url);
			// Proxy proxy = new Proxy(Proxy.Type.HTTP, new
			// InetSocketAddress("193.56.47.8", 8080));
			//HttpURLConnection con = (HttpURLConnection) obj.openConnection(proxy);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			int responseCode = con.getResponseCode();
			logger.debug("\nSending 'GET' request to URL : {}", url);
			logger.debug("Response Code : {}", responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			String userId = "2478807";
			String rank = getRankfromResponse(response.toString(), userId);
			rankRepo.save(new Rank(userId, rank, new Date()));
			// print result
			logger.info("recording User: {}, Rank : {}", userId, rank);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getRankfromResponse(String xmlResponse, String userId) throws Exception {

		InputSource source = new InputSource(new StringReader(xmlResponse));

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(source);

		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();

		return xpath.evaluate("string(//user[@id_user='" + userId + "']/@rank)", document);
	}

}
