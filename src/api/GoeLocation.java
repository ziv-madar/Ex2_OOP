package api;

public class GoeLocation implements geo_location {
	private double x;
	private double y;
	private double z;
	
	

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public GoeLocation(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public double x() {
		// TODO Auto-generated method stub
		return this.x;
	}

	@Override
	public double y() {
		// TODO Auto-generated method stub
		return this.y;
	}

	@Override
	public double z() {
		// TODO Auto-generated method stub
		return this.z;
	}

	@Override
	public double distance(geo_location g) {
		// TODO Auto-generated method stub
		return Math.sqrt(Math.pow(this.x-g.x(), 2)+Math.pow(this.y-g.y(), 2)+Math.pow(this.z-g.z(), 2));
	}

	@Override
	public String toString() {
		return "GoeLocation{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}
}
