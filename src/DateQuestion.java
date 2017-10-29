import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.*;

import com.eltima.components.ui.DatePicker;
public class DateQuestion extends Question{
	DatePicker datepick;

	public DateQuestion(int ID, String quesDescribe, String ans) {
		super(ID, quesDescribe, ans);
		this.quesType = 5;
		datepick = new DatePicker();
		String DefaultFormat = "yyyy-MM-dd";
        // ��ǰʱ��
        Date date = new Date();
        // ����
        Font font = new Font("Times New Roman", Font.BOLD, 12);
   
        Dimension dimension = new Dimension(200, 24);
   
        datepick = new DatePicker(date, DefaultFormat, font, dimension);
        //datepick.setLocation(137, 83);
        //datepick.setBounds(137, 83, 177, 24);
        // ����һ���·�����Ҫ������ʾ������
        //datepick.setHightlightdays(hilightDays, Color.red);
        // ����һ���·��в���Ҫ�����ӣ��ʻ�ɫ��ʾ
        //datepick.setDisableddays(disabledDays);
        // ���ù���
        datepick.setLocale(Locale.CHINA);
        // ����ʱ�����ɼ�
        //datepick.setTimePanleVisible(true);
		setupUI();
	}
	
	public void setupUI(){
		Font font = new Font("����",Font.PLAIN,20);
		if(quesDescribe.split("_").length == 2)
			jl = new JLabel(" " + (ID+1) +". " + quesDescribe.split("_")[1], JLabel.LEFT);
		else
			jl = new JLabel(" " + (ID+1) +". " + quesDescribe, JLabel.LEFT);
		jl.setFont(font);
		
		this.setLayout(new GridLayout(1, 2));
		this.add(jl);
		this.add(datepick);
		
		this.setVisible(true);
	}
	public void setAnswerSet(String answerSet){
		
	}
	public boolean submit(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		String date = df.format(datepick.getValue());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		this.setAnswer(date);
		return true;
	}
	public void getFocus(){
		System.out.println("Focus");
		this.datepick.requestFocus(true);
	}
	public void clear(){
		this.setAnswer("");
	}
	public boolean checkAnswer(String tempAnswer){
		return true;
	}
}
