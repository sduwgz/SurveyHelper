import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AnswerSheet extends JFrame{
	int curPage = 0;
	int curGroup = 0;
	JButton nextPage = new JButton("下一页");
	JButton prePage = new JButton("上一页");
	JButton confirm = new JButton("确认");
	JPanel pagePanel = new JPanel();
	JPanel groupPanel = new JPanel();
	JPanel controlPanel = new JPanel();
	CardLayout card = new CardLayout();
	ArrayList<Page> pageList;
	ArrayList<Group> groupList;
	Page startPage = new Page();
	Page endPage = new Page();
	JButton startButton, endButton;
	ExcelWriter writer;
	public AnswerSheet(ArrayList<Page> pageList, ArrayList<Group> groupList) throws IOException {
		this.pageList = pageList;
		this.groupList = groupList;
		initWriter();
		setupUI();
		setupListener();
	}
	public AnswerSheet(String fileName) {
		XMLOperator xmlo = new XMLOperator();
		try {
			AnswerSheet as = xmlo.reader(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showConfirmDialog(this, "问卷文件不合法", "文件错误提示", JOptionPane.OK_CANCEL_OPTION);
			e.printStackTrace();
		}
	}
	public AnswerSheet(){
		JFileChooser jfc=new JFileChooser();
		jfc.setCurrentDirectory(new File("d:/"));
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int value = jfc.showDialog(new JLabel(), "选择");
		if(value == JFileChooser.APPROVE_OPTION) {
			File file=jfc.getSelectedFile();
			if(file.getAbsolutePath().endsWith(".xml")) {
				try {
					AnswerSheet as = new AnswerSheet("" + file.getAbsolutePath());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("文件:"+file.getAbsolutePath());
				JOptionPane.showConfirmDialog(this, "文件 "+ file.getAbsolutePath() + " 不是问卷", "文件错误提示", JOptionPane.OK_CANCEL_OPTION);
			}
		}
		
	}
	public void setupUI(){
		this.setTitle("调查问卷");
		startButton = new JButton("开始答题");
		startPage.setLayout(new BorderLayout());
		endPage.setLayout(new BorderLayout());
		startPage.add(startButton, new BorderLayout().CENTER);
		endButton = new JButton("提交");
		endPage.add(endButton, new BorderLayout().CENTER);
		pagePanel.setLayout(card);
		controlPanel.setLayout(new GridLayout(1, 3));
		pagePanel.add(startPage);
		int psize = pageList.size();
		for(int i = 0; i < psize; ++ i){
			pagePanel.add(pageList.get(i));
		}
		pagePanel.add(endPage);
		
		groupPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		groupPanel.setLayout(new GridLayout(1, groupList.size()));
		int gsize = groupList.size();
		for(int i = 0; i < gsize; ++ i){
			groupList.get(i).setOpaque(true);
			groupList.get(i).setBorder(BorderFactory.createLineBorder(Color.BLACK));
			groupPanel.add(groupList.get(i));
		}
		
		
		controlPanel.add(prePage);
		controlPanel.add(confirm);
		controlPanel.add(nextPage);
		this.setSize(1000, 800);
		this.setLayout(new GridBagLayout());
		this.add(groupPanel, new GBC(0, 0, 10, 1).setFill(GBC.BOTH).setWeight(0.1, 0.1));
		this.add(pagePanel, new GBC(0, 1, 10, 9).setFill(GBC.BOTH).setWeight(1, 1));
		this.add(controlPanel, new GBC(0, 10, 10, 1).setFill(GBC.BOTH).setWeight(0.1, 0.1));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void setupListener(){
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				groupList.get(curGroup).active();
				int size = pageList.size();
				if(curPage <= size - 1){
					card.next(pagePanel);
				}
				System.out.println(curPage);
			}
		});
		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					submitAll();
				} catch (IOException e1) {
					// TODO Auto-generated catch block(done)
					e1.printStackTrace();
				}
				clearAll();
				restart();
			}
		});
		nextPage.addActionListener(new ActionListener() {
			@Override
			//TODO: the groupList will out of index. 
			public void actionPerformed(ActionEvent e) {
				int size = pageList.size();
				if(curPage < size){
					card.next(pagePanel);
					curPage ++;
					for(int i = 0; i < groupList.size(); ++ i){
						if(curPage > groupList.get(i).endPage)
							groupList.get(i).done();
						if(curPage >= groupList.get(i).startPage && curPage <= groupList.get(i).endPage)
							groupList.get(i).active();
						if(curPage < groupList.get(i).startPage)
							groupList.get(i).unreach();
					}
				} else {
					curPage = 0;
					groupList.get(groupList.size() - 1).done();
					card.next(pagePanel);
				}
				System.out.println(curPage);
			}
		});
		prePage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(curPage > 0){
					card.previous(pagePanel);
					curPage --;
					for(int i = 0; i < groupList.size(); ++ i){
						if(curPage > groupList.get(i).endPage)
							groupList.get(i).done();
						if(curPage >= groupList.get(i).startPage && curPage <= groupList.get(i).endPage)
							groupList.get(i).active();
						if(curPage < groupList.get(i).startPage)
							groupList.get(i).unreach();
					}
				}
				System.out.println(curPage);
			}
		});
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pageList.get(curPage).submit();
			}
		});
	}
	public void initWriter() {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");//设置日期格式
			String date = df.format(new Date());// new Date()为获取当前系统时间
			String outFileName = "output_" + date + ".xls";
			writer = new ExcelWriter(outFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> table = new ArrayList<String>();
		for(Page page : pageList){
			for(Question q : page.questionList){
				table.add(q.getQuesDescribe());
			}
		}
		//A bad transform from ArrayList to String[]
		int tableSize = table.size();
		try {
			writer.newTable((String[])table.toArray(new String[tableSize]));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addPage(Page newPage){
		pageList.add(newPage);
		this.pagePanel.add(newPage);
		this.repaint();
	}
	public void clearAll(){
		int size = pageList.size();
		for(int i = 0; i < size; ++ i){
			pageList.get(i).clear();
		}
	}
	public void submitAll() throws IOException{
		ArrayList<String> answerList = new ArrayList<String>();
		ArrayList<String> remarkList = new ArrayList<String>();
		int size = pageList.size();
		for(int i = 0; i < size; ++ i){
			int quesNumber = pageList.get(i).questionList.size();
			remarkList.add(pageList.get(i).getRemark());
				for(int j = 0; j < quesNumber; ++ j){
					answerList.add(pageList.get(i).questionList.get(j).getAnswer());
				}
		}
		int shellSize = answerList.size();
		String[] row = new String[shellSize];
		for(int i = 0; i < shellSize; ++ i){
			row[i] = answerList.get(i);
		}
		writer.writeRows(row);
		int remarkSize = remarkList.size();
		for(int i = 0; i < remarkSize; ++ i){
			System.out.println(remarkList.get(i));
		}
		curPage = 0;
	}
	public void restart(){
		card.first(pagePanel);
	}
	public static void main(String[] args) throws Exception{
		AnswerSheet as = new AnswerSheet();
	}
}
