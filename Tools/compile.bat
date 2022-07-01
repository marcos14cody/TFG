:: Compile processors and annotations
@javac src\processors\*.java src\annotations\*.java -d build
:: Create .jar file
@jar -c -f lib\processorlib.jar build/processors/*.class build/annotations/*.class
:: Compile main package using library
@javac -proc:only -cp build -processor processors.RootProcessor src\main\*.java
:: Visualize output
@del /q class_diagram.png
@dot -Tpng class_diagram.dot -o class_diagram.png