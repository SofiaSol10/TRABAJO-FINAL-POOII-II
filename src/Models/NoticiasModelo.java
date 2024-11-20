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
public class NoticiasModelo {
     private List<String> titulos;
    private List<String> links;
    
    public NoticiasModelo() {
        titulos = new ArrayList<>(); // Inicializar la lista
        links = new ArrayList<>(); // Inicializar la lista
    }

    public List<String> getTitulos() {
        return titulos;
    }

    public void setTitulos(ArrayList<String> titulos) {
        this.titulos = titulos;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

   
    
    public void leerNoticia(){
        
        try{
            FileReader fr=new FileReader("./src/javafxapplication3/Noticias.txt");
            BufferedReader br = new BufferedReader(fr);
            String d;
            while ((d=br.readLine())!= null) {
                String[] partes = d.split("@", 2); 
                if (partes.length == 2) {
                    titulos.add(partes[0].trim());
                    links.add(partes[1].trim());
                }
            }
            
        }catch(Exception e){
            e.printStackTrace(); //falta especificar mas esta parte
        }
    }
}
