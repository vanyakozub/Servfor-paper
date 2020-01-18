import java.io.*;
import java.net.*;
import java.util.*;


public class ClientThread extends Thread {
    private Socket socket;
    public Users u;


    ClientThread(Socket s, Users user){
        socket = s;
        u = user;
    }

    public void run() {
        try {
            act();
        }
        catch (Throwable t) {

        }

    }

    public void act() throws Exception {
        PrintWriter[] outs;
        HashMap<String, Socket> users;
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine, outputLine;
        outputLine = Integer.toString(u.getUsers().size()-1);
        out.println(outputLine);


        while ((inputLine = in.readLine()) != null) {
            users = u.getUsers();
            outs = new PrintWriter[users.size()];
            Iterator it = users.entrySet().iterator();
            outputLine = inputLine;
            int mode = 0;


            if(mode == 0) {
                for(int i = 0; it.hasNext()&& i < users.size(); i++) {
                    Map.Entry mapElement = (Map.Entry)it.next();
                    Socket tmp = (Socket) mapElement.getValue();
                    if(tmp.equals(socket)) {
                        continue;
                    }
                    outs[i] = new PrintWriter(tmp.getOutputStream(), true);
                    outs[i].println(outputLine);

                }
            }

            System.out.println(u.getName(socket) + ": " + outputLine);


            if (outputLine.equals("Bye."))
                break;
        }

    }



}
