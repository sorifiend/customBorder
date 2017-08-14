# customBorder
To use add the following:

```
   //Create border
   MyCustomBorder border = new MyCustomBorder(Color.BLACK, 10, new Point(0, 0), img1, new Point(0, 0), img2);

   //Add border to component
   my_jLabel.setBorder( border );

   //Add action listeners for dragging sliders
   border.addListeners(my_jLabel);
```

Full usage for MyCustomBorder:

```
   MyCustomBorder border = new MyCustomBorder(Color colour, int borderThickness, Point firstSlider, BufferedImage firstSliderImage, Point secondSlider, BufferedImage secondSliderImage);
```
