import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
class Player{
	public final static int type_human=0;
	public final static int type_computer=1;
	public static int tac;
	private int color;//������
	private int type;//�l�Ԃ����삷��̂��R���s���[�^�����삷��̂�
	
	
	Player(int c,int t){
		if(c==Stone.black||c==Stone.white){
			color=c;
		}else{
			System.out.println("�v���C���[�̐΂͍������łȂ���΂����܂���:"+c);
			System.exit(0);
		}
		if(t==type_human||t==type_computer){
			type=t;
		}else{
			System.out.println("�v���C���[�͐l�Ԃ��R���s���[�^�łȂ���΂����܂���:"+t);
			System.exit(0);
		}
	}
	
	int getColor(){
		return color;
	}
	
	int getType(){
		return type;
	}
	
	void setTac(int tact){
		tac=tact;
	}
	static int getTac(){
		return tac;
	}
	
	Point tactics(Board bd){//�z�u�\�ȏꏊ��T��
		bd.evaluateBoard();///////////////////////////////
		if(tac==1){//�����_���ɒu���헪
			if(color==Stone.black){
				if(bd.num_grid_black!=0){
					while(true){
						Random random = new Random();
						int random1 = random.nextInt(8);
						int random2 = random.nextInt(8);
						if(0<bd.eval_black[random1][random2]){
								return (new Point(random1,random2));
						}
						
					}
				}else{
					return (new Point(-1,-1));
				}
			}else if(color==Stone.white){
				if(bd.num_grid_white!=0){
					while(true){
						Random random = new Random();
						int random1 = random.nextInt(8);
						int random2 = random.nextInt(8);
						if(0<bd.eval_white[random1][random2]){
								return (new Point(random1,random2));
						}
						
					}
				}else{
					return (new Point(-1,-1));
				}
			}
			
		}else if(tac==2){//�Ђ�����Ԃ���΂��ł������Ȃ�悤�u���헪
			int max=0;
			int maxi=-1;
			int maxj=-1;
			if(color==Stone.black){
				for(int i=0;i<8;i++){
					for(int j=0;j<8;j++){
						if(max<bd.eval_black[i][j]){
							max=bd.eval_black[i][j];
							maxi=i;
							maxj=j;
						}
					}
				}
				if(maxi==-1&&maxj==-1){
					return (new Point(-1,-1));
				}else{
					return (new Point(maxi,maxj));
				}
			}else if(color==Stone.white){
				for(int i=0;i<8;i++){
					for(int j=0;j<8;j++){
						if(max<bd.eval_white[i][j]){
							max=bd.eval_white[i][j];
							maxi=i;
							maxj=j;
						}
					}
				}
				if(maxi==-1&&maxj==-1){
					return (new Point(-1,-1));
				}else{
					return (new Point(maxi,maxj));
				}
			}
		}else if(tac==3){//�p��[��D��ɁA�Ђ�����Ԃ���΂��ł������Ȃ�悤�u���헪
			int max=0;
			int maxi=-1;
			int maxj=-1;
			if(color==Stone.black){
				if(0<bd.eval_black[0][0])return (new Point(0,0));
				if(0<bd.eval_black[0][7])return (new Point(0,7));
				if(0<bd.eval_black[7][0])return (new Point(7,0));
				if(0<bd.eval_black[7][7])return (new Point(7,7));
				for(int i1=1;i1<7;i1++){
					if(max<bd.eval_black[i1][0]){
						max=bd.eval_black[i1][0];
						maxi=i1;
						maxj=0;
					}
				}
				for(int i2=1;i2<7;i2++){
					if(max<bd.eval_black[0][i2]){
						max=bd.eval_black[0][i2];
						maxi=0;
						maxj=i2;
					}
				}
				for(int i3=1;i3<7;i3++){
					if(max<bd.eval_black[i3][7]){
						max=bd.eval_black[i3][7];
						maxi=i3;
						maxj=7;
					}
				}
				for(int i4=1;i4<7;i4++){
					if(max<bd.eval_black[7][i4]){
						max=bd.eval_black[7][i4];
						maxi=7;
						maxj=i4;
					}
				}
				if(!(maxi==-1&&maxj==-1)){
					return (new Point(maxi,maxj));
				}
				
				
				///////////////////////////////////////////
				
				
				
				for(int i=0;i<8;i++){
					for(int j=0;j<8;j++){
						if(max<bd.eval_black[i][j]){
							max=bd.eval_black[i][j];
							maxi=i;
							maxj=j;
						}
					}
				}
				if(maxi==-1&&maxj==-1){
					return (new Point(-1,-1));
				}else{
					return (new Point(maxi,maxj));
				}
				///////////////////////////////////////////
				///////////////////////////////////////////
			}else if(color==Stone.white){
				if(0<bd.eval_white[0][0])return (new Point(0,0));
				if(0<bd.eval_white[0][7])return (new Point(0,7));
				if(0<bd.eval_white[7][0])return (new Point(7,0));
				if(0<bd.eval_white[7][7])return (new Point(7,7));
				for(int i1=1;i1<7;i1++){
					if(max<bd.eval_white[i1][0]){
						max=bd.eval_white[i1][0];
						maxi=i1;
						maxj=0;
					}
				}
				for(int i2=1;i2<7;i2++){
					if(max<bd.eval_white[0][i2]){
						max=bd.eval_white[0][i2];
						maxi=0;
						maxj=i2;
					}
				}
				for(int i3=1;i3<7;i3++){
					if(max<bd.eval_white[i3][7]){
						max=bd.eval_white[i3][7];
						maxi=i3;
						maxj=7;
					}
				}
				for(int i4=1;i4<7;i4++){
					if(max<bd.eval_white[7][i4]){
						max=bd.eval_white[7][i4];
						maxi=7;
						maxj=i4;
					}
				}
				if(!(maxi==-1&&maxj==-1)){
					return (new Point(maxi,maxj));
				}
				
				
				
				///////////////////////////////////////////
				
				for(int i=0;i<8;i++){
					for(int j=0;j<8;j++){
						if(max<bd.eval_white[i][j]){
							max=bd.eval_white[i][j];
							maxi=i;
							maxj=j;
						}
					}
				}
				if(maxi==-1&&maxj==-1){
					return (new Point(-1,-1));
				}else{
					return (new Point(maxi,maxj));
				}
			}
			
		}else{
			//����D��ɒu���헪
			if(color==Stone.black){
				for(int i=0;i<8;i++){
					for(int j=0;j<8;j++){
						if(bd.eval_black[i][j]>0){
							return (new Point(i,j));
						}
					}
				}
			}else if(color==Stone.white){
				for(int i=0;i<8;i++){
					for(int j=0;j<8;j++){
						if(bd.eval_white[i][j]>0){
							return (new Point(i,j));
						}
					}
				}
			}
		}
		
		return (new Point(-1,-1));//�z�u�\�łȂ�
	}
	
