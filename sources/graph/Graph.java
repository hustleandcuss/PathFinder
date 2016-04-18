package graph;

import java.util.*;

interface Graph<N> {
	void add(N newNode);
	void connect(N from, N to, String name, int weight);
	void setConnectionWeight(N from, N to, int newWeight);
	Set<N> getNodes();
	ArrayList<Edge<N>> getEdgesFrom(N node);
	Edge<N> getEdgeBetween(N n1, N n2);
	String toString();

}