package metrofreak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

public class RuntimeExec {

    public StreamWrapper getStreamWrapper(InputStream is, String type){
            return new StreamWrapper(is, type);
    }
    private class StreamWrapper extends Thread {
        InputStream is = null;
        String type = null;          
        String message = null;

    public String getMessage() {
            return message;
    }

    StreamWrapper(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ( (line = br.readLine()) != null) {
                buffer.append(line);//.append("\n");
            }
            message = buffer.toString();
        } catch (IOException ioe) {
            ioe.printStackTrace();  
        }
    }
}
 
// this is where the action is
//    public static void main(String[] args)
    public static void getMyMessage(MessageElements xMe) {
                Runtime rt = Runtime.getRuntime();
                RuntimeExec rte = new RuntimeExec();
                StreamWrapper error, output;

                try {
                            Process proc = rt.exec("netsh wlan show interfaces");
                            error = rte.getStreamWrapper(proc.getErrorStream(), "ERROR");
                            output = rte.getStreamWrapper(proc.getInputStream(), "OUTPUT");
                            int exitVal = 0;

                            error.start();
                            output.start();
                            error.join(3000);
                            output.join(3000);
                            exitVal = proc.waitFor(); 
                            String[] arrOfMessage = output.message.split("\\s+", 0);
                            String description = "" + arrOfMessage[12] + " " + arrOfMessage[13] + " "
                                    + arrOfMessage[14] + " " + arrOfMessage[15] + " " + arrOfMessage[16] 
                                    + " "  + arrOfMessage[17];
                            
                            MessageElements me = new MessageElements(arrOfMessage[9], description, 
                                    arrOfMessage[20], arrOfMessage[24], arrOfMessage[27], arrOfMessage[30], 
                                    arrOfMessage[33], arrOfMessage[37], arrOfMessage[41], arrOfMessage[44], 
                                    arrOfMessage[47], arrOfMessage[55], arrOfMessage[60], 
                                    arrOfMessage[65], arrOfMessage[68], arrOfMessage[71]);
                            
                            xMe.setName(me.getName());
                            xMe.setDescription(me.getDescription());
                            xMe.setGuid(me.getGuid());
                            xMe.setPhysicalAddress(me.getPhysicalAddress());
                            xMe.setState(me.getState());
                            xMe.setSsid(me.getSsid());
                            xMe.setBssid(me.getBssid());
                            xMe.setNetworkType(me.getNetworkType());
                            xMe.setRadioType(me.getRadioType());
                            xMe.setAuthentification(me.getAuthentification());
                            xMe.setCipher(me.getCipher());
                            xMe.setChannel(me.getChannel());
                            xMe.setReceiveRateMbps(me.getReceiveRateMbps());
                            xMe.setTransmitRateMbps(me.getTransmitRateMbps());
                            xMe.setSignal(me.getSignal());
                            xMe.setProfile(me.getProfile());

                } catch (IOException e) {
                            e.printStackTrace();
                } catch (InterruptedException e) {
                            e.printStackTrace();
            }
        }
    }
