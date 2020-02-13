package service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/*
 * Data Service as a vertx event-loop 
 */
public class DataService extends AbstractVerticle {

	private int port;
	private static final int MAX_SIZE = 10;
	private LinkedList<DataPoint> values;
	private boolean tokenAvailability = true;
	private int nDeposit = 0;
	
	public DataService(int port) {
		values = new LinkedList<>();		
		this.port = port;
	}

	@Override
	public void start() {		
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		//BASE
		router.post("/api/data").handler(this::handleAddNewData);
		router.get("/api/data").handler(this::handleGetData);
		//SmartDumpster
		router.post("/api/dodeposit").handler(this::doDeposit);
		router.get("/api/ndeposit").handler(this::nDeposit);
		router.get("/api/token").handler(this::sendToken);
		router.get("/api/getdeposit").handler(this::sendDeposits);
		router.post("/api/setavailability").handler(this::setAvailability);
		vertx
			.createHttpServer()
			.requestHandler(router::accept)
			.listen(port);

		log("Service ready.");
	}
	
	private void nDeposit(RoutingContext routingContext) {
		JsonArray arr = new JsonArray();
		JsonObject data = new JsonObject();
		data.put("value", nDeposit);
		arr.add(data);
		routingContext.response()
		.putHeader("content-type", "application/json")
		.end(arr.encodePrettily());
		log("number of deposits sent...");
	}
	
	private void doDeposit(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		JsonObject res = routingContext.getBodyAsJson();
		if (res == null) {
			sendError(400, response);
		} else {
			String type = res.getString("type");
			String date = res.getString("date");
			
			values.addFirst(new DataPoint(type, date));
			if (values.size() > MAX_SIZE) {
				values.removeLast();
			}
			nDeposit++;
			log("Deposit type: " + type + " in " + date);
			response.setStatusCode(200).end();
		}
	}
	
	private void sendToken(RoutingContext routingContext) {
		JsonArray arr = new JsonArray();
		JsonObject data = new JsonObject();
		if(this.tokenAvailability) {
			data.put("value", true);
			this.tokenAvailability = false;
		}
		else {
			data.put("value", false);
		}
		arr.add(data);
		routingContext.response()
			.putHeader("content-type", "application/json")
			.end(arr.encodePrettily());
		log("Token released");
	}
	
	private void sendDeposits(RoutingContext routingContext) {
		JsonArray arr = new JsonArray();
		for (DataPoint p: values) {
			JsonObject data = new JsonObject();
			data.put("date", p.getDate());
			data.put("type", p.getType());
			arr.add(data);
		}
		routingContext.response()
		.putHeader("content-type", "application/json")
		.end(arr.encodePrettily());
	}
	
	private void setAvailability(RoutingContext routingContext) {
		//prendere valori da json post tag: value, valore: true/false
		HttpServerResponse response = routingContext.response();
		JsonObject res = routingContext.getBodyAsJson();
		if (res == null) {
			sendError(400, response);
		} else {
			this.tokenAvailability = res.getBoolean("value");
		}
		log("Updated the token availability status...");
		response.setStatusCode(200).end();
	}
	
	private void handleAddNewData(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		// log("new msg "+routingContext.getBodyAsString());
		JsonObject res = routingContext.getBodyAsJson();
		if (res == null) {
			sendError(400, response);
		} else {
			float value = res.getFloat("value");
			String place = res.getString("place");
			long time = System.currentTimeMillis();
			
			//values.addFirst(new DataPoint(value, time, place));
			if (values.size() > MAX_SIZE) {
				values.removeLast();
			}
			
			log("New value: " + value + " from " + place + " on " + new Date(time));
			response.setStatusCode(200).end();
		}
	}
	
	private void handleGetData(RoutingContext routingContext) {
		JsonArray arr = new JsonArray();
		for (DataPoint p: values) {
			JsonObject data = new JsonObject();
			//data.put("time", p.getTime());
			//data.put("value", p.getValue());
			//data.put("place", p.getPlace());
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