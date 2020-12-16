package api;

import java.util.ArrayList;
import java.util.Collection;





public class NodeData implements node_data {
	
	protected int _key;
	protected static int count = 0;
	protected String _info;
	protected static int white = 0;
	private static int gray = 1;
	private static int black = 2;
	protected int _tag;
	protected geo_location p;
	protected double w;
	
	
	
	public NodeData() {

		this._key = count++;
		this._tag = white;
		this.w = Double.MAX_VALUE;
		this._info = "";
		this.p = new GoeLocation(0,0,0);
	}
	
	public NodeData(int key) {

		this._key = key;
		this._tag = white;
		this.w = Double.MAX_VALUE;
		this._info = "";
		this.p = new GoeLocation(0,0,0);
	}

	public NodeData(int key, geo_location location, double weight) {
		this._key = key;
		this.p = new GoeLocation(location.x(), location.y(), location.z());
		this.w = weight;
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

	@Override
	public String toString() {
		return  ""+_key; 
	}
	
	

}