	Point nextMove(Board bd,Point p){//���ɐ΂�u���}�X��
		if(type==type_human){
			return p;//�N���b�N�����Ƃ�
		}else if(type==type_computer){
			return tactics(bd);
		}
		return (new Point(-1,-1));//�ʏ�͗L�蓾�Ȃ�
	}
}


class Stone{
	public final static int black=1;
	public final static int white=2;
	public int obverse;//�\�ʂ̐F
	
	Stone(){
		obverse=0;
	}
	
	//�\�ʂ̐F��ݒ�
	void setObverse(int color){
		if(color==black||color==white){
			obverse=color;
		}else{
			System.out.println("�������łȂ���΂����܂���");
		}
	}
	
	//�\�ʂ̐F�ŁA���Sp�A���arad�̉~��h��
	void paint(Graphics g,Point p,int rad){
		if(obverse==black){
			g.setColor(Color.black);
			g.fillOval(p.x-rad,p.y-rad,2*rad,2*rad);
		}else if(obverse==white){
			g.setColor(Color.white);
			g.fillOval(p.x-rad,p.y-rad,2*rad,2*rad);
		}
	}
	
	
	int getObverse(){
		return obverse;
	}
	
}
class Board{
	private int width=800;
	private int height=800;
	private static Stone[][] board=new Stone[8][8];
	private static Point[][] p=new Point[8][8];
	
