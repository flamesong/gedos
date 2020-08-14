# Pixelate application

Build:

mvn clean package

Run:

java -jar pixelate-1.0.jar {fileName} {pixelRatio}

fileName: The path to the input image.
pixelRatio: The size of pixels in the output image. This parameter is optional, default value is 5.

Example:

java -jar pixelate-1.0.jar input.jpg 10

The result image is generated as output.jpg.