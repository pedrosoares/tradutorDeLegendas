/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo;

import br.sc.pedro.legenda.entity.Proxy;
import br.sc.pedro.legenda.view.ProgressBar;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.IOException;
import java.util.List;
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

    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Exception {
       Robo robo = new Robo();
       //robo.paginaPrincipalFirefox();
       //robo.xpath();
       
        
    }

    private Robo() {
        
    }
    
    
    public void paginaPrincipal() throws Exception {
        final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage("http://www.google.com");
       
        final String pageAsXml = page.asXml();
        //Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

        final String pageAsText = page.asText();
        //Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
        System.out.println(pageAsXml);

        webClient.closeAllWindows();
        
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



            //final HtmlDivision div = (HtmlDivision) page.getByXPath("//div[@dir='ltr']").get(0);
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
        
       
        
        //final HtmlDivision div = (HtmlDivision) page.getByXPath("//div[@dir='ltr']").get(0);
        final HtmlDivision div = (HtmlDivision) page.getByXPath("//div[@class='t0']").get(0);
        String saida = div.getTextContent();
        webClient.closeAllWindows();
        return saida;
    }
    
    public void xpath() throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
        final HtmlPage page = webClient.getPage("https://translate.google.com.br/m?hl=pt-BR&sl=auto&tl=pt&ie=UTF-8&prev=_m&q="+en);

        //get list of all divs
        //final List<?> divs = page.getByXPath("//span");
        
        //final HtmlForm form = page.getFormByName("text_form");

        //final HtmlSubmitInput button = form.getInputByValue("Translate");
        
        //final HtmlPage page2 = button.click();

        //get div which has a 'name' attribute of 'John'
        //System.out.print(page.asXml());
        final HtmlDivision div = (HtmlDivision) page.getByXPath("//div[@dir='ltr']").get(0);
        
        //System.out.println(divs);
        System.out.println(div.getTextContent());
        webClient.closeAllWindows();
    }
    
    public WebClient homePage_proxy(Proxy p) throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17, p.getServidor(), Integer.parseInt(p.getPorta()));

        //set proxy username and password 
        final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
        credentialsProvider.addCredentials(p.getUsuario(), p.getSenha());

        return webClient;
    }   
    
    public void submittingForm() throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
        

        // Get the first page
        final HtmlPage page1 = webClient.getPage("https://translate.google.com/#en/pt/my%20name%20is");

        //System.out.println(page1.asXml());
        
        // Get the form that we are dealing with and within that form, 
        // find the submit button and the field that we want to change.
        final HtmlForm form = page1.getFormByName("text_form");

        final HtmlSubmitInput button = form.getInputByValue("Translate");
        //final HtmlTextInput textField = form.getInputByName("q");

        // Change the value of the text field
        //textField.setValueAttribute("teste");

        // Now submit the form by clicking the button and get back the second page.
        final HtmlPage page2 = button.click();
        
        System.out.println(page2.asXml());
        
        webClient.closeAllWindows();
    }

    String traduzir(Proxy p) throws Exception {
        if(p.UsarProxy()){
            return traduzirComProxy(p);
        }else{
            return traduzir();
        }
    }
}
