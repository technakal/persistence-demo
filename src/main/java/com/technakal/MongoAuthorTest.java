package com.technakal;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class MongoAuthorTest {

    public static void main(String[] args) {

        // get our uri
        String uri = "mongodb://dev_user:password@localhost:27017/jdnd-c3";

        // create our client
        MongoClient client = MongoClients.create(uri);

        // get our database
        MongoDatabase database = client.getDatabase("jdnd-c3");

        // create our collection
        database.createCollection("authors");

        // get our new collection
        MongoCollection<Document> authors = database.getCollection("authors");

        // create author documents
        Document author1 = new Document()
                .append("first_name", "Marcus")
                .append("last_name", "Borg");
        Document author2 = new Document()
                .append("first_name", "Nathan")
                .append("last_name", "Ballingrud")
                .append("birth_year", 1970)
                .append("sex", "male");
        Document author3 = new Document()
                .append("first_name", "Stephen")
                .append("last_name", "King")
                .append("birth_year", 1947)
                .append("sex", "male");

        // add authors to the collection
        authors.insertOne(author1);
        authors.insertMany(new ArrayList<Document>() {
            {
                add(author2);
                add(author3);
            }
        });

        // get an author's id
        ObjectId _id = author1.getObjectId("_id");

        // replace an author with one that has more detail
        authors.replaceOne(
                new Document("_id", _id),
                new Document()
                    .append("first_name", "Marcus")
                    .append("last_name", "Borg")
                    .append("gender", "male")
                    .append("birth_year", 1935)
        );

        // update the field "gender" to "sex"
        authors.updateOne(
                new Document("_id", _id),
                new Document()
                    .append("$rename", new Document("gender", "sex"))
        );

        // do an upsert on an author
        authors.replaceOne(
                new Document("first_name", "Ao"),
                new Document()
                    .append("first_name", "Ao")
                    .append("last_name", "Jyumonji")
                    .append("sex", "male"),
                new ReplaceOptions().upsert(true)
        );

        // delete an author
        // authors.deleteOne(new Document("first_name", "Ao"));

        client.close();
    }
}
