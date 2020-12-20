package api;

public class EdgeData implements edge_data {
	
	private int src, dest, tag;
	private double weight;
	private String info;
	
	public EdgeData(int src, int dest, double w) {
		this.src = src;
		this.dest = dest;
		this.tag = 0;
		this.weight = w;
		this.info = "";
	}

	

	@Override
	public int getSrc() {
		
		return this.src;
	}

	@Override
	public int getDest() {

		return this.dest;
	}

	@Override
	public double getWeight() {

		return this.weight;
	}

	@Override
	public String getInfo() {

		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info = s;
		
	}

	@Override
	public int getTag() {

		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t;
		
	}



	@Override
	public String toString() {
		return src+"->"+dest+"("+weight+")";
	}
	

}
