import javax.swing.*;

public abstract class CreateQuestion extends JFrame{
	JLabel jl = new JLabel("输入问题: ");
	JButton clear = new JButton("清除");
	JButton cancel = new JButton("取消");
	JButton confirm = new JButton("确认");
	JPanel controlPanel = new JPanel();
	JPanel contentPanel = new JPanel();
	
	CreatePage belogedPage;
	
	public CreateQuestion(CreatePage belogedPage){
		this.belogedPage = belogedPage;
	}
	public void cancel(){
		this.dispose();
	}
	public abstract void setupUI();
	public abstract void setupListener();
	public abstract void addQuestiontoPage();
}
