import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.ArrayList;

public class XMLOperator{
	private String fileName = "questionare.xml";
	private ArrayList<Page> pageList;
	private String groupInfo;
	public XMLOperator(){
	}
	public XMLOperator(ArrayList<Page> pageList, String groupInfo){
		this.pageList = pageList;
		this.groupInfo = groupInfo;
	}
	public XMLOperator(ArrayList<Page> pageList, String groupInfo, String fileName){
		this.pageList = pageList;
		this.groupInfo = groupInfo;
		this.fileName = fileName;
	}
	public Element getFillIn(Document doc, Question curQues){
		Element question = doc.createElement("question");
		question.setAttribute("ID", "" + curQues.getID());
		//question.setAttribute("ref", "" + curQues.getREF());
		question.setAttribute("type", "" + curQues.getType());
		question.setAttribute("describe", curQues.getQuesDescribe());
		question.setAttribute("set", curQues.getAnswerSet());
		question.setAttribute("min", curQues.getMinRange());
		question.setAttribute("max", "" + curQues.getMaxRange());
		return question;
	}
	public Element getChoise(Document doc, Question curQues){
		Element question = doc.createElement("question");
		question.setAttribute("ID", "" + curQues.getID());
		question.setAttribute("type", "" + curQues.getType());
		question.setAttribute("describe", curQues.getQuesDescribe());
		question.setAttribute("choises", curQues.getChoises());
		question.setAttribute("jumps", curQues.getJumps());
		
		return question;
	}
	public Document newDocument() throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		int pageSize = pageList.size();
		Element pageNodes = doc.createElement("answerSheet");
		pageNodes.setAttribute("group", groupInfo);
		for(int i = 0; i < pageSize; ++ i){
			System.out.println(i);
			Element page = doc.createElement("page");
			
			ArrayList<Question> quesList = pageList.get(i).questionList;
			page.setAttribute("title", pageList.get(i).title.getText());
			page.setAttribute("describe", pageList.get(i).describe.getText());
			page.setAttribute("pr", "" + pageList.get(i).prePage);
			page.setAttribute("ne", "" + pageList.get(i).nextPage);
			page.setAttribute("ref", "" + pageList.get(i).refQues);
			int quesSize = quesList.size();
			for(int j = 0; j < quesSize; ++ j){
				Question curQues = quesList.get(j);
				
				if(curQues.getType() == 1){
					Element question = getFillIn(doc, curQues);
					page.appendChild(question);
				} else if(curQues.getType() == 2){
					Element question = getChoise(doc, curQues);
					page.appendChild(question);
				}	
			}
			pageNodes.appendChild(page);
		}
		doc.appendChild(pageNodes);
		return doc;
	}
	public void writer(String fileName) throws Exception{
		Document doc = this.newDocument();
		DOMSource source = new DOMSource(doc);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		StreamResult result = new StreamResult(new File(fileName));
		t.transform(source, result);
	}
	public AnswerSheet reader(String fileName) throws Exception{
		ArrayList<Page> pageList = new ArrayList<Page>();
		ArrayList<Group> groupList = new ArrayList<Group>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(fileName);
		Element root = document.getDocumentElement();
		String groupInfo = root.getAttributes().getNamedItem("group").getNodeValue();
		if(groupInfo.split(",").length > 1){
			for(String s : groupInfo.split(",")){
				Group g = new Group(s);
				groupList.add(g);
			}
		}
		NodeList pageNodes = root.getChildNodes();
		int pageNumber = pageNodes.getLength();
		//System.out.println(pageNumber);
		for(int i = 0; i < pageNumber; ++ i){
			ArrayList<Question> questionList = new ArrayList<Question>();
			Node onePage = pageNodes.item(i);
			//System.out.println(onePage.toString());
			String pageTitle = onePage.getAttributes().getNamedItem("title").getNodeValue();
			String pageDescribe = onePage.getAttributes().getNamedItem("describe").getNodeValue();
			String pr = onePage.getAttributes().getNamedItem("pr").getNodeValue();
			String ne = onePage.getAttributes().getNamedItem("ne").getNodeValue();
			String ref = onePage.getAttributes().getNamedItem("ref").getNodeValue();
			//System.out.println(pageTitle);
			//System.out.println(pageDescribe);
			NodeList questionNodes = onePage.getChildNodes();
			int questionNumber = questionNodes.getLength();
			for(int j = 0; j < questionNumber; ++ j){
				Node oneQuestion = questionNodes.item(j);
				int quesType = Integer.parseInt(oneQuestion.getAttributes().getNamedItem("type").getNodeValue());
				int ID = Integer.parseInt(oneQuestion.getAttributes().getNamedItem("ID").getNodeValue());
				//int ref = Integer.parseInt(oneQuestion.getAttributes().getNamedItem("ref").getNodeValue());
				//System.out.println("ID:¡¡" + ID);
				//System.out.println("ref:¡¡" + ref);
				String quesDescribe = oneQuestion.getAttributes().getNamedItem("describe").getNodeValue();
				
				if(quesType == 1){
					String answerSet = oneQuestion.getAttributes().getNamedItem("set").getNodeValue();
					String minRange = oneQuestion.getAttributes().getNamedItem("min").getNodeValue();
					String maxRange = oneQuestion.getAttributes().getNamedItem("max").getNodeValue();
					Question q = new FillinQuestion(ID, quesDescribe, answerSet, minRange, maxRange);
					questionList.add(q);
				}else if(quesType == 2){ 
					String choises = oneQuestion.getAttributes().getNamedItem("choises").getNodeValue();
					String jumps = oneQuestion.getAttributes().getNamedItem("jumps").getNodeValue();
					Question q = new ChoiseQuestion(ID, quesDescribe, choises, jumps);
					questionList.add(q);
				}
			}
			Page p = new Page(questionList, i + 1, pageTitle, pageDescribe, pr, ne, ref);
			pageList.add(p);
		}
		AnswerSheet as = new AnswerSheet(pageList, groupList);
		return as;
	}
	public static void main(String args[])throws Exception{
		/*
		ArrayList<Page> pl = new ArrayList<Page>();
		for(int i = 0; i < 5; ++ i){
			Question fillin1 = new FillinQuestion("NAME:");
			Question fillin2 = new FillinQuestion("AGE:");
			ArrayList<Question> ql = new ArrayList<Question>();
			ql.add(fillin1);
			ql.add(fillin2);
			Page p = new Page(ql, i + 1, "testTitle" + i, "testDiscribe");
			pl.add(p);
		}
		//Question fillin3 = new FillinQuestion("NUMBER:");
		//pl.get(0).addQuestion(fillin3);
		*/
		XMLOperator xmlo = new XMLOperator();
		//Document doc = xmlo.newDocument();
		//xmlo.writer(doc, "output.xml");
		AnswerSheet as = xmlo.reader("output.xml");
	}
}