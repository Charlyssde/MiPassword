/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author texch
 */
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import mipasswordinterface.Usuario;

/**
 * Clase encargada de la encriptacion de los datos
 * @author Carlos Carrillo
 */
public class AES {

  private static SecretKeySpec secretKey;
  private static byte[] key;

  private static PrivateKey PrivateKey;
  private static PublicKey PublicKey;

  private static Usuario user;
  
  private static byte [] privEncoded;
  private static byte [] pubEncoded;

  /**
   * Constructor de la clase
   * @param usuario usuario del cual se desean encriptar los datos
   */
  public AES(Usuario usuario) {
    this.user = usuario;
  }

  /**
   * metodo para establecer la llave
   */
  public static void setKey() {
    MessageDigest sha = null;
    try {
      key = user.getPassword().getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      secretKey = new SecretKeySpec(key, "AES");
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  /**
   * Metodo para encriptar através de la llave publica
   * @param strToEncrypt texto a encriptar
   * @return 
   */
  public static String encrypt(String strToEncrypt) {
    try {
      setKey();
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    } catch (Exception e) {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, e);
    }
    return null;
  }

  /**
   * metodo para desencriptar
   * @param strToDecrypt texto a desencriptar
   * @return 
   */
  public static String decrypt(String strToDecrypt) {
    try {
      setKey();
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
        Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, e);      }
    return null;
  }

  /*
    Comienza el cripting de RSA en el que se deben crear las llaves pub y priv con las que se encripta
    las contraseñas de las llaves
    
   */
  /**
   * metodo para generar las dos llaves 
   * @param size
   */
  public static void genKeyPair(int size) {

    try {
      KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
      kpg.initialize(size);
      KeyPair kp = kpg.genKeyPair();
      
      PublicKey publicKey = kp.getPublic();
      PrivateKey privateKey = kp.getPrivate();
      
      PrivateKey = privateKey;
      PublicKey = publicKey;
      
      privEncoded = PrivateKey.getEncoded();
      pubEncoded =  PublicKey.getEncoded();
      
      String prKEnc = new String(privEncoded);
      String prK = encrypt(prKEnc);
      
      String pubKEnc = new String(pubEncoded);
      String pubK = encrypt(pubKEnc);
      
      user.setClavePublica(pubK);
      user.setClavePrivada(prK);
    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * metodo para encriptar las contraseñas de lsa llaves
   * @param plain texto a encriptar
   * @return
   * @throws NoSuchAlgorithmException
   * @throws NoSuchPaddingException
   * @throws InvalidKeyException
   * @throws IllegalBlockSizeException
   * @throws BadPaddingException
   * @throws InvalidKeySpecException
   * @throws UnsupportedEncodingException
   * @throws NoSuchProviderException 
   */
  public String EncryptPassword(String plain) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, UnsupportedEncodingException, NoSuchProviderException {

    PublicKey pub = convertStringToPublic();
    byte[] encryptedBytes;

    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, pub);
    encryptedBytes = cipher.doFinal(plain.getBytes());

    return bytesToString(encryptedBytes);

  }

  /**
   * metodo para desencriptar las contraseñas
   * @param result texto a desencriptar
   * @return
   * @throws NoSuchAlgorithmException
   * @throws NoSuchPaddingException
   * @throws InvalidKeyException
   * @throws IllegalBlockSizeException
   * @throws BadPaddingException 
   */
  public String DecryptPassword(String result) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

    PrivateKey pv = convertStringToKey();
    byte[] decryptedBytes;

    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, pv);
    decryptedBytes = cipher.doFinal(stringToBytes(result));
    return new String(decryptedBytes);
  }

  public String bytesToString(byte[] b) {
    byte[] b2 = new byte[b.length + 1];
    b2[0] = 1;
    System.arraycopy(b, 0, b2, 1, b.length);
    return new BigInteger(b2).toString(36);
  }

  public byte[] stringToBytes(String s) {
    byte[] b2 = new BigInteger(s, 36).toByteArray();
    return Arrays.copyOfRange(b2, 1, b2.length);
  }

  private PrivateKey convertStringToKey() {
    PrivateKey priv = null; 
    try {
      byte[] publicBytes = privEncoded;
      EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(publicBytes);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      priv = (PrivateKey) keyFactory.generatePrivate(keySpec);
    } catch (NoSuchAlgorithmException |InvalidKeySpecException ex) {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return priv;
  }
  
  private PublicKey convertStringToPublic(){
    PublicKey pub = null;
    try { 
      byte[] publicKeyBytes = pubEncoded;
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
      pub = keyFactory.generatePublic(publicKeySpec);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex ) {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
    }
    return pub;
  }

  /*public static void main(String[] args) {
    try {

      Usuario u = new Usuario("User", "Carlos", "Carrillo", "2281568496", "2580asd", "jjuso@gmail");
      AES aes = new AES(u);
      aes.genKeyPair(512);
      String encripted = (user.getClavePrivada());
      System.out.println("Cripting: " + encripted);
      System.out.println("Decripting: \n" + aes.decrypt(encripted));

      String pass = aes.EncryptPassword("sadffasd");

      System.out.println("Password (sadffasd): \n" + pass);
      
      String passDec = aes.DecryptPassword(pass);
      
      System.out.println("Password decript: " + "\n" + passDec);

    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NoSuchPaddingException ex) {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvalidKeyException ex) {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalBlockSizeException ex) {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
    } catch (BadPaddingException ex) {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvalidKeySpecException ex) {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
    } catch (UnsupportedEncodingException ex) {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NoSuchProviderException ex) {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
    }

  }
*/

}
