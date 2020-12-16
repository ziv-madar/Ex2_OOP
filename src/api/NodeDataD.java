package api;

public class NodeDataD extends NodeData {
	
	private double tagD;
	
	
	
	public NodeDataD() {

		this._key = count++;
		this._tag = white;
		this.w = Double.MAX_VALUE;
		this._info = "";
		this.tagD = 0;
	}
	
	public NodeDataD(int key) {

		this._key = key;
		this._tag = white;
		this.w = Double.MAX_VALUE;
		this._info = "";
		this.tagD = 0;
	}

	public NodeDataD(int key, geo_location location, double weight, double tagD) {
		this._key = key;
		this.p = new GoeLocation(location.x(), location.y(), location.z());
		this.w = weight;
		this.tagD = tagD;
	}

	@Override
	public int getKey() {
		return this._key;
	}

	@Override
	public geo_location getLocation() {
		// TODO Auto-generated method stub
		return this.p;
	}

	@Override
	public void setLocation(geo_location p) {
		this.p = p;
		
	}

	@Override
	public double getWeight() {
		
		return this.w;
	}

	@Override
	public void setWeight(double w) {
		this.w = w;
		
	}

	@Override
	public String getInfo() {
		
		return this._info;
	}

	@Override
	public void setInfo(String s) {
		this._info = s;
		
	}

	@Override
	public int getTag() {
		
		return this._tag;
	}

	@Override
	public void setTag(int t) {
		this._tag = t;
		
	}
	
	public double getTagD() {
		return tagD;
	}

	public void setTagD(double tagD) {
		this.tagD = tagD;
	}

	public String toString() {
		return  ""+_key; 
	}
	
	
	

}
