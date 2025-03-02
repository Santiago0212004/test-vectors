package DESTest.extendido;

import java.io.File;
import java.security.spec.*;
import java.util.Scanner;

import javax.crypto.*;
import javax.crypto.spec.*;

class DESTest {
   public static void main(String[] args) {
      try {
         int testVectorsAmount = 4;

         for(int i = 1; i<=testVectorsAmount; i++){
            File testVectorFile = new File("test-vector-"+Integer.toString(i)+".txt");
            Scanner reader = new Scanner(testVectorFile);
            System.out.println("\n---------------------Test Vector Number "+Integer.toString(i)+"------------------------------\n");
            int counter = 0;
            int success = 0;
            while (reader.hasNextLine()) {
               String data = reader.nextLine();
               byte[] theKey = null;
               byte[] theMsg = null; 
               byte[] theExp = null; 

               String[] dataSplitted = data.split(" ");
               theKey = hexToBytes(dataSplitted[0]);
               theMsg = hexToBytes(dataSplitted[1]);
               theExp = hexToBytes(dataSplitted[2]);

               KeySpec ks = new DESKeySpec(theKey);
               SecretKeyFactory kf 
                  = SecretKeyFactory.getInstance("DES");
               SecretKey ky = kf.generateSecret(ks);
               Cipher cf = Cipher.getInstance("DES/ECB/NoPadding");
               cf.init(Cipher.ENCRYPT_MODE,ky);
               byte[] theCph = cf.doFinal(theMsg);

               counter++;
               if(bytesToHex(theCph).equals(bytesToHex(theExp))){
                  success++;
               }

               System.out.println("Key     : "+bytesToHex(theKey));
               System.out.println("Message : "+bytesToHex(theMsg));
               System.out.println("Cipher  : "+bytesToHex(theCph));
               System.out.println("Expected: "+bytesToHex(theExp));
               System.out.println();
            }
            System.out.println("Success rate: "+ Double.toString((success/counter)*100)+"%");
            reader.close();
         }
      } catch (Exception e) {
         e.printStackTrace();
         return;
      }
   }
   public static byte[] hexToBytes(String str) {
      if (str==null) {
         return null;
      } else if (str.length() < 2) {
         return null;
      } else {
         int len = str.length() / 2;
         byte[] buffer = new byte[len];
         for (int i=0; i<len; i++) {
             buffer[i] = (byte) Integer.parseInt(
                str.substring(i*2,i*2+2),16);
         }
         return buffer;
      }

   }
   public static String bytesToHex(byte[] data) {
      if (data==null) {
         return null;
      } else {
         int len = data.length;
         String str = "";
         for (int i=0; i<len; i++) {
            if ((data[i]&0xFF)<16) str = str + "0" 
               + java.lang.Integer.toHexString(data[i]&0xFF);
            else str = str
               + java.lang.Integer.toHexString(data[i]&0xFF);
         }
         return str.toUpperCase();
      }
   }            
}