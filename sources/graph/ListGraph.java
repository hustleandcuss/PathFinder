package graph;

import java.util.*;

public class ListGraph<N> implements Graph<N> {
	protected Map<N, ArrayList<Edge<N>>> nodes = new HashMap<>();

	public void add(N newNode) {
		if(nodes.containsKey(newNode)) {
			throw new IllegalArgumentException("Key already excists!");

		}

		nodes.put(newNode, new ArrayList<Edge<N>>());

	}//add

		
	public void connect(N from, N to, String name, int weight) {
		if(weight < 0) {
			throw new IllegalArgumentException("Weight can't be less than 0");

		}

		for(Edge<N> e : nodes.get(from)) {
			if(e.getDestination().equals(to)) {
				throw new IllegalArgumentException("The edge already excists.");

			}
		}

		ArrayList<Edge<N>> fromList = nodes.get(from);
		ArrayList<Edge<N>> toList = nodes.get(to);

		if(fromList == null || toList == null) {
			throw new NoSuchElementException("There is no such objects.");

		}

		Edge<N> e1 = new Edge<N>(to, name, weight);
		fromList.add(e1);

		Edge<N> e2 = new Edge<N>(from, name, weight);
		toList.add(e2);

	}//connect
 
	public void setConnectionWeight(N from, N to, int newWeight) {
		if(newWeight < 0) {
			throw new IllegalArgumentException("The new weight can't be less than 0");

		}

		if(!getEdgesFrom(from).contains(to)) {
			throw new NoSuchElementException("There is no such edge.");

		}

		
		ArrayList<Edge<N>> fromList = nodes.get(from);
		ArrayList<Edge<N>> toList = nodes.get(to);

		if(fromList == null || toList == null) {
			throw new NoSuchElementException("There is no such objects.");

		}

		int i = 0;

		for(Edge<N> edge1 : fromList) {
			if(edge1.getDestination() == to) {
				edge1.setWeight(newWeight);
		
			}
		}

		for(Edge<N> edge2 : toList) {
			if(edge2.getDestination() == from) {
				edge2.setWeight(newWeight);
		
			}
		}
	}//setConnectionWeight

	public Set<N> getNodes() {
		return nodes.keySet();

	}//getNodes


	public ArrayList<Edge<N>> getEdgesFrom(N node) {
		if(!nodes.containsKey(node)) {
			throw new NoSuchElementException("There is no such object in the graph.");
		
		} else {
			ArrayList arr = new ArrayList<Edge<N>>(nodes.get(node));
			return arr;
		
		}
	}//getEdgesFrom

	public Edge<N> getEdgeBetween(N n1, N n2) {
		if(!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
			throw new NoSuchElementException("There is no such object in the graph.");

		}

		ArrayList <Edge<N>> arr = nodes.get(n1);

		for(Edge<N> e : arr) {
				if(e.getDestination() == n2) {
					return e;

				}
			}

			return null;

	}//getEdgeBetween	

	public String toString() {
		String str = "";

		for(Map.Entry<N, ArrayList<Edge<N>>> e : nodes.entrySet()) {
			str += e.getKey() + ": ";

			for(Edge<N> edge : e.getValue()) {
				str += edge + ", ";

			}

			str += "\n";

		} 

		return str;
		
	}//toString
}