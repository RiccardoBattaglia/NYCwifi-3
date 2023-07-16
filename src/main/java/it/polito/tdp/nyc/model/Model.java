package it.polito.tdp.nyc.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.db.NYCDao;


public class Model {
	

	
	private NYCDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private Map<String, LatLng> locationMap;
	private List<Hotspot> hotspotList;
	
	public Model() {
		
		this.dao = new NYCDao();
		
		this.hotspotList = this.dao.getAllHotspot();
		
//		this.retailersMap=new HashMap<>();
//		for(Retailers i : retailersList) {
//			this.retailersMap.put((i), this.dao.getProdottiPerRetailerInAnno(i, anno));
//		}
		
	}
	

	public void creaGrafo(String provider , double distanza) {
		// TODO Auto-generated method stub

	this.grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
	// Aggiunta VERTICI 
	List<String> vertici=new LinkedList<>();
	
	for(Hotspot i : this.hotspotList) {
		if(i.getProvider().equals(provider) && !vertici.contains(i.getLocation())) {
			vertici.add(i.getLocation());
		}
	}
	
	Graphs.addAllVertices(this.grafo, vertici);
	
	
	this.locationMap=new HashMap<>();
	for(String i : this.grafo.vertexSet()) {
		this.locationMap.put(i, this.dao.getLatLgt(provider, i));
	}

	
	// Aggiunta ARCHI
	
	for (String v1 : vertici) {
		for (String v2 : vertici) {
			if(!v1.equals(v2)) {
			if(LatLngTool.distance(this.locationMap.get(v1), this.locationMap.get(v2), LengthUnit.KILOMETER)<=distanza  && LatLngTool.distance(this.locationMap.get(v1), this.locationMap.get(v2), LengthUnit.KILOMETER)>=0){ 

		      this.grafo.addEdge(v1,v2);
		      this.grafo.setEdgeWeight(this.grafo.getEdge(v1, v2),LatLngTool.distance(this.locationMap.get(v1), this.locationMap.get(v2), LengthUnit.KILOMETER));
			}
			}
			}
			}

	}

public int nVertici() {
	return this.grafo.vertexSet().size();
}

public int nArchi() {
	return this.grafo.edgeSet().size();
}

public Set<String> getVertici(){
	
	Set<String> vertici=this.grafo.vertexSet();
	
	return vertici;
}

public Set<DefaultWeightedEdge> getArchi(){
	
	Set<DefaultWeightedEdge> archi=this.grafo.edgeSet();
	
	return archi;
}

//public List<Set<User>> getComponente() {
//	ConnectivityInspector<User, DefaultWeightedEdge> ci = new ConnectivityInspector<>(this.grafo) ;
//	return ci.connectedSets() ;
//}

//public Set<Retailers> getComponente(Retailers v) {
//	ConnectivityInspector<Retailers, DefaultWeightedEdge> ci = new ConnectivityInspector<>(this.grafo) ;
//	return ci.connectedSetOf(v) ;
//}
//
//public List<String> getVerticiName(){
//	
//	List<String> nomi=new LinkedList<>();
//	
//	for(Retailers i : this.grafo.vertexSet()) {
//		nomi.add(i.getName());
//	}
//	
//	Collections.sort(nomi);
//	
//	return nomi;
//}	
//	

public List<String> getProvider(){
	
	List<String> provider=new LinkedList<>();
	
	for(Hotspot i : hotspotList) {
		if(!provider.contains(i.getProvider())) {
		provider.add(i.getProvider());
		}
	}
	
	Collections.sort(provider);
	
	return provider;
}

public List<Pair<String, Integer>> getMaxVicini() {
	
	int max = 0;
	List<Pair<String, Integer>> result = new LinkedList<>();
	
	
	for(String v : this.grafo.vertexSet()){
		if(this.grafo.edgesOf(v).size()>=max) {
			max=this.grafo.edgesOf(v).size();
		}
	}
	
	for(String v : this.grafo.vertexSet()){
		if(this.grafo.edgesOf(v).size()==max) {
			result.add(new Pair<>(v, max));
		}
	}
//	Collections.sort(result);
	
//	System.out.println(result);
	
	return result;
}

}
