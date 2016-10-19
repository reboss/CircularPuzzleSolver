JFLAGS=-J-Xmx5m -J-Xmx4m

main: src/*java src/Searching/*.java
	javac  $(JFLAGS) src/*.java src/Searching/*.java 2> /dev/null
	echo "#!/bin/bash" > airberlin
	echo "java -Xmx1024m src.Main" >> airberlin
	chmod 700 airberlin 

run:
	java -Xmx1024m src.Main

clean:
	$(RM) src/*.class src/Searching/*.class
