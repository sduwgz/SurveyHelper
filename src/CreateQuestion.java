import javax.swing.*;

public abstract class CreateQuestion extends JFrame{
	JLabel jl = new JLabel("��������: ");
	JButton clear = new JButton("���");
	JButton cancel = new JButton("ȡ��");
	JButton confirm = new JButton("ȷ��");
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
