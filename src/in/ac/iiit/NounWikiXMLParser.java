/**
 * 
 */
package in.ac.iiit;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author ratish
 *
 */
public class NounWikiXMLParser {

	private static final String NOUN_SER = "/home/arya/project/intern/InflectionGeneration/noun.ser";
	private static final String NOUN_OUT = "/home/arya/project/intern/InflectionGeneration/noun.out";
	private static final String ENWIKTIONARY_PAGES_ARTICLES_XML = "/home/arya/Downloads/enwiktionary-20150413-pages-articles.xml";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			final StringBuilder sb = new StringBuilder();
			final Map<String, List<String>> inflections = new HashMap<String, List<String>>();

			DefaultHandler handler = new DefaultHandler() {

				boolean bpage = false;
				boolean btitle = false;
				boolean btext = false;
				private String rootWord;
				int counter = 0;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					if (qName.equalsIgnoreCase("page")) {
						bpage = true;
					}
					if (qName.equalsIgnoreCase("title")) {
						// System.out.println("Start Element :" + qName);
						btitle = true;
					}
					if (qName.equalsIgnoreCase("text")) {
						// System.out.println("Start Element :" + qName);
						btext = true;
						sb.setLength(0);
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					if (qName.equalsIgnoreCase("title")) {
						// System.out.println("End Element :" + qName);
					}

					if (qName.equalsIgnoreCase("text")) {
						// System.out.println("End Element :" + qName);
						btext = false;
						// System.out.println("text: "+sb.toString());
						String textData = sb.toString();
						String regex = "";
						regex = "\\{\\{en-noun[a-z|~-]*\\}\\}";
						Pattern pattern = Pattern.compile(regex);
						Matcher m = pattern.matcher(textData);
						// boolean b = m.find();
						while (m.find()) {
							String value = m.group();
//							System.out.println(value);
							List<String> values = new ArrayList<String>();
							if (!inflections.containsKey(rootWord)) {
								values.add(value);
								inflections.put(rootWord, values);
							} else {
								values = inflections.get(rootWord);
								values.add(value);
								// System.out.println("already present "+rootWord);
							}
//							System.out.println(counter++);

						}
					}

				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (btitle) {
						// System.out.println("title: "+new
						// String(ch,start,length));
						rootWord = new String(ch, start, length);
						btitle = false;
					}

					if (btext) {
						/*
						 * System.out.println("text: "+new
						 * String(ch,start,length));
						 * System.out.println("text continue"+ new String(ch));
						 * btext = false;
						 */
						sb.append(ch, start, length);
					}

				}

			};

			saxParser
					.parse(ENWIKTIONARY_PAGES_ARTICLES_XML,
							handler);

			BufferedWriter bw = new BufferedWriter(new FileWriter(
					NOUN_OUT));
			bw.write(inflections.toString());
			bw.close();

			FileOutputStream fos = new FileOutputStream(
					NOUN_SER);
			ObjectOutput out = null;
			out = new ObjectOutputStream(fos);
			out.writeObject(inflections);
			out.close();
			fos.close();

			// System.out.println(inflections);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
