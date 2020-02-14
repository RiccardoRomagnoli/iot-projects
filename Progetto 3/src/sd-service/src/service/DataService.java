package service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.LinkedList;

/*
 * Data Service as a vertx event-loop 
 */
public class DataService extends AbstractVerticle {

	private int port;
	private static final int MAX_SIZE = 10;
	private LinkedList<DataPoint> values;
	private boolean tokenAvailability = true;
	private boolean availability = true;
	private int nDeposit = 0;
	private int totalWeight = 0;
	
	public DataService(int port) {
		values = new LinkedList<>();		
		this.port = port;
	}

	@Override
	public void start() {		
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());

		//SmartDumpster Server API
		router.post("/api/dodeposit").handler(this::doDeposit);
		router.post("/api/setavailability").handler(this::setAvailability);
		router.get("/api/ndeposit").handler(this::nDeposit);
		router.get("/api/token").handler(this::sendToken);
		router.get("/api/getdeposit").handler(this::sendDeposits);
		router.get("/api/releaseToken").handler(this::releaseToken);
		router.get("/api/isAvailable").handler(this::isAvailable);
		
		vertx.createHttpServer()
			 .requestHandler(router::accept)
			 .listen(port);

		log("Service ready.");
	}
	
	//POST
	private void doDeposit(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		JsonObject res = routingContext.getBodyAsJson();
		if (res == null) {
			sendError(400, response);
		} else {
			String type = res.getString("type");
			String date = res.getString("date");
			//generazione randomica del peso dell'oggetto
			int weight = (int)(Math.random() * 1100 -99 ); //da 100 a 1000 grammi
			
			values.addFirst(new DataPoint(type, date, weight));
			if (values.size() > MAX_SIZE) {
				values.removeLast();
			}
			nDeposit++;
			totalWeight+=weight;
			log("Deposit type: " + type + " in " + date +" and weights " + weight);
			response.setStatusCode(200).end();
		}
		this.tokenAvailability = true;
	}
	
	//POST
	private void setAvailability(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		JsonObject res = routingContext.getBodyAsJson();
		if (res == null) {
			sendError(400, response);
		} else {
			boolean newState = res.getBoolean("value");
			this.availability = newState;
			
			//Reset stats when re-establish the availability -> via DashBoard
			if(newState) {
				this.nDeposit = 0;
				this.totalWeight = 0;
			}
		}
		log("Updated the SmartDumpster availability status: "+this.availability);
		response.setStatusCode(200).end();
	}
	
	private void releaseToken(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		this.tokenAvailability = true;
		log("Token released");
		response.setStatusCode(200).end();
	}
	
	private void sendToken(RoutingContext routingContext) {
		JsonArray arr = new JsonArray();
		JsonObject data = new JsonObject();
		if(this.tokenAvailability && this.availability) {
			data.put("value", true);
			this.tokenAvailability = false;
			log("Token assigned");
		}
		else {
			data.put("value", false);
		}
		arr.add(data);
		routingContext.response()
					  .putHeader("content-type", "application/json")
					  .end(arr.encodePrettily());
	}
	
	private void isAvailable(RoutingContext routingContext) {
		JsonArray arr = new JsonArray();
		JsonObject data = new JsonObject();
		data.put("value", this.availability);
		arr.add(data);
		routingContext.response()
					  .putHeader("content-type", "application/json")
					  .end(arr.encodePrettily());
	}
	
	private void nDeposit(RoutingContext routingContext) {
		JsonArray arr = new JsonArray();
		JsonObject data = new JsonObject();
		data.put("weight", totalWeight);
		data.put("nDeposit", nDeposit);
		arr.add(data);
		routingContext.response()
		.putHeader("content-type", "application/json")
		.end(arr.encodePrettily());
		log("Weights of deposits sent...");
	}
	
	private void sendDeposits(RoutingContext routingContext) {
		JsonArray arr = new JsonArray();
		for (DataPoint p: values) {
			JsonObject data = new JsonObject();
			data.put("date", p.getDate());
			data.put("type", p.getType());
			data.put("weight", p.getWeight());
			arr.add(data);
		}
		routingContext.response()
		.putHeader("content-type", "application/json")
		.end(arr.encodePrettily());
	}
	
	private void sendError(int statusCode, HttpServerResponse response) {
		response.setStatusCode(statusCode).end();
	}

	private void log(String msg) {
		System.out.println("[DATA SERVICE] "+msg);
	}

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		DataService service = new DataService(8080);
		vertx.deployVerticle(service);
	}
}