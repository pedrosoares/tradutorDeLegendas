/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sc.pedro.legenda.entity;

import java.io.File;

/**
 *
 * @author pedro.soares
 */
public class Legenda {
   
    
    
    private File legenda;

    public Legenda(File legenda) {
        this.legenda = legenda;
    }

    @Override
    public String toString() {
        return legenda.getName();
    }
    
    public File getLegenda() {
        return legenda;
    }

    public void setLegenda(File legenda) {
        this.legenda = legenda;
    }
    
    
    
}
