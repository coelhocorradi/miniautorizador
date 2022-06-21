package br.com.testebackend.miniautorizador.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBClient {

	private MongoClient client;

	private MongoDatabase database;

	private MongoCollection<Document> gradesCollection;

	public MongoDBClient() {
		try {
			client = MongoClients.create(System.getProperty("mongodb.uri"));
			if (client == null)
				throw new Exception("client is null!");

			database = client.getDatabase(System.getProperty("mongodb.database"));
			if (database == null)
				throw new Exception("database is null!");

			gradesCollection = database.getCollection("grades");
			if (gradesCollection == null)
				throw new Exception("gradesCollection is null!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
