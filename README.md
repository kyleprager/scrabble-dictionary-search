Given your scrabble letters this program will figure out all possible words you can play and sort them by their point score. It also works with blank squares by using a '?' in the string of scrabble letters given to the program.

How to run:

1.  Build the project using "mvn install" from inside the root folder.
2.  Go into the newly created "target" folder.
3.  Run the following command: 
    java -cp DictionarySearch-1.0.jar org.kprager.search.concurrent.MultithreadedSearch [scrabble_letters]
