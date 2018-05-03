import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class Calc implements ActionListener{
	JFrame windowFrame;
	JTextField display;
	JButton[] numButtons = new JButton[10];
	Map<String, JButton> fnButtons = new HashMap<String, JButton>();
	
	double storedNum, workingNum;
	int operator;
	final static String[] fn_keys = {"+","-","*","/",".","=","Delete","Clear All"};
	static Map<String, Integer> fn_key_to_num = new HashMap<String, Integer>();
	final static int numButtonSize = 50, marginWidth = 10, buttonSpacing = 20, decorationSize = 40;
	
	void setLayout(){
		windowFrame.setLayout(null);
		windowFrame.setVisible(true);
		windowFrame.setSize(marginWidth*2 + buttonSpacing*3 + numButtonSize*4, decorationSize + marginWidth*2 + buttonSpacing*5 + numButtonSize*6);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.setResizable(false);
		windowFrame.add(display);
		display.setBounds(marginWidth, marginWidth, numButtonSize*4+buttonSpacing*3, numButtonSize);
		
		for(int i = 3; i > 0; i--) {
			for(int j = 2; j >= 0; j--) {
				numButtons[i*3-j].setBounds(
						marginWidth + (2-j)*(numButtonSize+buttonSpacing),
						marginWidth + (4-i)*(numButtonSize+buttonSpacing),
						numButtonSize,
						numButtonSize);				
			}
		}
		numButtons[0].setBounds(
				marginWidth,
				marginWidth + 4*(numButtonSize+buttonSpacing),
				numButtonSize,
				numButtonSize);
		fnButtons.get(".").setBounds(
				numButtons[0].getX() + numButtonSize + buttonSpacing,
				numButtons[0].getY(),
				numButtonSize,
				numButtonSize);
		fnButtons.get("=").setBounds(
				fnButtons.get(".").getX() + numButtonSize + buttonSpacing,
				fnButtons.get(".").getY(),
				numButtonSize,
				numButtonSize);
		fnButtons.get("+").setBounds(
				numButtons[9].getX() + numButtonSize + buttonSpacing,
				numButtons[9].getY(),
				numButtonSize,
				numButtonSize);
		fnButtons.get("-").setBounds(
				numButtons[6].getX() + numButtonSize + buttonSpacing,
				numButtons[6].getY(),
				numButtonSize,
				numButtonSize);
		fnButtons.get("*").setBounds(
				numButtons[3].getX() + numButtonSize + buttonSpacing,
				numButtons[3].getY(),
				numButtonSize,
				numButtonSize);
		fnButtons.get("/").setBounds(
				fnButtons.get("=").getX() + numButtonSize + buttonSpacing,
				fnButtons.get("=").getY(),
				numButtonSize,
				numButtonSize);
		fnButtons.get(fn_keys[7]).setBounds(
				numButtons[0].getX(),
				numButtons[0].getY() + numButtonSize + buttonSpacing,
				numButtonSize*2 + buttonSpacing,
				numButtonSize);
		fnButtons.get(fn_keys[6]).setBounds(
				numButtons[0].getX() + 2*(numButtonSize+buttonSpacing),
				numButtons[0].getY() + numButtonSize + buttonSpacing,
				numButtonSize*2 + buttonSpacing,
				numButtonSize);
		
		for(JButton b : fnButtons.values()) {
			windowFrame.add(b);
		}
		for(int i = 0; i < 10; i++) {
			windowFrame.add(numButtons[i]);
		}
		
		for(JButton b : fnButtons.values()) {
			b.addActionListener(this);
		}
		for(int i = 0; i < 10; i++) {
			numButtons[i].addActionListener(this);
		}
	}
	
	void setLabels(){
		windowFrame = new JFrame("MyCalculator");
		display = new JTextField();
		for(int i = 0; i < 10; i++) {
			numButtons[i] = new JButton(String.valueOf(i));
		}
		
		for(String fn_key : fn_keys) {
			fnButtons.put(fn_key, new JButton(fn_key));
		}
		
	}
	
	void clearAll() {
		storedNum = 0;
		workingNum = 0;
		operator = -1;
		display.setText("");
	}
	
	Calc(){
		setLabels();
		setLayout();
		clearAll();
		if(fn_key_to_num.isEmpty()) {
			for(int i = 0; i < fn_keys.length; i++) {
				fn_key_to_num.put(fn_keys[i], i);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent buttonSignal) {
		JButton button = (JButton) buttonSignal.getSource();
		String text = button.getText();
		if(display.getText() == "WTF?" && text != "Clear All") {
			//do nothing
		}else if(fn_key_to_num.containsKey(text) && text != ".") {
			if(fn_key_to_num.get(text) > fn_key_to_num.get("=")) {//Delete or Clear All
				if("Delete" == text) {
					String shorter = display.getText();
					shorter = shorter.substring(0,shorter.length()-1);
					display.setText(shorter);
				}else {
					clearAll();	
				}			
			}else if(text == "=") {
				workingNum = Double.parseDouble(display.getText());
				switch(operator) {
				case 0: //  +
					storedNum += workingNum;
					break;
				case 1: //  -
					storedNum -= workingNum;
					break;
				case 2: //  *
					storedNum *= workingNum;
					break;
				case 3: //  /
					if(workingNum != 0) {
						storedNum /= workingNum;
						break;
					}else {
						operator = 4;
					}
				default:
					operator = 4;
				}
				if(operator < 4 && operator >= 0) {
					display.setText(String.valueOf(storedNum));
				}else {
					display.setText("WTF?");
				}
			}else {
				operator = fn_key_to_num.get(text);
				storedNum = Double.parseDouble(display.getText());
				display.setText("");
			}
		}else {
			display.setText(display.getText().concat(text));
		}
	}
	
}
