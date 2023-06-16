
import java.io.*;
import java.net.Socket;

public class Client {
        Socket socket ;

        BufferedReader br;
        PrintWriter out;


    public Client(){
        try {
            System.out.println("sending request to srvr");
            socket = new Socket("127.0.0.1",9999);
            System.out.println("connection established");

            br = new  BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            //start reading and writing
            startReading();
            startWriting();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    // know as we want to read the data along with writing so here we will use multithreading
    public void startReading(){
        //thread will help to read
        Runnable r1 = ()->{
            System.out.println("reader started ..");
            try{

                while(true){
                
                    String msg ;
                    
                        msg = this.br.readLine();

                        if(msg.equals("bye")){
                        System.out.println("Server terminated chat");
                        socket.close();
                        break;
                    }

                    System.out.println("Server:"+msg);   

                }
            }catch(Exception e){
                //e.printStackTrace();
                System.out.println("Tata bye bye connection closed :)");
            }   
        };
        new Thread(r1).start(); 
    }
    public void startWriting(){
       //thread will take the data and it will write to server
       Runnable r2 = ()->{
        System.out.println("writer started");
        System.out.println("Client is writing");
           try{
            while(!socket.isClosed()){
            
                
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in)); 
                String content= br1.readLine();
                out.println(content);
                out.flush();

                if(content.equals("bye")){
                    socket.close();
                    break;
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
       };

       //creating thread
       new Thread(r2).start();
    } 
    public static void main(String[] args) {
        System.out.println("this is my client");

        new Client();
    }
}
