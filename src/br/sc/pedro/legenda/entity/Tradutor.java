/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sc.pedro.legenda.entity;

import br.sc.pedro.legenda.entity.Proxy;
import br.sc.pedro.legenda.view.ProgressBar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author pedro.soares
 */
public class Tradutor implements Runnable {

    private File arquivo;
    private Proxy p;

    List<String> legenda = new ArrayList<String>();
    List<String> falas = new ArrayList<String>();

    public Tradutor(File arquivo) {
       this.arquivo = arquivo;
    }

    public Tradutor(File legenda, Proxy p) {
        this.arquivo = legenda;
        this.p = p;
    }

    private void open(File arquivo){
        try{
        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        String text = null;

        int i = 0;
        while ((text = br.readLine()) != null) {
            legenda.add(text);
            i++;
        }
        }catch(Exception e){
            ProgressBar.getInstance().logOut(e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao traduzir");
            System.exit(1);
        }
    }

    private void getFalas() {
        /*String temp = "";
        for (String string : legenda) {
            if (string.equals("")) {
                falas.add(temp);
            }

            if (!string.equals("") && legenda.get(legenda.size() - 1) == string) {
                falas.add(legenda.get(legenda.size() - 1));
            }

            temp = string;
        }*/
        try{
            int i = 0;
            while ( i < legenda.size()) {
                if(NumberUtils.isNumber(legenda.get(i))){
                    i += 2;
                    String frase = legenda.get(i)+" | ";
                    i++;
                    while(!legenda.get(i).equals("")){
                        frase += legenda.get(i)+" | ";
                        i++;
                    }
                    falas.add(frase);
                }else{
                    i++;
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        //progresso.setMaximum(falas.size());
        ProgressBar.getInstance().setMax(falas.size());
    }

    private void traduzir() throws Exception {
        for (int i = 0; i < falas.size(); i++) {

            String log = "";
            String string = falas.get(i);

           log = "traduzindo: '" + string + "' para '";
            

            GoogleTradutor r = new GoogleTradutor(string);
            string = r.traduzir(this.p);

            if(string == null){
                falas = null;
                return;
            }
            
            falas.set(i, string);

            
            log += string+"'";
            //System.out.println("'");
            ProgressBar.getInstance().logOut(log);
            
            ProgressBar.getInstance().setValue(i);

            //progresso.setValue(i);
            //progresso.setStringPainted(true);
        }
    }

    private void salvar() throws IOException {
        String novo = arquivo.getPath().replace(".srt", "PT.srt");

        FileWriter arq = new FileWriter(novo);
        PrintWriter gravarArq = new PrintWriter(arq);

        for (String string : legenda) {
            gravarArq.println(string);
        }

        arq.close();

        JOptionPane.showMessageDialog(null, "Traduzido Com Sucesso!");
        //progresso.setValue(progresso.getMaximum());
        ProgressBar.getInstance().setValue(falas.size());
        ProgressBar.getInstance().close();
    }

    private void remontar() {
        for (String string : falas) {
            System.out.println(string);
        }
        
        int i = 0;
        int l = 0;
        while ( i < legenda.size()) {
            if(NumberUtils.isNumber(legenda.get(i))){
                String[] frases = this.falas.get(l).split("\\|");
                i += 2;
                if(frases.length >= 2){
                    for (int j = 0; j < frases.length; j++) {
                        legenda.set(i, frases[j]);
                        i++;
                    }
                }else{

                    legenda.set(i, falas.get(l).replace("|", ""));
                    i++;
                }
                l++;
            }else{
                i++;
            }
        }
    }

    @Override
    public void run() {
        try {
            open(arquivo);
            getFalas();
            traduzir();
            if(falas == null){
                ProgressBar.getInstance().close();
                return;
            }
            remontar();
            salvar();
        } catch (Exception ex) {
            ProgressBar.getInstance().logOut(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao traduzir");
            System.exit(1);
        }
    }
}
