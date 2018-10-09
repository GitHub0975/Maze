import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class maze extends JFrame implements ActionListener
{ 	
	//視窗大小
	private int width;
	private int height;
	//頂端提示文字列
	private JLabel topMessage;
	//JButton陣列(放按鈕)
	private JButton[][] buttons;
	//迷宮按鈕所放置的空間
	private JPanel buttonPanel;
	//迷宮的變數
	private int[][] maze;
	//迷宮規劃是否完成
	
	
	
	private boolean mazeReady = false;
	//走出迷宮步數
	private int step = 0;
	//規劃路線是否找到出口
	private boolean find = false;    
	
	public static void main(String[] args) 
	{			
		//框架
		maze Test = new maze();
		Test.setSize(850, 850);
		Test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Test.setVisible(true);
	}
	public maze()
	{ 	
		//視窗名子
		super("The mouse in the maze~");
		
		//設定排序
		setLayout(new BorderLayout());
		
		//showInputDialog(列)
		while(width==0||width<0){
		String temp = JOptionPane.showInputDialog(null, "Enter the row of maze", "Row" ,JOptionPane.QUESTION_MESSAGE);
		//字串轉整數
		width = Integer.parseInt(temp);
		}
		//行
		while(height==0||height<0){
		String temp1 = JOptionPane.showInputDialog(null, "Enter the column of maze", "Column", JOptionPane.QUESTION_MESSAGE);
		height = Integer.parseInt(temp1);	
	    };
		//Top message
		topMessage = new JLabel("Click the path~", SwingConstants.CENTER);
		topMessage.setFont(new Font(null, 1, 30));
		add(topMessage, BorderLayout.NORTH);
		
		//Set Maze
		//迷宮陣列 將四周加一道牆
		maze = new int[height + 2][width + 2];
		
		buttonPanel = new JPanel();
		//網格狀Panel
		buttonPanel.setLayout(new GridLayout(height + 2, width + 2));
		//建立迷宮的按鈕
		buttons = new JButton[height + 2][width + 2];		
		
		for(int i = 0; i < height + 2; i++)
		{
			for(int j = 0; j < width + 2; j++)
			{
				//外框看不見也不可按
				if(i == 0 || i == height + 1 || j == 0 || j == width + 1)
				{
					maze[i][j] = 2;
					buttons[i][j] = new JButton();
		        	buttons[i][j].setEnabled(false);
		        	buttons[i][j].setVisible(false);
				}
				//入口 
				else if(i == 1 && j == 1)
				{
	                maze[i][j] = 0;
					buttons[i][j] = new JButton("entry");
					buttons[i][j].setFont(new Font(null, 1, 15));
	                buttons[i][j].setBackground(Color.BLUE);
	                buttons[i][j].setEnabled(false);
				}
				//出口 
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
					//預設牆壁
	                maze[i][j] = 2;
					buttons[i][j] = new JButton();
	                buttons[i][j].setBackground(Color.BLACK);
	                buttons[i][j].addActionListener(this);
					//設定每個button的指令(按鈕的名字)
					buttons[i][j].setActionCommand("mazeButton"+" "+i+" "+j);
				}
				//將每個按鈕個別加入相對位置的panel
				buttonPanel.add(buttons[i][j]);
			}
		}
		add(buttonPanel, BorderLayout.CENTER);
		//設置下方按鈕
		JButton downButton = new JButton("design complete!");
		downButton.setFont(new Font(null, 1, 20));	
		downButton.addActionListener(this);
		//設定指令
		downButton.setActionCommand("downButton");
		downButton.setBackground(Color.WHITE);
		add(downButton, BorderLayout.SOUTH);
	}

	//@Override
	public void actionPerformed(ActionEvent e) 
	{
		//將指令轉成字串儲存
		String command = e.getActionCommand();
				
		//將指令以空白為分隔化成陣列
		String[] seperateCommand = command.split(" ");
		
		//設定迷宮
		if(seperateCommand[0].equals("mazeButton") && mazeReady == false)
		{
			int x = Integer.parseInt(seperateCommand[1]);
			int y = Integer.parseInt(seperateCommand[2]);
			//點擊為通路
			maze[x][y] = 0;
			buttons[x][y].setBackground(Color.WHITE);
		}
		
		//設定迷宮規劃完畢
		else if(seperateCommand[0].equals("downButton")  && mazeReady == false)
		{
			topMessage.setText("path");	
			if(visit(1, 1))
			{
				//顯示路徑
				displayPath();					
				//顯示路徑長度訊息
				String message = String.format("total length of path : " + step);
				JOptionPane.showMessageDialog(null, message);
			}
			else
			{
				//沒找到路徑
				String message = String.format("No path can be found!");
				JOptionPane.showMessageDialog(null, message);
			}
			mazeReady=true;
		}
	}	
	//規劃迷宮路徑
	public boolean visit(int x, int y)
	{
		//走過的路設為1
		maze[x][y] = 1;
		//走到終點
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
		
		//走過但沒走到終點的路還原預設值
		if(find!=true)
		{
			maze[x][y] = 0;			
		}
		
		return find;
	}	
	
	//顯示路徑
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
