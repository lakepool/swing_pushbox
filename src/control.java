package pushbox;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
//内置精美BUG

public class control {
	//地图
	Point endp = new Point();//最终位置
	Point manp = new Point();//人的位置
	Point boxp = new Point();//箱子的位置
	Point[] map=new Point[]{};
	draw drw = new draw();
	File mapfile = null;
	//buttons
	JButton jb=null;
	JButton jbup=null;
	JButton jbdown=null;
	JButton jbleft=null;
	JButton jbright=null;

	public Point[] makemap(int[] nums){			//生成墙的坐标
		if(nums==null||nums.length==0) return null;
		Point[] re = new Point[nums[0]*2+nums[1]*2-4+(nums.length-8)/2];
		try {
				int ip=0;
				for(int i=0;i!=nums[0];i++){
					re[ip]=new Point((0+i)*50,0);
					re[ip+1]=new Point((0+i)*50,(nums[1]-1)*50);
					ip+=2;
				}
				for(int i=0;i!=nums[1]-2;i++){
					re[ip]=new Point(0,(1+i)*50);
					re[ip+1]=new Point((nums[0]-1)*50,(1+i)*50);
					ip+=2;
				}
				for(int i=0;i!=nums.length-8;i+=2){
					re[ip]= new Point(nums[8+i]*50,nums[9+i]*50);
					ip++;
				}
				return re;
			} catch (Exception e) {
				e.printStackTrace();
				drw.message("地图错误，无法使用！");
				return null;
			}
	}
	
	public boolean loadmap(String path){		//从文件中读取地图并绘制在面板上
		try {
		File fp = new File(path);
		if(!fp.exists()) throw new Exception(path+"地图打开失败！");
		FileReader fpr = new FileReader(fp);
		char bytes[] = new char[1024];
		int len = fpr.read(bytes);
		String str= new String(bytes,0,len);
		String[] strs=str.split(",");
		int[] nums=new int[strs.length];
		for(int i=0;i!=strs.length;i++){
			nums[i]=Integer.parseInt(strs[i]);
		}
		
		map=makemap(nums);
		drw.clean();				//清空
		drw.add(jb);drw.add(jbright);drw.add(jbleft);drw.add(jbdown);drw.add(jbup);
		drw.setBounds(200,50,50*nums[0],150+50*nums[1]);		//根据地图生成按钮位置
		jb.setBounds(50*nums[0]-100, 50*nums[1], 90, 40);
		jbup.setBounds(50*nums[0]/2-50, 50*nums[1], 50, 50);
		jbdown.setBounds(50*nums[0]/2-50, 50*nums[1]+50, 50, 50);
		jbleft.setBounds(50*nums[0]/2-100, 50*nums[1], 50, 100);
		jbright.setBounds(50*nums[0]/2, 50*nums[1], 50, 100);
		endp=new Point(nums[2]*50,nums[3]*50);
		manp=new Point(nums[4]*50,nums[5]*50);
		boxp=new Point(nums[6]*50,nums[7]*50);
		drw.drawmap(map);
		drw.drawman(manp);
		drw.drawbox(boxp);
		drw.drawend(endp);
		return true;
		} catch (IOException e) {
			e.printStackTrace();
			drw.message("地图资源打开失败，请手动选择！");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			drw.message("地图错误，无法使用！");
			return false;
		}
	}
	public control(){
		jb = new JButton("读取地图");
		jb.setBounds(400, 500, 90, 40);
		drw.add(jb);
		jbup = new JButton("^");
		jbup.setBounds(200, 500, 50, 50);
		jbdown = new JButton("v");
		jbdown.setBounds(200, 550, 50, 50);
		jbleft = new JButton("<");
		jbleft.setBounds(150, 500, 50, 100);
		jbright = new JButton(">");
		jbright.setBounds(250, 500, 50, 100);
		//虚拟键盘^_^注册按钮事件
			jb.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
				JFileChooser chs=new JFileChooser();chs.setCurrentDirectory(new File("."));
				if(chs.showOpenDialog(drw)==JFileChooser.APPROVE_OPTION) mapfile=chs.getSelectedFile();
				loadmap(mapfile.getAbsolutePath());
			}});
			jbup.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){run(1);}});
			jbdown.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){run(2);}});
			jbleft.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){run(3);}});
			jbright.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){run(4);}});
		//
		drw.add(jbright);drw.add(jbleft);drw.add(jbdown);drw.add(jbup);
	}
	private boolean chkwin(){	//检测是否取胜
		if(endp.equals(boxp)) return true;
		return false;
	}
	private Point driection(Point p,int driec){	//根据方向返回下一步的位置
		Point chkp=null;
		switch(driec){
		case 1:chkp = new Point(p.x,p.y-50);break;
		case 2:chkp = new Point(p.x,p.y+50);break;
		case 3:chkp = new Point(p.x-50,p.y);break;
		case 4:chkp = new Point(p.x+50,p.y);break;
		}
		return chkp;
	}
	private boolean canmove(Point p,int driec){	//判断是否撞墙
		Point chkp=driection(p,driec);
		for(int i=0;i!=map.length;i++){
			if(map[i].equals(chkp)) return false;
		}
		return true;
	}
	private boolean close(Point p1,Point p2,int driec){	//判断人是否可以推着箱子
		Point chkp=driection(p1,driec);
		if(chkp.equals(p2)) return true;
		return false;
	}
	public void run(int driec){			//根据方向行走，进行各种判断
		if(close(manp,boxp,driec)){			//推着箱子走
			if(canmove(boxp,driec)){
				drw.drawman(boxp);
				drw.drawbox(driection(boxp,driec));
				boxp=driection(boxp,driec);
				manp=driection(manp,driec);
			}
		}else{								//只有人自己走
			if(canmove(manp,driec)){
				manp=driection(manp,driec);
				drw.drawman(manp);
			}
		}
		if(chkwin()){						//判断是否取胜
			drw.message("恭喜您获胜！");
			endp=new Point(-1,-1);
		}
	}
}
