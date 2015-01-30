package pushbox;
import java.awt.*;
import java.net.URL;
import javax.swing.*;
//内置精美BUG
public class draw extends JFrame{
	private JLabel man;
	private JLabel box;
	private Container con;
	private ImageIcon wallicon;
	private ImageIcon endicon;
	private ImageIcon boxicon;
	private ImageIcon manicon;
	private JLabel[] jl=null;
	public draw(){		//初始化，读取出要用到的图片
		con = this.getContentPane();
		try {
			URL url=draw.class.getResource("img/box.png");
			if(url==null) throw new NullPointerException("box.png图片资源打开失败");
			boxicon=new ImageIcon(url);
			url=draw.class.getResource("img/man.png");
			if(url==null) throw new NullPointerException("man.png图片资源打开失败");
			manicon=new ImageIcon(url);
			url=draw.class.getResource("img/wall.png");
			if(url==null) throw new NullPointerException("wall.png图片资源打开失败");
			wallicon=new ImageIcon(url);
			url =draw.class.getResource("img/end.png");
			if(url==null) throw new NullPointerException("end.png图片资源打开失败");
			endicon=new ImageIcon(url);
			box=new JLabel();
			box.setIcon(boxicon);con.add(box);
			man=new JLabel();
			man.setIcon(manicon);con.add(man);
			setLayout(null);
			setBounds(200,50,500,650);
			setVisible(true);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		} catch (NullPointerException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			message("图片加载失败！请检查路径。");
			System.exit(-1);
		}
	}
	public void message(String str){	//相当于微软家族的MessageBox，消息对话框
		JOptionPane.showMessageDialog(this,str);
	}
	  public void drawman(Point p){//画人
		man.setBounds(p.x,p.y,50,50);
	}
	  public void drawend(Point p){//画终点
		  JLabel jl= new JLabel();
		  jl.setIcon(endicon);
		  jl.setBounds(p.x,p.y,50,50);
		  con.add(jl);
	  }
	  public void drawmap(Point[] map){//传入数组，画地图
		  jl= new JLabel[map.length];
		  for(int i=0;i!=map.length;i++){
			  jl[i]=new JLabel();
			  jl[i].setIcon(wallicon);
			  jl[i].setBounds(map[i].x,map[i].y,50,50);
			  con.add(jl[i]);
		  }
	}
	  public void drawbox(Point p){//画箱子
		box.setBounds(p.x,p.y,50,50);
	}
	  public void clean(){
		con.removeAll();
		box=new JLabel();
		box.setIcon(boxicon);con.add(box);
		man=new JLabel();
		man.setIcon(manicon);con.add(man);
	  }
	public Container getCon() {	 //获取Container对象，使得在其他类也可以使用
		return con;
	}
}
