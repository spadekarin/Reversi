import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
import java.io.*;
class Stone{
	public final static int black=1;
	public final static int white=2;
	public int obverse;//表面の色
	
	Stone(){
		obverse=0;
	}
	
	//表面の色を設定
	void setObverse(int color){
		if(color==black||color==white){
			obverse=color;
		}else{
			System.out.println("黒か白でなければいけません");
		}
	}
	
	//表面の色で、中心p、半径radの円を塗る
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
	
	
	
	//コンストラクタ(初期化)
	Board(){
		//石の(初期)配置
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				board[i][j]=new Stone();//忘れがち
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
		
		
		//方向ベクトルの生成
		direction[0]=new Point(1,0);
		direction[1]=new Point(1,1);
		direction[2]=new Point(0,1);
		direction[3]=new Point(-1,1);
		direction[4]=new Point(-1,0);
		direction[5]=new Point(-1,-1);
		direction[6]=new Point(0,-1);
		direction[7]=new Point(1,-1);
		
		
		
	}
	
	
	
	//場面描画
	void paint(Graphics g,int unit_size){
		//背景
		g.setColor(Color.black);
		g.fillRect(0,0,width,height);
		
		//盤面
		g.setColor(new Color(0,85,0));
		g.fillRect((width/10),(height/10),(width/10)*8,(height/10)*8);
		
		//横線
		g.setColor(Color.black);
		for(int i=0;i<9;i++){
			//(x1,y1,x2,y2)
			g.drawLine((width/10),(height/10)*i,(width/10)*9,(height/10)*i);
		}
		
		//縦線
		for(int i=0;i<9;i++){
			//(x1,y1,x2,y2)
			g.drawLine((width/10)*i,(height/10),(width/10)*i,(height/10)*9);
		}
		
		//目印
		for(int i=0;i<2;i++){
			for(int j=0;j<2;j++){
				g.fillRect((width/10)*3+(i*(width/10)*4)-5,(height/10)*3+(j*(width/10)*4)-5,10,10);
			}
		}
		
		
		//石
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
	
	//盤面(x,y)から方向dに向かって石を順に取得
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
	
	//盤面(x,y)に石sを置いた場合に反転できる石の数を数える
	int countReverseStone(int x,int y,int s){
		//既に石が置かれていたら置けない
		if(board[x][y].getObverse()!=0){
			return -1;
		}
		//8方向をチェック
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
	
	
	//マス目(x,y)に石sを配置
	void setStone(int x,int y,int s){
		board[x][y].setObverse(s);
		
	}
	
	//マス目(x,y)に石sを配置、他の石を反転
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
	
	
	//盤面を評価
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
	
	
	//盤面をコンソールに表示する
	void printBoard(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				System.out.printf("%2d ",board[i][j].getObverse());
			}
			System.out.println("");
		}
	}
	
	//盤面の評価結果をコンソールに表示する
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
	
	
	
	//盤面上の石sの数
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

public class ReversiClient extends JPanel{
    public final static int UNIT_SIZE=80;
    public final static String LHOST="localhost";//接続先サーバのホスト名
    public final static int PORT=51001;
    private Board board=new Board();
    private int turn;//1:Stone.black⇔2:Stone.white
    private int myColor;
    private PrintWriter pw;//出力用のライター

    //コンストラクタ（初期化）
    public ReversiClient(){
	this(LHOST);
    }

    //画面描画
    public void paintComponent(Graphics g){
	String msg1="";
	board.paint(g,UNIT_SIZE);
	g.setColor(Color.white);
	if(turn==Stone.black)
	    msg1="黒の番です";
	else
	    msg1="白の番です";
	if(turn==myColor)
	    msg1+="(あなたの番です)";
	else
	    msg1+="(相手の番です)";
	if(myColor==0)
	    msg1="対戦相手を待っています...";
	String msg2="[黒:"+board.countStone(Stone.black)+
	    ",白"+board.countStone(Stone.white)+"]";
	g.drawString(msg1,UNIT_SIZE/2,UNIT_SIZE/2);
	g.drawString(msg2,UNIT_SIZE/2,19*UNIT_SIZE/2);
    }

    //起動
    public static void main(String[] args){
	JFrame f=new JFrame();
	f.getContentPane().setLayout(new FlowLayout());
	if(args.length>1)
	    f.getContentPane().add(new ReversiClient(args[0]));
	else
	    f.getContentPane().add(new ReversiClient());
	f.pack();
	f.setResizable(false);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setVisible(true);
    }

