/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sc.pedro.legenda.entity;

/**
 *
 * @author pedro.soares
 */
public class Proxy {
    
    private Boolean usarProxy = false;
    private String servidor;
    private String usuario;
    private String senha;
    private String porta;

    public Proxy(Boolean usarProxy, String servidor,String usuario, String senha, String porta) {
        this.usuario = usuario;
        this.servidor = servidor;
        this.senha = senha;
        this.usarProxy = usarProxy;
        this.porta = porta;
    }
    
    public Boolean UsarProxy(){
        return usarProxy;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    
    
    public Proxy(boolean  usarProxy) {
        this.usarProxy = usarProxy;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    
    
    
    public Boolean isUsarProxy() {
        return usarProxy;
    }

    public void setUsarProxy(Boolean usarProxy) {
        this.usarProxy = usarProxy;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
    
    
}
