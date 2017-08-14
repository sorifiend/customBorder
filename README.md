# customBorder
To use add the following:

`MyCustomBorder border = new MyCustomBorder(Color colour, int borderThickness, Point firstSlider, BufferedImage firstSliderImage, Point secondSlider, BufferedImage secondSliderImage);`

Example:

`MyCustomBorder border = new MyCustomBorder(Color.BLACK, 10, new Point(0, 0), img1, new Point(0, 0), img2);
my_jLabel.setBorder( border );
border.addListeners(my_jLabel);`
