import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class maze extends JFrame implements ActionListener
{ 	
	//�����j�p
	private int width;
	private int height;
	//���ݴ��ܤ�r�C
	private JLabel topMessage;
	//JButton�}�C(����s)
	private JButton[][] buttons;
	//�g�c���s�ҩ�m���Ŷ�
	private JPanel buttonPanel;
	//�g�c���ܼ�
	private int[][] maze;
	//�g�c�W���O�_����
	
	
	
	private boolean mazeReady = false;
	//���X�g�c�B��
	private int step = 0;
	//�W�����u�O�_���X�f
	private boolean find = false;    
	
	public static void main(String[] args) 
	{			
		//�ج[
		maze Test = new maze();
		Test.setSize(850, 850);
		Test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Test.setVisible(true);
	}
	public maze()
	{ 	
		//�����W�l
		super("The mouse in the maze~");
		
		//�]�w�Ƨ�
		setLayout(new BorderLayout());
		
		//showInputDialog(�C)
		while(width==0||width<0){
		String temp = JOptionPane.showInputDialog(null, "Enter the row of maze", "Row" ,JOptionPane.QUESTION_MESSAGE);
		//�r������
		width = Integer.parseInt(temp);
		}
		//��
		while(height==0||height<0){
		String temp1 = JOptionPane.showInputDialog(null, "Enter the column of maze", "Column", JOptionPane.QUESTION_MESSAGE);
		height = Integer.parseInt(temp1);	
	    };
		//Top message
		topMessage = new JLabel("Click the path~", SwingConstants.CENTER);
		topMessage.setFont(new Font(null, 1, 30));
		add(topMessage, BorderLayout.NORTH);
		
		//Set Maze
		//�g�c�}�C �N�|�P�[�@�D��
		maze = new int[height + 2][width + 2];
		
		buttonPanel = new JPanel();
		//���檬Panel
		buttonPanel.setLayout(new GridLayout(height + 2, width + 2));
		//�إ߰g�c�����s
		buttons = new JButton[height + 2][width + 2];		
		
		for(int i = 0; i < height + 2; i++)
		{
			for(int j = 0; j < width + 2; j++)
			{
				//�~�جݤ����]���i��
				if(i == 0 || i == height + 1 || j == 0 || j == width + 1)
				{
					maze[i][j] = 2;
					buttons[i][j] = new JButton();
		        	buttons[i][j].setEnabled(false);
		        	buttons[i][j].setVisible(false);
				}
				//�J�f 
				else if(i == 1 && j == 1)
				{
	                maze[i][j] = 0;
					buttons[i][j] = new JButton("entry");
					buttons[i][j].setFont(new Font(null, 1, 15));
	                buttons[i][j].setBackground(Color.BLUE);
	                buttons[i][j].setEnabled(false);
				}
				//�X�f 
				else if(i == height && j == width)
				{
	                maze[i][j] = 0;
					buttons[i][j] = new JButton("exit");
					buttons[i][j].setFont(new Font(null, 1, 15));
	                buttons[i][j].setBackground(Color.BLUE);
	                buttons[i][j].setEnabled(false);
				}
				else
				{
					//�w�]���
	                maze[i][j] = 2;
					buttons[i][j] = new JButton();
	                buttons[i][j].setBackground(Color.BLACK);
	                buttons[i][j].addActionListener(this);
					//�]�w�C��button�����O(���s���W�r)
					buttons[i][j].setActionCommand("mazeButton"+" "+i+" "+j);
				}
				//�N�C�ӫ��s�ӧO�[�J�۹��m��panel
				buttonPanel.add(buttons[i][j]);
			}
		}
		add(buttonPanel, BorderLayout.CENTER);
		//�]�m�U����s
		JButton downButton = new JButton("design complete!");
		downButton.setFont(new Font(null, 1, 20));	
		downButton.addActionListener(this);
		//�]�w���O
		downButton.setActionCommand("downButton");
		downButton.setBackground(Color.WHITE);
		add(downButton, BorderLayout.SOUTH);
	}

	//@Override
	public void actionPerformed(ActionEvent e) 
	{
		//�N���O�ন�r���x�s
		String command = e.getActionCommand();
				
		//�N���O�H�ťլ����j�Ʀ��}�C
		String[] seperateCommand = command.split(" ");
		
		//�]�w�g�c
		if(seperateCommand[0].equals("mazeButton") && mazeReady == false)
		{
			int x = Integer.parseInt(seperateCommand[1]);
			int y = Integer.parseInt(seperateCommand[2]);
			//�I�����q��
			maze[x][y] = 0;
			buttons[x][y].setBackground(Color.WHITE);
		}
		
		//�]�w�g�c�W������
		else if(seperateCommand[0].equals("downButton")  && mazeReady == false)
		{
			topMessage.setText("path");	
			if(visit(1, 1))
			{
				//��ܸ��|
				displayPath();					
				//��ܸ��|���װT��
				String message = String.format("total length of path : " + step);
				JOptionPane.showMessageDialog(null, message);
			}
			else
			{
				//�S�����|
				String message = String.format("No path can be found!");
				JOptionPane.showMessageDialog(null, message);
			}
			mazeReady=true;
		}
	}	
	//�W���g�c���|
	public boolean visit(int x, int y)
	{
		//���L�����]��1
		maze[x][y] = 1;
		//������I
		if(x == height && y == width)
		{
			find = true;
		}
		if(find!=true && maze[x+1][y] == 0)
		{
			visit(x+1, y);
		}
		if(find!=true&& maze[x][y+1] == 0)
		{
			visit(x, y+1);
		}
		if(find!=true && maze[x-1][y] == 0)
		{
			visit(x-1, y);
		}
		if(find!=true && maze[x][y-1] == 0)
		{
			visit(x, y-1);
		}
		if(find!=true && maze[x - 1][y-1] == 0)
		{
			visit(x - 1, y-1);
		}
		if(find!=true && maze[x + 1][y+1] == 0)
		{
			visit(x + 1, y+1);
		}
		if(find!=true && maze[x - 1][y+1] == 0)
		{
			visit(x - 1, y+1);
		}
		if(find!=true && maze[x + 1][y-1] == 0)
		{
			visit(x + 1, y-1);
		}
		
		//���L���S������I�����٭�w�]��
		if(find!=true)
		{
			maze[x][y] = 0;			
		}
		
		return find;
	}	
	
	//��ܸ��|
	public void displayPath()
	{
		for(int i = 1; i < height + 1; i++)
		{
			for(int j = 1; j < width + 1; j++)
			{
				if(maze[i][j] == 1)
				{
						buttons[i][j].setBackground(Color.YELLOW);
						step++;
					
				}
			}
		}
		
	}
	
}