	public int num_grid_black;
	public int num_grid_white;
	private Point[] direction=new Point[8];
	public int[][] eval_black=new int[8][8];
	public int[][] eval_white=new int[8][8];
	
	
	
	//�R���X�g���N�^(������)
	Board(){
		//�΂�(����)�z�u
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				board[i][j]=new Stone();//�Y�ꂪ��
				p[i][j]=new Point();
				p[i][j].x=(width/10)*i+(width/20)*3;
				p[i][j].y=(height/10)*j+(height/20)*3;
				
				if(i==4&&j==4){
					board[i][j].setObverse(1);
				}else if(i==3&&j==3){
					board[i][j].setObverse(1);
				}else if(i==4&&j==3){
					board[i][j].setObverse(2);//
				}else if(i==3&&j==4){
					board[i][j].setObverse(2);//
				}
				
				
			}
		}
		
		
		//�����x�N�g���̐���
		direction[0]=new Point(1,0);
		direction[1]=new Point(1,1);
		direction[2]=new Point(0,1);
		direction[3]=new Point(-1,1);
		direction[4]=new Point(-1,0);
		direction[5]=new Point(-1,-1);
		direction[6]=new Point(0,-1);
		direction[7]=new Point(1,-1);
		
		
		
	}
	
	
	
	//��ʕ`��
	void paint(Graphics g,int unit_size){
		//�w�i
		g.setColor(Color.black);
		g.fillRect(0,0,width,height);
		
		//�Ֆ�
		g.setColor(new Color(0,85,0));
		g.fillRect((width/10),(height/10),(width/10)*8,(height/10)*8);
		
		//����
		g.setColor(Color.black);
		for(int i=0;i<9;i++){
			//(x1,y1,x2,y2)
			g.drawLine((width/10),(height/10)*i,(width/10)*9,(height/10)*i);
		}
		
		//�c��
		for(int i=0;i<9;i++){
			//(x1,y1,x2,y2)
			g.drawLine((width/10)*i,(height/10),(width/10)*i,(height/10)*9);
		}
		
		//�ڈ�
		for(int i=0;i<2;i++){
			for(int j=0;j<2;j++){
				g.fillRect((width/10)*3+(i*(width/10)*4)-5,(height/10)*3+(j*(width/10)*4)-5,10,10);
			}
		}
		
		
		//��
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				board[i][j].paint(g,p[i][j],(unit_size*2)/5);
			}
		}
		
		
	}
	
	
	boolean isOnBoard(int x,int y){
		if(x<0||7<x||y<0||7<y){
			return false;
		}else{
			return true;
		}
	}
	
	//�Ֆ�(x,y)�������d�Ɍ������Đ΂����Ɏ擾
	ArrayList<Integer> getLine(int x,int y,Point d){
		ArrayList<Integer> line=new ArrayList<Integer>();
		int cx=x+d.x;
		int cy=y+d.y;
		while(isOnBoard(cx,cy)&&board[cx][cy].getObverse()!=0){
			line.add(board[cx][cy].getObverse());
			cx+=d.x;
			cy+=d.y;
		}
		return line;
	}
	
	//�Ֆ�(x,y)�ɐ�s��u�����ꍇ�ɔ��]�ł���΂̐��𐔂���
	int countReverseStone(int x,int y,int s){
		//���ɐ΂��u����Ă�����u���Ȃ�
		if(board[x][y].getObverse()!=0){
			return -1;
		}
		//8�������`�F�b�N
		int cnt=0;
		for(int d=0;d<8;d++){
			ArrayList<Integer> line=new ArrayList<Integer>();
			line=getLine(x,y,direction[d]);
			int n=0;
			while(n<line.size()&&line.get(n)!=s){
				n++;
			}
			if(1<=n&&n<line.size()){
				cnt+=n;
			}
			
		}
		
		return cnt;
	}
	
	
	//�}�X��(x,y)�ɐ�s��z�u
	void setStone(int x,int y,int s){
		board[x][y].setObverse(s);
		
	}
	
	//�}�X��(x,y)�ɐ�s��z�u�A���̐΂𔽓]
	void setStoneAndReverse(int x,int y,int s){
		board[x][y].setObverse(s);
		for(int d=0;d<8;d++){
			ArrayList<Integer> line=new ArrayList<Integer>();
			line=getLine(x,y,direction[d]);
			
			
			
			int n=0;
			while(n<line.size()&&line.get(n)!=s)n++;
			if(1<=n&&n<line.size()){
				int cx=x+direction[d].x;
				int cy=y+direction[d].y;
				
				while(isOnBoard(cx,cy)&&board[cx][cy].getObverse()!=s){
					
				
				
					if(board[cx][cy].getObverse()==1){
						board[cx][cy].setObverse(2);
						setStone(cx,cy,2);
					}else if(board[cx][cy].getObverse()==2){
						board[cx][cy].setObverse(1);
						setStone(cx,cy,1);
					}
					
					cx+=direction[d].x;
					cy+=direction[d].y;
				}
				
			}
			
			
		}
		
	}
	
	
	//�Ֆʂ�]��
	void evaluateBoard(){
		num_grid_black=0;
		num_grid_white=0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				eval_black[i][j]=countReverseStone(i,j,1);
				if(eval_black[i][j]>0){
					num_grid_black+=1;
				}
				eval_white[i][j]=countReverseStone(i,j,2);
				if(eval_white[i][j]>0){
					num_grid_white+=1;
				}
			}
		}
		
	}
	
	
	//�Ֆʂ��R���\�[���ɕ\������
	void printBoard(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				System.out.printf("%2d ",board[i][j].getObverse());
			}
			System.out.println("");
		}
	}
	
	//�Ֆʂ̕]�����ʂ��R���\�[���ɕ\������
	void printEval(){
		System.out.println("Black(1):");
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				System.out.printf("%2d ",eval_black[i][j]);
			}
			System.out.println("");
		}
		
		System.out.println("White(2):");
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				System.out.printf("%2d ",eval_white[i][j]);
			}
			System.out.println("");
		}
	}
	
	
	
	//�Ֆʏ�̐�s�̐�
	int countStone(int s){
		int count=0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(s==board[i][j].obverse){
					count+=1;
				}
			}
		}
		return count;
	}
	
	
}
public class Reversi extends JPanel{
	private int width=800;
	private int height=800;
	private int turn;
	public final static int UNIT_SIZE=80;
	public static int tac;
	private Board board=new Board();
	private Player[] player=new Player[2];
	
	
	
