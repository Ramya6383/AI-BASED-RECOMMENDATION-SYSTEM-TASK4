package com.example;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.io.File;
import java.util.List;

public class RecommenderApp {
    public static void main(String[] args) {
        try {
            // Load data
            DataModel model = new FileDataModel(new File("src/main/resources/data.csv"));

            // Calculate user similarity
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Find nearest neighbors
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Build the recommender system
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Recommend 3 items for user 3
            List<RecommendedItem> recommendations = recommender.recommend(3, 3);

            // Display results
            System.out.println("Recommendations for User 3:");
            for (RecommendedItem item : recommendations) {
                System.out.printf("Item ID: %d, Estimated Preference: %.2f\n", item.getItemID(), item.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
