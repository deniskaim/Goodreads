# Goodreads: Book Recommender

## Overview
Goodreads: Book Recommender is a Java-based application that provides book recommendations based on statistical approaches from Machine Learning. It processes a dataset of the 10,000 most recommended books of all time and utilizes text analysis techniques to suggest similar books.

## Features
- **Book Recommendation System**: Uses TF-IDF and Overlap Coefficient to recommend books based on similarity.
- **Book Search**: Search books by author, genres, or keywords.
- **Genre and Description Similarity Calculation**: Determines book similarity based on overlapping genres and textual descriptions.
- **Stopword Filtering**: Removes common words that do not add meaningful information.
- **Efficient Data Parsing**: Utilizes OpenCSV for seamless CSV file handling.

## Technologies Used
- **Java 17+**
- **OpenCSV** for CSV file parsing
- **JUnit** for unit testing - achieved 95% code coverage
- **Collections Framework** for efficient data storage and retrieval
- **Stream API**

## Dataset
The application uses a dataset (`goodreads_data.csv`), which contains:
- Book title
- Author
- Description
- Genres
- Average rating
- Number of ratings
- URL to the book’s page

## How It Works
1. **Loading the Dataset**:  
   - The dataset is parsed using OpenCSV.
   - The `BookLoader` class loads the books into a `Set<Book>`.

2. **Tokenization and Stopword Removal**:  
   - The `TextTokenizer` processes book descriptions.
   - Stopwords are filtered to improve text relevance.

3. **Similarity Calculation**:  
   - `GenresOverlapSimilarityCalculator` computes similarity using the Overlap Coefficient.
   - `TFIDFSimilarityCalculator` applies the TF-IDF algorithm to compare book descriptions.

4. **Book Recommendation**:  
   - `BookRecommender` finds the most similar books based on genre and description similarity.
   - The top N recommendations are returned as a `SortedMap<Book, Double>`.

5. **Book Searching**:  
   - `BookFinder` enables searching by author, genre, or keywords.
   - Matching options (`MATCH_ALL` or `MATCH_ANY`) refine search results.
