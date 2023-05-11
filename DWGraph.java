/**
 * Name: Yusuf Khalid
 * University Directory ID: ykhalid
 * University ID Number: 117245387
 * I pledge on my honor that I have not given or received any 
 * unauthorized assistance on this assignment.
 * 
 * This class implements a weighted, directed, adjacency map with generics.
 * The weights of edges are measured by integers. There are various methods
 * that manipulate the vertices and edges in the graph and return information
 * or collections containing certain data. There is also a method that
 * divides the current object, returning a new graph. All methods with
 * parameters throw the IllegalArgumentException if any parameters are null.
 */

package dwGraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DWGraph<V extends Comparable<V>> {

	private HashMap<V, HashMap<V, Integer>> dwgraph;

	public DWGraph() {
		dwgraph = new HashMap<>();
	}

	/*
	 * This method adds a new vertex to dwgraph if the vertex does not already 
	 * exist and the parameter is not null. It returns true if the method 
	 * successfully adds a vertex to the graph.
	 */
	public boolean createVertex(V dataForVertex) 
			throws IllegalArgumentException {

		// checks if parameters are null
		if (dataForVertex == null) {
			throw new IllegalArgumentException();
		}

		// puts the vertex in the graph if it does not already exist
		if (dwgraph.containsKey(dataForVertex)) {
			return false;
		} else {
			dwgraph.put(dataForVertex, new HashMap<V, Integer>());
			return true;
		}
	}
	
	/*
	 * This method returns true if the parameter dataForVertex
	 * is already in the graph. 
	 */
	public boolean isVertex(V dataForVertex) throws IllegalArgumentException {
		// checks if parameters are null
		if (dataForVertex == null) {
			throw new IllegalArgumentException();
		}

		// returns true if dwgraph contains the parameter key
		if (dwgraph.containsKey(dataForVertex)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * This method returns a keySet of the graph, which is a set of 
	 * HashMap<V, Integer>.
	 */
	public Collection<V> getVertices() {

		return dwgraph.keySet();
	}

	/*
	 * This method creates an edge between parameter intialVertex and
	 * finalVertex of cost weight, if the given weight parameter is 
	 * not negative. 
	 */
	public boolean createEdge(V initialVertex, V finalVertex, int weight) 
			throws IllegalArgumentException {
		// checks if parameters are null
		if (initialVertex == null || finalVertex == null) {
			throw new IllegalArgumentException();
		}

		// returns false if weight is negative
		if (weight < 0) {
			return false;
		} else {
			// if weight is positive, the edge is added
			HashMap<V, Integer> current = dwgraph.get(initialVertex);
			current.put(finalVertex, weight);
			return true;
		}
	}

	/*
	 * This method returns the weight/cost of the edge between
	 * parameter initialVertex and finalVertex if there is an edge between
	 * the two.
	 */
	public int edgeCost(V initialVertex, V finalVertex)
			throws IllegalArgumentException {
		// checks if parameters are null
		if (initialVertex == null || finalVertex == null) {
			throw new IllegalArgumentException();
		}

		// if initial vertex is in dwgraph, it's hashmap is created
		if (dwgraph.containsKey(initialVertex)) {
			HashMap<V, Integer> current = dwgraph.get(initialVertex);

			// if final vertex is also in, it returns the edge cost
			if (current.containsKey(finalVertex)) {
				
				return current.get(finalVertex);
			}
		}

		// if either vertice was not found, -1 is returned
		return -1;

	}

	/*
	 * This method removes the edge between initialVertex and finalVertex
	 * if the edge exists. 
	 */
	public boolean removeEdge(V initialVertex, V finalVertex) 
			throws IllegalArgumentException {
		// checks if parameters are null
		if (initialVertex == null || finalVertex == null) {
			throw new IllegalArgumentException();
		}

		// gets intialVertex's hashmap
		HashMap<V, Integer> current = dwgraph.get(initialVertex);

		// removes the edge from the hashmap if it exists
		if (current.containsKey(finalVertex)) {
			current.remove(finalVertex);
			return true;
		} else {
			return false;
		}
	}

	/*
	 * This method removes the vertex dataForVertex if it exists, and returns
	 * true if it is removed successfully.
	 */
	public boolean removeVertex(V dataForVertex) 
			throws IllegalArgumentException {
		// checks if parameters are null
		if (dataForVertex == null) {
			throw new IllegalArgumentException();
		}

		// removes the vertex if it exists
		if (dwgraph.containsKey(dataForVertex)) {

			dwgraph.remove(dataForVertex);
			return true;
		} else {

			return false;
		}
	}

	/*
	 * This method returns a collection of all vertices that dataForVertex
	 * has an outgoing edge to. It returns null if dataForVertex is not in 
	 * the graph.
	 */
	public Collection<V> adjacentVertices(V dataForVertex) 
			throws IllegalArgumentException {
		// checks if parameters are null
		if (dataForVertex == null) {
			throw new IllegalArgumentException();
		}

		// returns null if dataForVertex is not in the graph
		if (!dwgraph.containsKey(dataForVertex)) {
			return null;
		}

		// returns keyset of dataForVertex's edges
		return dwgraph.get(dataForVertex).keySet();

	}

	/*
	 * This method returns a collection of all vertices that have an outgoing
	 * edge to destVertex. It returns null if dataForVertex is not in 
	 * the graph.
	 */
	public Collection<V> predecessorsOfVertex(V destVertex) 
			throws IllegalArgumentException {
		// checks if parameters are null
		if (destVertex == null) {
			throw new IllegalArgumentException();
		}

		// returns null if destVertex is null
		if (!dwgraph.containsKey(destVertex)) {
			return null;
		}

		Set<V> allVertices = dwgraph.keySet();
		HashSet<V> adjacent = new HashSet<V>();

		// loops through dwgraph using keyset
		for (V values : allVertices) {

			// creates list of edges using the hashmap
			Map<V, Integer> currentEdgeList = dwgraph.get(values);
			Set<V> currentEdges = currentEdgeList.keySet();

			// loops through the list of edges and adds them to a hashmap
			// called adjacent if they are equal to destVertex
			for (V x : currentEdges) {

				if (x.equals(destVertex)) {
					adjacent.add(values);
				}
			}
		}
		return adjacent;

	}

	/*
	 * This method removes all vertices from the dwgraph that are in the
	 * parameter verticesForNewGraph, and it returns a dwgraph with all
	 * removed vertices. 
	 */
	public DWGraph<V> divideGraph(Collection<V> verticesForNewGraph) {
		// checks if parameters are null
		if (verticesForNewGraph == null) {
			throw new IllegalArgumentException();
		}

		DWGraph<V> newGraph = new DWGraph<>();
		Set<V> allVertices = new HashSet<V>(dwgraph.keySet());

		// adds vertices that are in verticesForNewGraph
		for (V vertex : allVertices) {
			if (verticesForNewGraph.contains(vertex)) {
				newGraph.createVertex(vertex);
			}
		}

		// adds appropriate edges
		for (V vertex : allVertices) {
			Map<V, Integer> currentEdgeList = dwgraph.get(vertex);
			Set<V> currentEdges = currentEdgeList.keySet();

			for (V edge : currentEdges) {
				if (verticesForNewGraph.contains(edge)) {
					newGraph.createEdge(vertex, edge, 
							currentEdgeList.get(edge));
				}
			}
		}

		// removes values from the original graph
		for (V vertex : allVertices) {
			if (verticesForNewGraph.contains(vertex)) {
				dwgraph.remove(vertex);
			}
		}

		return newGraph;
	}
}