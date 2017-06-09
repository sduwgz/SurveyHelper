
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.ArrayList;
public class DOMDemo02{
	public static void main(String args[])throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element persons = doc.createElement("persons");
		for(int i = 0; i < 10; ++ i){
			Element person = doc.createElement("person");
			Element age = doc.createElement("age");
			Element name = doc.createElement("name");
			Text xiazdong = doc.createTextNode("xiazdong");
			Text ageText = doc.createTextNode("15");
			age.appendChild(ageText);
			name.appendChild(xiazdong);
			person.appendChild(name);
			person.appendChild(age);
			persons.appendChild(person);
			System.out.println(i);
		}
		doc.appendChild(persons);
		DOMSource source = new DOMSource(doc);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		StreamResult result = new StreamResult(new File("output.xml"));
		t.transform(source,result);
	}
}