/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import mipasswordinterface.MPInterface;

/**
 *
 * @author texch
 */
public class Client extends UnicastRemoteObject {

  MPInterface server;
  String nombre = "GameServer";
  String serverName = "localhost";
  int serverPORT = 5254;

  public Client() throws RemoteException {
    super();
  }

  public void iniciarClient() {

    try {

      Registry registro = LocateRegistry.getRegistry(serverName, serverPORT);
      server = (MPInterface) registro.lookup(nombre);
      
      System.out.println("Conectado al servidor");
    } catch (Exception ex) {
      System.err.println(ex.getCause());
    }
  }

}
