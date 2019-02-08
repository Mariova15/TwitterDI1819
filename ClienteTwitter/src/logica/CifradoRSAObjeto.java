/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author alumnop
 */
public class CifradoRSAObjeto extends CifradoRsa{
    
    public <T extends Serializable> byte[] cifrarObjeto (T objeto) throws IOException, Excepciones.CifradoExcepcion{
        byte[] objetoArray = this.object2byteArray(objeto);
        return super.encriptar(objetoArray);
    }
    
    public Object descifrarObjeto (byte[] objCifrado) throws Excepciones.CifradoExcepcion, IOException, ClassNotFoundException{
        byte[] objetoDescifrado = super.desencriptar(objCifrado);
        return this.byteArray2object(objCifrado);
    }
    
    public <T extends Serializable> byte[]  object2byteArray ( T object) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        return baos.toByteArray();
    }
    
    public Object byteArray2object (byte[] buffer) throws IOException, ClassNotFoundException{
        ByteArrayInputStream baos = new ByteArrayInputStream(buffer);
        ObjectInputStream oos = new ObjectInputStream(baos);
        return oos.readObject();
    }
}
