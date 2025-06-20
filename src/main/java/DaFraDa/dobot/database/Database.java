package DaFraDa.dobot.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Database {

	private static String connectionString = "mongodb://10.100.20.181:27017";
	private static MongoClient mongoClient;
	
	static {
		try {
        	mongoClient = MongoClients.create(connectionString);
            System.out.println("=> Connection successful: " + preFlightChecks(mongoClient));
            System.out.println("=> Print list of databases:");
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
        }
        catch (Exception e) {
        	System.out.println(e.getStackTrace());
        }
	}

    static boolean preFlightChecks(MongoClient mongoClient) {
        Document pingCommand = new Document("ping", 1);
        Document response = mongoClient.getDatabase("Messdaten").runCommand(pingCommand);
        System.out.println("=> Print result of the '{ping: 1}' command.");
        System.out.println(response.toJson(JsonWriterSettings.builder().indent(true).build()));
        return response.get("ok", Number.class).intValue() == 1;
    }

	
	public static String writeDB(String collectionString, List<Object> data) {

		try {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Main");
            MongoCollection<Document>collection = sampleTrainingDB.getCollection(collectionString);
		
			Document doc = new Document("_id", new ObjectId());
			doc.append("date", System.currentTimeMillis());
			
			if(collectionString == "temperatur") {
				doc.append("temperatur", data.get(0));				
			}
			else if (collectionString == "luftfeuchtigkeit") {
				doc.append("luftfeuchtigkeit", data.get(0));
			}
			else if (collectionString == "farbe") {
				doc.append("farbe", data.get(0));
			}
			collection.insertOne(doc);
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		return null;
		
	}
	
}
