package graph;

import java.util.*;

public class GraphMethods {

	private static <N> void dfs(ListGraph<N> listGraph, N where, Set<N> visited) {
		visited.add(where);
		for(Edge<N> e : listGraph.nodes.get(where)) {
			if(!visited.contains(e.getDestination())) {
				dfs(listGraph, e.getDestination(), visited);

			}
		}
	}

	public static <N> boolean pathExists(ListGraph<N> listGraph, N from, N to) {
		Set<N> visited = new HashSet<N>(); 
		dfs(listGraph, from, visited);

		return visited.contains(to);

	}

	private static <N> void dfs2(ListGraph<N> listGraph, N where, N whereFrom, Set<N> visited, Map<N, N> via) {
		visited.add(where);
		via.put(where, whereFrom);
		for(Edge<N> e : listGraph.nodes.get(where)) {
			if(!visited.contains(e.getDestination())) {
				dfs2(listGraph, e.getDestination(), where, visited, via);

			}
		}
	}

	public static <N> List<Edge<N>> getPath(ListGraph<N> listGraph, N from, N to) { //hittar en väg vilken som helst
		Set<N> visited = new HashSet<N>();
		Map<N, N> via = new HashMap<N, N>();
		dfs2(listGraph, from, null, visited, via);

		List<Edge<N>> theWay = new ArrayList<Edge<N>>();

		N where = to;

		while(!where.equals(from)) {
			N n = via.get(where);
			Edge<N> e = listGraph.getEdgeBetween(n, where);
			theWay.add(e);
			where = n;
		}

		Collections.reverse(theWay);
		return theWay;
 
	}

}