	//�R���X�g���N�^(������)
	public Reversi(){
		//�p�l���̃T�C�Y
		setPreferredSize(new Dimension(width,height));
		addMouseListener(new MouseProc());
		
		//����type_��ύX���邱�Ƃőΐl��A�R���s���[�^�킪�ł���
		player[0]=new Player(Stone.black,Player.type_human);
		player[1]=new Player(Stone.white,Player.type_computer);
		turn=Stone.black;
		
		
	}
	
	//��ʕ`��
	public void paintComponent(Graphics g){
		String msg1="";
		board.paint(g,UNIT_SIZE);//Board�N���X��paint���\�b�h���Ăяo��
		g.setColor(Color.white);
		if(turn==Stone.black){
			msg1="���̔Ԃł�";
		}else{
			msg1="���̔Ԃł�";
		}
		if(player[turn-1].getType()==Player.type_computer){
			msg1+="�l���Ă��܂�";
		}
		
		String msg2="[��:"+board.countStone(Stone.black)+",��"+board.countStone(Stone.white)+"]";
		g.drawString(msg1,UNIT_SIZE/2,UNIT_SIZE/2);
		g.drawString(msg2,UNIT_SIZE/2,UNIT_SIZE*19/2);
		
		
	}
	
	
	
	//�N��
	public static void main(String[] args){
		JFrame f=new JFrame();
		f.getContentPane().setLayout(new FlowLayout());
		f.getContentPane().add(new Reversi());
		f.pack();
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		Player.tac=Integer.parseInt(args[0]);
		
		
		

	}
	
		
	
	
	void changeTurn(){
		if(turn==Stone.black){
			turn=Stone.white;
			
		}else{
			turn=Stone.black;
		}
		
	}
	
