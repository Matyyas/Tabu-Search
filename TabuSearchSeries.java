import java.util.ArrayList;

public class TabuSearchSeries {




	public static ArrayList<Integer> generateListe(int n){
		ArrayList<Integer> liste=new ArrayList<Integer>();
		for(int i=1; i<=n;i++){
			liste.add(i-1,i);			
		}
		return liste;	
	}

	public static ArrayList<Integer> randomListe(ArrayList<Integer> liste){
		for(int i=0;i<2*liste.size();i++){
			double vr=Math.random()*(liste.size()-1);
			int index=(int)Math.round(vr);	
			if(index!=0){
				int a=liste.get(index);
				int b=liste.get(0);
				liste.set(0,a);
				liste.set(index,b);
			}
		}
		return liste;	
	}
	public static int fonctionCout(ArrayList<Integer> liste){
		//System.out.println(liste);
		int n=liste.size();
		ArrayList<Integer> contraintes=new ArrayList<Integer>();
		for(int i=0;i<n-1;i++){
			contraintes.add(i,Math.abs(liste.get(i)-liste.get(i+1)));
		}
		//System.out.println(contraintes);
		int value=0;
		for(int i=1;i<=contraintes.size();i++){
			if(contraintes.contains(i)){
				//System.out.println(i);
				value++;
			}
		}
		return contraintes.size()-value;	
	}

	public static ArrayList<ArrayList<Integer>> upgrade(ArrayList<ArrayList<Integer>> tabu, int t){
		if(tabu.size()>=t){
			tabu.remove(0);
			tabu.remove(1);
			//System.out.println("tabu upgrade");
		}
		return tabu;		
	}



	
	public static ArrayList<Integer> search(int maxRestart, int maxIter, int t,int n){
		ArrayList<Integer> liste= new ArrayList<Integer>();
		ArrayList<Integer> result= new ArrayList<Integer>();

		for(int k=0;k<maxRestart;k++){
			liste=randomListe(generateListe(n));
			ArrayList<ArrayList<Integer>> tabu= new ArrayList<ArrayList<Integer>>();
			int i=0;
			while(i<maxIter && fonctionCout(liste)>0){

				int index1=(int)Math.round(Math.random()*(liste.size()-1));	
				int index2=(int)Math.round(Math.random()*(liste.size()-1));	

				if(index1!=index2){
					ArrayList<Integer> liste2= (ArrayList<Integer>) liste.clone();
					int a=liste2.get(index1);
					int b=liste2.get(index2);					
					liste2.set(index1,b);
					liste2.set(index2,a);

					ArrayList<Integer> permutation=new ArrayList<Integer>();
					permutation.add(index1);
					permutation.add(index2);
					ArrayList<Integer> permutationInv=new ArrayList<Integer>();
					permutationInv.add(index2);
					permutationInv.add(index1);


					/*System.out.println("fonction cout liste 2 = "+fonctionCout(liste2)+
							"   fonction cout liste 1 = "+fonctionCout(liste));
					/*if(fonctionCout(liste)==0){
						result=liste;
					}*/
					if(fonctionCout(liste2)<=fonctionCout(liste) && !tabu.contains(permutationInv)
							&& !tabu.contains(permutation)){

						//System.out.println("tabu list "+tabu.size()+" taille t = "+t);
						liste=liste2;
						tabu.add(permutation);
						tabu.add(permutationInv);
						upgrade(tabu,t);

						if(fonctionCout(liste)==0){
							result=liste;
							i=maxIter;
							k=maxRestart;
						}

					}
				}
				i++;
			}
		}

		return result;		
	}
	public static void main(String[] args) {
		// Joue sur les param 
		/*ArrayList<Integer> l=search(200,700,15,15);
		System.out.println("Liste obtenue "+l);

		ArrayList<Integer> l2=new ArrayList<Integer>();
		for(int i=0;i<l.size()-1;i++){
			l2.add(Math.abs(l.get(i)-l.get(i+1)));
		}
		System.out.println("Liste des intervalles : "+l2);*/


		ArrayList<Integer> l3=new ArrayList<Integer>();
		ArrayList<Integer> result=new ArrayList<Integer>();
		long time=System.currentTimeMillis();
		double tempsCalcul=0;
		
		long t=0;
		while(t<=time+60000){
			
			l3=search(200,1000,40,20);
			//System.out.println("Liste : "+l3);
			t=System.currentTimeMillis();
			if(!l3.isEmpty()){
				tempsCalcul=((t-time)*Math.pow(10,-3));
				t=2*time;
				result=l3;
			}			
		}
		if(!result.isEmpty()){
			System.out.println("Liste : "+result);

			ArrayList<Integer> l4=new ArrayList<Integer>();
			for(int i=0;i<result.size()-1;i++){
				l4.add(Math.abs(result.get(i)-result.get(i+1)));
			}
			System.out.println("Liste des intervalles : "+l4);
			System.out.println("Temps de calcul de l'algorithme : "+tempsCalcul+"s");

		}


	}
}
