JFLAGS=-J-Xmx5m -J-Xmx4m

main: src/*java src/Searching/*.java
	javac  $(JFLAGS) src/*.java src/Searching/*.java

run:
	java -Xmx64m src.Main

clean:
	$(RM) src/*.class src/Searching/*.class
