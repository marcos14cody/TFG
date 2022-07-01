:: Standart mode: Compile all processors and annotations, use build folder as source for processing main
:: 1. Compile processors and annotations
@javac src\processors\*.java src\annotations\*.java -d build
:: 2. Compile main package using build
@javac -proc:only -cp build -processor processors.RootProcessor src\main\*.java
:: 3. Visualize output
@dot -Tpng class_diagram.dot -o class_diagram.png

:: Create .jar file
:: @jar -c -f lib\processorlib.jar build/processors/*.class build/annotations/*.class

:: Library mode: Use library as source for processing main
:: 1. Compile main package using library
:: @javac -proc:only -cp lib\processorlib.jar -processor processors.RootProcessor src\main\*.java
:: 2. Visualize output
:: @dot -Tpng class_diagram.dot -o class_diagram.png