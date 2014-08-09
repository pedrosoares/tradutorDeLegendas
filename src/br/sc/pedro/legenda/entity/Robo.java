/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sc.pedro.legenda.entity;
import br.sc.pedro.legenda.view.ProgressBar;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import javax.swing.JOptionPane;

/**
 *
 * @author robson
 */
public class Robo {
    
    
    private String en = "本森？在吗";

    public Robo(String en) {
        this.en = en;
    }
    
    public void paginaPrincipalFirefox() throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
        final HtmlPage page = webClient.getPage("http://sc.senai.br");
        System.out.println("HtmlUnit - Welcome to HtmlUnit: "+ page.getTitleText());

        webClient.closeAllWindows();
    }
    
    public String traduzir() throws Exception {
        String saida = "";
        try{
            final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
            final HtmlPage page = webClient.getPage("https://translate.google.com.br/m?hl=pt-BR&sl=auto&tl=pt&ie=UTF-8&prev=_m&q="+en);
            final HtmlDivision div = (HtmlDivision) page.getByXPath("//div[@class='t0']").get(0);
            saida = div.getTextContent();
            webClient.closeAllWindows();
        }catch(Exception e){
            ProgressBar.getInstance().logOut(e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao traduzir, verifique sua conexão ou Servidor Proxy");
            return null;
        }
        return saida;
    }
    
    public String traduzirComProxy(Proxy p) throws Exception{
        final WebClient webClient = homePage_proxy(p);
        final HtmlPage page = webClient.getPage("https://translate.google.com.br/m?hl=pt-BR&sl=auto&tl=pt&ie=UTF-8&prev=_m&q="+en);
        final HtmlDivision div = (HtmlDivision) page.getByXPath("//div[@class='t0']").get(0);
        String saida = div.getTextContent();
        webClient.closeAllWindows();
        return saida;
    }
    
    public WebClient homePage_proxy(Proxy p) throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17, p.getServidor(), Integer.parseInt(p.getPorta()));

        //set proxy username and password 
        final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
        credentialsProvider.addCredentials(p.getUsuario(), p.getSenha());

        return webClient;
    }   

    String traduzir(Proxy p) throws Exception {
        if(p.UsarProxy()){
            return traduzirComProxy(p);
        }else{
            return traduzir();
        }
    }
}
