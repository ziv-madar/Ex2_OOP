package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

public class DWG_JsonDeserializer implements JsonDeserializer<DWGraph_DS> {
	@Override
	public DWGraph_DS deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		DWGraph_DS dwg = new DWGraph_DS();
		JsonObject jsonObj = json.getAsJsonObject();
		JsonArray arrE = jsonObj.get("Edges").getAsJsonArray();
		JsonArray arrV = jsonObj.get("Nodes").getAsJsonArray();
		Iterator<JsonElement> iterV = arrV.iterator();
		while (iterV.hasNext()) {
			JsonElement vertex = iterV.next();
			JsonObject nodesObject = vertex.getAsJsonObject();

			node_data ansV = new NodeData(nodesObject.get("id").getAsInt());
			StringTokenizer stPos = new StringTokenizer(nodesObject.get("pos").getAsString(),",");
			double x = Double.parseDouble(stPos.nextToken());
			double y = Double.parseDouble(stPos.nextToken());
			double z = Double.parseDouble(stPos.nextToken());
			geo_location gl = new GoeLocation(x, y, z);
			ansV.setLocation(gl);
			dwg.addNode(ansV);
		}
		Iterator<JsonElement> iterE = arrE.iterator();
		while(iterE.hasNext()) {
			JsonElement edge = iterE.next();
			JsonObject edgesObject = edge.getAsJsonObject();
			int src = edgesObject.get("src").getAsInt();
			double weight = edgesObject.get("w").getAsDouble();
			int dest = edgesObject.get("dest").getAsInt();
			dwg.connect(src, dest, weight);
		}
		return dwg;
	}
}