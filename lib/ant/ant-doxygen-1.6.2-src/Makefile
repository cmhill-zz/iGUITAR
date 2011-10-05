
all:
	mkdir -p bin
	javac -cp lib/ant.jar -d bin src/org/doxygen/tools/*.java
	cd bin && jar cf ../lib/ant_doxygen.jar org/doxygen/tools/*.class

clean:
	-rm -r bin
	-rm lib/ant_doxygen.jar
