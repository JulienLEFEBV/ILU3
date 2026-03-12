package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class GestionCartes {
	private static Random random = new Random();
	
	public static <T> T extraire(List<T> liste) {
		if(liste.size()==1) return liste.remove(0);
		return liste.remove(random.nextInt(liste.size()-1));
	}
	
	public static <T> T extraireAlt(List<T> liste) {
		int indiceSup=random.nextInt(liste.size()-1);
		int indiceAct=0;
		Iterator<T> it=liste.iterator();
		while(it.hasNext()) {
			T eleAct=it.next();
			if(indiceAct==indiceSup) {
				it.remove();
				return eleAct;
			}
			indiceAct++;
		}
		return null;
	}
	
	public static <T> List<T> melanger(List<T> liste){
		List<T> listeMelange = new ArrayList<>();
		while(!liste.isEmpty()) {
			listeMelange.add(extraire(liste));
		}
		return listeMelange;
	}
	
	public static <T> boolean verifierMelange(List<T> liste1,List<T> liste2){
		for(T ele : liste1) {
			if(Collections.frequency(liste1,ele)!=Collections.frequency(liste2,ele)) {
				return false;
			}
		}
		return true;
	}
	
	public static <T> List<T> rassembler(List<T> liste){
		List<T> rassemble = new ArrayList<>();
		while(!liste.isEmpty()) {
			T ele = liste.remove(0);
			rassemble.add(ele);
			while(liste.contains(ele)) {
				rassemble.add(liste.remove(liste.indexOf(ele)));
			}
		}
		return rassemble;
	}
	
	public static <T> boolean verifierRassemblement(List<T> liste){
		ListIterator<T> itParc=liste.listIterator();
		ListIterator<T> itRech=liste.listIterator();
		if(itRech.hasNext()) itRech.next();
		while(itParc.hasNext()) {
			T val=itParc.next();
			if(itRech.hasNext() && val!=itRech.next()) {
				while(itRech.hasNext()) {
					if(val==itRech.next()) {
						return false;
					}
				}
				itRech=liste.listIterator(itParc.nextIndex());
			}
		}
		return true;
	}
}