    //終了メッセージダイアログの表示
    void EndMessageDialog(){
	int black=board.countStone(Stone.black);
	int white=board.countStone(Stone.white);
	String str="[黒:"+black+",白"+white+"]で";
	if(black>white)
	    str+="黒の勝ち";
	else if(black<white)
	    str+="白の勝ち";
	else
	    str+="引き分け";
	JOptionPane.showMessageDialog(this,str,"ゲーム終了",
				      JOptionPane.INFORMATION_MESSAGE);
	System.exit(0);
    }

    //メッセージダイアログの表示
    void MessageDialog(String str){
	JOptionPane.showMessageDialog(this,str,"情報",
				      JOptionPane.INFORMATION_MESSAGE);
    }

    //手番を交替
    void changeTurn(){
	if(turn==Stone.black)turn=Stone.white;
	else if(turn==Stone.white)turn=Stone.black;
    }

    //サーバへの接続
    public ReversiClient(String host){
	setPreferredSize(new Dimension(10*UNIT_SIZE,10*UNIT_SIZE));
	addMouseListener(new MouseProc());
	turn=1;

	Socket socket=null;
	try{
	    socket=new Socket(host,PORT);
	}catch(UnknownHostException e){
	    System.err.println("ホストのIPアドレスが判定できません: "+e);
	}catch(IOException e){
	    System.err.println("エラーが発生しました: "+e);
	    System.exit(0);
	}

	MesgRecvThread mrt=new MesgRecvThread(socket);
	mrt.start();
    }

    //メッセージ受信のためのスレッド
    public class MesgRecvThread extends Thread
	{
	    Socket socket;

	    public MesgRecvThread(Socket s){
		socket=s;
	    }

	    //通信状況を監視し，受信データによって動作する
	    public void run(){
		try{
		    InputStreamReader isr=new InputStreamReader(socket.getInputStream());
		    BufferedReader br=new BufferedReader(isr);
		    pw=new PrintWriter(socket.getOutputStream(),true);
		    while(true){
			String inputLine=br.readLine();
			if(inputLine!=null){
			    System.out.println(inputLine);//デバッグ用
			    String[] inputTokens=inputLine.split(" ");
			    String cmd=inputTokens[0];
			    if(cmd.equals("set")){
				int x=Integer.parseInt(inputTokens[1]);
				int y=Integer.parseInt(inputTokens[2]);
				int c=Integer.parseInt(inputTokens[3]);
				board.setStoneAndReverse(x,y,c);
				changeTurn();
				repaint();
				//ゲーム終了確認
				if(board.num_grid_black==0&&board.num_grid_white==0){
				    String action_msg="gameover "+myColor;
				    pw.println(action_msg);
				    pw.flush();
				}
			    }
			    else if(cmd.equals("skip")){
				int player=Integer.parseInt(inputTokens[1]);
				if(player==myColor)
				    MessageDialog("あなたはパスです");
				else
				    MessageDialog("相手はパスです");
				changeTurn();
				repaint();
			    }
			    else if(cmd.equals("msg")){
				MessageDialog(inputTokens[1]);
			    }
			    else if(cmd.equals("color")){
				myColor=Integer.parseInt(inputTokens[1]);
			    }
			    else if(cmd.equals("gamestart")){
				MessageDialog("ゲームを開始します");
				repaint();
			    }
			    else if(cmd.equals("gameover")){
				EndMessageDialog();
			    }
			}
			else{
			    break;
			}
		    }
		    socket.close();
		}catch(IOException e){
		    System.err.println("エラーが発生しました: "+e);
		    System.exit(0);
		}
	    }
	}


    //クリックされた時の処理用のクラス
    class MouseProc extends MouseAdapter{
	public void mouseClicked(MouseEvent me){
	    if(myColor==0){
		String action_msg="msg 相手が来るまでお待ち下さい "+myColor;
		pw.println(action_msg);
		pw.flush();
	    }
	    if(turn!=myColor)return;//自分のターンでなければ無視
	    Point point=me.getPoint();
	    int btn=me.getButton();
	    int x=point.x/UNIT_SIZE-1;
	    int y=point.y/UNIT_SIZE-1;
	    if(!board.isOnBoard(x,y))return;//盤面の外
	    //パスの確認
	    if(myColor==Stone.black&&board.num_grid_black==0||
	       myColor==Stone.white&&board.num_grid_white==0){
		String action_msg="skip "+myColor;
		pw.println(action_msg);
		pw.flush();
	    }
	    //プレイヤーの手番
	    else if((myColor==Stone.black&&board.eval_black[x][y]>0)||
		   (myColor==Stone.white&&board.eval_white[x][y]>0)){

		String action_msg="set "+x+" "+y+" "+myColor;
		pw.println(action_msg);
		pw.flush();
	    }
	}
    }
}
