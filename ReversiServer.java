import java.util.*;
import java.io.*;
import java.net.*;

public class ReversiServer{
    public static final int PORT=51001;//利用するポート番号
    public static int num_player=0;//現在の接続数
    private static int n=0;//累計接続数
    private static Socket[] sk=new Socket[2];//ソケット
    private static InputStreamReader[] isr=new InputStreamReader[2];//入力ストリーム
    private static BufferedReader[] br=new BufferedReader[2];//バッファリーダー
    private static PrintWriter[] pw=new PrintWriter[2];//出力ストリーム
    private static ClientProc[] client=new ClientProc[2];//スレッド
    
    //全てのクライアントにメッセージを送信
    public static void broadcast(String str){
	for(int i=0;i<n;i++){
	    pw[i].println(str);
	    pw[i].flush();
	}
    }

    //特定のクライアントにメッセージを送信
    public static void sendMessage(int n,String str){
	pw[n].println(str);
	pw[n].flush();
    }

    public static void main(String[] args){
	try{
	    ServerSocket ss=new ServerSocket(PORT);
	    System.out.println("クライアントの接続を待っています...");
	    while(true){
		if(n<2){
		    try{
			sk[n]=ss.accept();
			System.out.println("クライアントの接続要求がありました:#"+n);
			isr[n]=new InputStreamReader(sk[n].getInputStream());
			br[n]=new BufferedReader(isr[n]);
			pw[n]=new PrintWriter(sk[n].getOutputStream(),true);
			client[n]=new ClientProc(n,sk[n],isr[n],br[n],pw[n]);
			client[n].start();//スレッドの開始
			n++;
			num_player++;
		    }catch(Exception e){
			e.printStackTrace();
		    }
		}
		else{
		    System.out.println("定員に達しました");
		    sendMessage(0,"color1");//先に接続したクライアントは黒
		    sendMessage(1,"color2");//後に接続したクライアントは白
		    broadcast("gamestart");
		    break;
		}
		if(num_player<n){
		    System.out.println("切断されたクライアントがあります");
		    System.exit(0);
		}
	    }
	    ss.close();//サーバソケットを閉じる（ゲーム開始後は接続要求を受け付けない）
	}catch(Exception e){
	    System.out.println("ソケットを作成できませんでした");
	}
    }
}


//各クライアントに対応するスレッド
class ClientProc extends Thread{
    private int num;
    private Socket sk;
    private InputStreamReader isr;
    private BufferedReader br;
    private PrintWriter pw;

    public ClientProc(int n,Socket s,InputStreamReader i,BufferedReader b,PrintWriter p){
	num=n;
	sk=s;
	isr=i;
	br=b;
	pw=p;
    }

    public void run(){
	try{
	    while(true){//無限ループでソケットへの入力を監視する
		String str=br.readLine();
		if(str!=null){
		    ReversiServer.broadcast(str);
		}
	    }
	}catch(Exception e){
	    System.out.println("切断します:#"+num);
	    ReversiServer.num_player-=1;
	    ReversiServer.broadcast("msg 対戦相手が逃げました");
	}
    }
}
