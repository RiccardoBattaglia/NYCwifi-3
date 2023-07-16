/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.alg.util.Pair;

import it.polito.tdp.nyc.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<String> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="txtDistanza"
    private TextField txtDistanza; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtStringa"
    private TextField txtStringa; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtTarget"
    private ComboBox<?> txtTarget; // Value injected by FXMLLoader

    @FXML
    void doAnalisiGrafo(ActionEvent event) {
    	
    	List<Pair<String, Integer>> vicini = this.model.getMaxVicini();
    	
    	txtResult.appendText("Vertici con più vicini:\n");
    	for(Pair<String, Integer> i : vicini) {
    	txtResult.appendText(i.getFirst()+" ,#vicini="+i.getSecond()+"\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	 String provider = cmbProvider.getValue() ;
     	
     	if(provider==null) {
     		txtResult.setText("Inserire un provider.\n");
     		return ;
     	}
     	
     	String d = txtDistanza.getText() ;
     	
     	if(d.equals("")) {
     		txtResult.setText("Inserire una distanza.\n");
     		return ;
     	}
     	
     	double distanza = 0.0 ;

     	try {
 	    	distanza = Double.parseDouble(d) ;
     	} catch(NumberFormatException e) {
     		txtResult.setText("Inserire un valore numerico come distanza.\n");
     		return ;
     	}
     	
//    	creazione grafo
    	this.model.creaGrafo(provider, distanza);
    	
    	
//    	stampa grafo
    	this.txtResult.setText("Grafo creato.\n");
    	this.txtResult.appendText("#Vertici: " + this.model.nVertici() + "\n");
    	this.txtResult.appendText("#Archi: " + this.model.nArchi() + "\n\n");
    	
    	btnAnalisi.setDisable(false);
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDistanza != null : "fx:id=\"txtDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStringa != null : "fx:id=\"txtStringa\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    public void setModel(Model model) {
    	this.model = model;
    	
    	cmbProvider.getItems().addAll(this.model.getProvider());
    	btnAnalisi.setDisable(true);
    	btnPercorso.setDisable(true);
    }
}