	void MessageDialog(String str){
		JOptionPane.showMessageDialog(this,str,"���",JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	
	
	//�I�����b�Z�[�W�_�C�A���O
	void EndMessageDialog(int kuro,int shiro){
		String str1="[��:";
		String str2;
		String str3;
		String str4;
		String str5;
		if(kuro>shiro){
			str2=String.valueOf(kuro);
			str3=",��:";
			str4=String.valueOf(shiro);
			str5="]�ō��̏���";
		}else if(kuro==shiro){
			str2=String.valueOf(kuro);
			str3=",��:";
			str4=String.valueOf(shiro);
			str5="]�ň�������";
		}else{
			str2=String.valueOf(kuro);
			str3=",��:";
			str4=String.valueOf(shiro);
			str5="]�Ŕ��̏���";
		}
		String str=str1.concat(str2);
		str=str.concat(str3);
		str=str.concat(str4);
		str=str.concat(str5);
		
		
		JOptionPane.showMessageDialog(this,str,"�Q�[���I��",JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}
	
	
	
	
	//�N���b�N���ꂽ�Ƃ��̏���
	class MouseProc extends MouseAdapter{
		public void mouseClicked(MouseEvent me){
			Point point=me.getPoint();//�N���b�N���ꂽ���W���擾
			int btn=me.getButton();//�N���b�N���ꂽ�{�^���̎�ނ��擾
			Point gp=new Point();
			gp.x=point.x/UNIT_SIZE-1;
			gp.y=point.y/UNIT_SIZE-1;
			
			
			if(!board.isOnBoard(gp.x,gp.y)){
				return;
			}
			removeMouseListener(this);//�ꎞ�I�Ƀ}�E�X�C�x���g�̏������~�߂�
			
			board.evaluateBoard();
			//�l�Ԃ̎��
			if(player[turn-1].getType()==Player.type_human){
				if((player[turn-1].getColor()==Stone.black&&board.num_grid_black==0)||
					(player[turn-1].getColor()==Stone.white&&board.num_grid_white==0)){
					MessageDialog("���Ȃ��̓p�X�ł�");
					changeTurn();
					repaint();
				}else if((player[turn-1].getColor()==Stone.black&&board.eval_black[gp.x][gp.y]>0)||
					(player[turn-1].getColor()==Stone.white&&board.eval_white[gp.x][gp.y]>0)){
					Point nm=player[turn-1].nextMove(board,gp);
					//board.board[gp.x][gp.y]=player[turn-1].getColor();
						
						//board.evaluateBoard();
						
					board.setStoneAndReverse(nm.x,nm.y,player[turn-1].getColor());
					changeTurn();
					repaint();
					//�Q�[���I���m�F
						if(board.num_grid_black==0&&board.num_grid_white==0){
							EndMessageDialog(board.countStone(1),board.countStone(2));
						
						}
				}
				if(player[turn-1].getType()==Player.type_human){
					addMouseListener(this);
				}
			}
			
			
			//�R���s���[�^�̎��
			if(player[turn-1].getType()==Player.type_computer){
				Thread th=new TacticsThread();
				th.start();
			}
		}
			
			
		
		//�R���s���[�^�Ƃ̑ΐ�p�X���b�h
		class TacticsThread extends Thread{
			public void run(){
				try{
					Thread.sleep(2000);//2�b�҂�
					Point nm=player[turn-1].nextMove(board,new Point(-1,-1));
					if(nm.x==-1&&nm.y==-1){
						MessageDialog("����̓p�X�ł�");
					}else{
						board.setStoneAndReverse(nm.x,nm.y,player[turn-1].getColor());
					}
					changeTurn();
					repaint();
					addMouseListener(new MouseProc());
					
					
					
					//�Q�[���I���m�F
					if(board.num_grid_black==0&&board.num_grid_white==0){
							EndMessageDialog(board.countStone(1),board.countStone(2));
						
					}
				}catch(InterruptedException ie){
					ie.printStackTrace();
				}
			}
		
			
			
		}
	}
	
}