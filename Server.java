import java.io.*;
import java.net.*;
public class Server{

    ServerSocket server; //this is server
    Socket socket; 

    //to read and write data
    BufferedReader br;
    PrintWriter out;


    //constructor
    public Server(){
        try{
            this.server = new ServerSocket(9999); //server created
            System.out.println("server is ready to accept  conncetion");
            System.out.println("waiting...");

            //know as we need to accept when connection request will come
            socket=server.accept();

            //for reading the string from socket
            br = new  BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            //start reading and writing
            startReading();
            startWriting();

        }catch(Exception e){
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
                        System.out.println("Client terminated chat");
                        socket.close();
                        break;
                    }

                    System.out.println("Client:"+msg);   
                   
                    
                } 
            }catch(Exception e){
               // e.printStackTrace();
               System.out.println("bye bhai connection closed");

            }  
        };
        new Thread(r1).start(); 
    }
    public void startWriting(){
       //thread will take the data and it will write to Client
       Runnable r2 = ()->{
        System.out.println("writer started");
        System.out.println("server is writing");

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
        
        }catch(Exception e ){
            e.printStackTrace();
        }
       };

       //creating thread
       new Thread(r2).start();
    } 

    public static void main(String[] args) {
        System.out.println("this is my server...i m starting it");
        new Server();  // this will create my object of constructor and execute all logic inside constructor
    }
}