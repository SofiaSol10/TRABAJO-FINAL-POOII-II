/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sofía
 */
public class DistritosModelo {
    private List<String>  IDS;
    private List<String>  nombresDistritos;

    public DistritosModelo() {
        IDS = new ArrayList<>();
        nombresDistritos = new ArrayList<>();
    }

    public List<String> getIDS() {
        return IDS;
    }

    public void setIDS(List<String> IDS) {
        this.IDS = IDS;
    }

    public List<String> getNombresDistritos() {
        return nombresDistritos;
    }

    public void setNombresDistritos(List<String> nombresDistritos) {
        this.nombresDistritos = nombresDistritos;
    }
            
    public void leerDistritos(){
        try{
            FileReader fr=new FileReader("./src/javafxapplication3/Distritos.txt");
            BufferedReader br = new BufferedReader(fr);
            String d;
            while ((d=br.readLine())!= null) {
                String[] partes = d.split(",", 2); 
                if (partes.length == 2) {
                    IDS.add(partes[0].trim());//creando lista con los ids 
                    nombresDistritos.add(partes[1].trim()); //creando lista con los nombres de los distritos
                }
            }
            
        }catch(Exception e){
            e.printStackTrace(); //falta especificar mas esta parte
        }
    }
}
