package com.technakal;

import com.mongodb.client.*;
import org.bson.Document;

public class MongoMemberTest {

  public static void main(String[] args) {

    String uri = "mongodb://dev_user:password@localhost:27017/jdnd-c3";

    // todo: create client
    MongoClient client = MongoClients.create(uri);

    MongoDatabase db = client.getDatabase("jdnd-c3");

    MongoCollection<Document> members = db.getCollection("members");

    // find the first person with last name Khan
    Document khan = members.find(new Document("last_name", "Khan")).first();
    System.err.println(khan);

    // find the first female with last name Doe
    Document femDoe = members.find(
        new Document()
            .append("last_name", "Doe")
            .append("gender", "female")
    ).first();
    System.err.println(femDoe);

    // find all who are golfers
    FindIterable<Document> golfers = members.find(new Document("interests", "golf"));
    for(Document golfer : golfers) {
      System.err.println(golfer);
    }

    // find all who live in MN
    FindIterable<Document> fargoers = members.find(new Document("address.state", "MN"));
    for(Document ohsure : fargoers) {
      System.err.println(ohsure);
    }

    // count males
    long malesCount = members.countDocuments(new Document("gender", "male"));
    System.err.println("There are " + malesCount + " males.");

    // find first female, sorted by first name
    Document firstFemale = members.find(new Document("gender", "female")).sort(new Document("first_name", 1)).first();
    System.err.println(firstFemale);

    // close client
    client.close();
  }

}
