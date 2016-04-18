package graph;

public class Edge<E> {

	private E dest;
	private String name;
	private int weight;

	public Edge(E dest, String name, int weight) {
		if(weight < 0) {
			throw new IllegalArgumentException("Weight can't be less than 0.");

		} else {
			this.dest = dest;
			this.name = name;
			this.weight = weight;

		}
	}

	public E getDestination() {
		return dest;

	}

	public int getWeight() {
		return weight;

	}

	public void setWeight(int newWeight) {
		if(weight < 0) {
			throw new IllegalArgumentException("Weight can't be less than 0.");

		} else {
			weight = newWeight;
			
		}
	}

	public String getName() {
		return name;

	}

	public String toString() {
		return name + " to " + dest + " (" + weight + "h)\n";
		
	}
}