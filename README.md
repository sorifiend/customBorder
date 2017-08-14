# customBorder
An example java class for a question on StackOverflow: https://stackoverflow.com/questions/45667001/attaching-images-to-jlabels-border

To use this code copy it into your project as a new class, then add the following to your form class:

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
