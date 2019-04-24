package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;



public class RSA{
  
  private static Cipher rsa;

   public static void main(String[] args) {

   }
   
   public static void EjemploCript() throws Exception{
     // Generar el par de claves
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
      PublicKey publicKey = keyPair.getPublic();
      PrivateKey privateKey = keyPair.getPrivate();

      // Se salva y recupera de fichero la clave publica
      saveKey(publicKey, "publickey.dat");
      publicKey = loadPublicKey("publickey.dat");

      // Se salva y recupera de fichero la clave privada
      saveKey(privateKey, "privatekey.dat");
      privateKey = loadPrivateKey("privatekey.dat");

      // Obtener la clase para encriptar/desencriptar
      rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");

      // Texto a encriptar
      String text = "Nombre";

      // Se encripta
      rsa.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] encriptado = rsa.doFinal(text.getBytes());

      // Escribimos el encriptado para verlo, con caracteres visibles
      for (byte b : encriptado) {
         System.out.print(Integer.toHexString(0xFF & b));
      }
      System.out.println();

      // Se desencripta
      rsa.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] bytesDesencriptados = rsa.doFinal(encriptado);
      String textoDesencripado = new String(bytesDesencriptados);

      // Se escribe el texto desencriptado
      System.out.println(textoDesencripado);
      
      /*fiirst result
      3a57f859de15e85bb44b349abe93925965d8e8016662aeb524c1e82c32361e85f1519edfc48547922ef86f79645311a7190e17d52e3a99914fcddbbf3825d14edbd864f586689047bb4f51c8e43880a63fca4967188c1646cd0c69c78eea1f3f55497782b97ff8bde2b19635ed4320372ec932bc8f3e9b98bf4827b24986269911dcd9b72814f0b0d5d8bf478166a8c1db84117fbbd3655bd037cfa81e0c34c501fcdcbf1e51ac6eb37f6d394041124086713707793cec84d9fb9af867913cd443550428e43abca4e55bda4e67aa87d14cac7c897cd95859091d3e0891e59456ffc471802eb2b0962d11b12f9bf17f98271bdb31ed175372
Text to encrypt
      */
   }
   
   public static void encriptarDatosUsuario(Usuario usuario){
     
   }

   private static PublicKey loadPublicKey(String fileName) throws Exception {
      FileInputStream fis = new FileInputStream(fileName);
      int numBtyes = fis.available();
      byte[] bytes = new byte[numBtyes];
      fis.read(bytes);
      fis.close();

      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      KeySpec keySpec = new X509EncodedKeySpec(bytes);
      PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
      return keyFromBytes;
   }

   private static PrivateKey loadPrivateKey(String fileName) throws Exception {
      FileInputStream fis = new FileInputStream(fileName);
      int numBtyes = fis.available();
      byte[] bytes = new byte[numBtyes];
      fis.read(bytes);
      fis.close();

      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
      PrivateKey keyFromBytes = keyFactory.generatePrivate(keySpec);
      return keyFromBytes;
   }

   private static void saveKey(Key key, String fileName) throws Exception {
      byte[] publicKeyBytes = key.getEncoded();
      FileOutputStream fos = new FileOutputStream(fileName);
      fos.write(publicKeyBytes);
      fos.close();
   }
  
}
