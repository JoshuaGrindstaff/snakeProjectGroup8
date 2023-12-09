package com.example.snake;

public class HighScore {
}
//public class HighScore implements Comparable<HighScore> {
//  private String playerName;
//private int score;

// Constructors, getters, and setters

//@Override
//public int compareTo(HighScore other) {
// Implement comparison logic based on scores
//  return Integer.compare(other.getScore(), this.score);
//}
//*Tiaera: private void saveHighScores() {
//    // Use SharedPreferences for simplicity (you can use a file or a database for more complex scenarios)
//    SharedPreferences preferences = context.getSharedPreferences("HighScores", Context.MODE_PRIVATE);
//    SharedPreferences.Editor editor = preferences.edit();
//
//    // Convert the list of HighScore objects to a JSON string
//    Gson gson = new Gson();
//    String json = gson.toJson(highScores);
//
//    // Save the JSON string
//    editor.putString("highScores", json);
//    editor.apply();
//}
//
//private void loadHighScores() {
//    SharedPreferences preferences = context.getSharedPreferences("HighScores", Context.MODE_PRIVATE);
//    String json = preferences.getString("highScores", "");
//
//    // Convert the JSON string back to a list of HighScore objects
//    Gson gson = new Gson();
//    Type type = new TypeToken<List<HighScore>>(){}.getType();
//    highScores = gson.fromJson(json, type);
//
//    if (highScores == null) {
//        highScores = new ArrayList<>();
//    }
//}
//}
