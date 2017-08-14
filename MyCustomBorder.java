import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author sorifiend
 */
public class MyCustomBorder extends AbstractBorder
{
    private Color borderColour;
    private int borderThickness = 10;
    private Point firstSlider = new Point(0, 0);
    private Point secondSlider = new Point(0, 0);
    private BufferedImage firstSliderImage;
    private BufferedImage secondSliderImage;

    Boolean draggingFirst = false;
    Boolean draggingSecond = false;
    
    //Usage
    //Create border
    //MyCustomBorder border = new MyCustomBorder(Color.BLACK, 10, new Point(0, 0), img1, new Point(0, 0), img2);
    //
    //Add border to component
    //my_jLabel.setBorder( border );
    //
    //Add action listeners for dragging sliders
    //border.addListeners(my_jLabel);
    
    public MyCustomBorder(Color colour, int thickness, Point firstSlider, BufferedImage firstSliderImage, Point secondSlider, BufferedImage secondSliderImage)
    {
	borderColour = colour;
	borderThickness = thickness;
	this.firstSlider = firstSlider;
	this.secondSlider = secondSlider;
	this.firstSliderImage = firstSliderImage;
	this.secondSliderImage = secondSliderImage;
    }
    
    //listeners for dragging
    void addListeners(Component button)
    {
	button.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
	{
	    public void mouseDragged(java.awt.event.MouseEvent evt)
	    {	
		//Only drag if a slider was selected
		if (draggingFirst)
		{
		    //update position of silder
		    firstSlider = snapToEdge(evt.getPoint(), evt.getComponent());
		    evt.getComponent().repaint();
		}
		else if (draggingSecond)
		{
		    //update position of silder
		    secondSlider = snapToEdge(evt.getPoint(), evt.getComponent());
		    evt.getComponent().repaint();
		}
	    }
	});
	button.addMouseListener(new java.awt.event.MouseAdapter()
	{
	    //check if a slider was selected
	    public void mousePressed(java.awt.event.MouseEvent evt)
	    {
		if (isInside(evt.getPoint(), firstSlider))
		{
		    draggingFirst = true;
		}
		else if (isInside(evt.getPoint(), secondSlider))
		{
		    draggingSecond = true;
		}
	    }
	    public void mouseReleased(java.awt.event.MouseEvent evt)
	    {
		//cancel selected slider
		draggingFirst = false;
		draggingSecond = false;
	    }
	});
    }
    
    //check if a slider was selected
    private Boolean isInside(Point clicked, Point toCheck)
    {
	if (clicked.x > toCheck.x && clicked.x < toCheck.x + borderThickness)
	{
	    if (clicked.y > toCheck.y && clicked.y < toCheck.y + borderThickness)
	    {
		return true;
	    }
	}
	return false;
    }
    
    //snap a sliders co-ords to as edge
    private Point snapToEdge(Point dragged, Component label)
    {	
	//work out how close to each edge
	int topEdge = dragged.y;
	int leftEdge = dragged.x;
	int rightEdge = label.getWidth()- dragged.x;
	int bottomEdge = label.getHeight() - dragged.y;
	
	//snap to slider co-ords to closest edge
	if (topEdge < leftEdge && topEdge < rightEdge && topEdge < bottomEdge)
	{
	    dragged.y = 0;
	}
	else if (leftEdge < rightEdge && leftEdge < bottomEdge)
	{
	    dragged.x = 0;
	}
	else if (rightEdge < bottomEdge)
	{
	    dragged.x = label.getWidth()-borderThickness;
	}
	else
	{
	    dragged.y = label.getHeight()-borderThickness;
	}
	return dragged;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {
	super.paintBorder(c, g, x, y, width, height);
	Graphics2D g2d = null;

	if (g instanceof Graphics2D)
	{
	    g2d = (Graphics2D) g;
	    
	    //Draw border fill (currently hard coded to white, but can be changed)
	    g2d.setColor(Color.white);
            //Top
            g2d.fill(new Rectangle2D.Double(0, 0, width, borderThickness));
	    //Left
            g2d.fill(new Rectangle2D.Double(0, 0, borderThickness, height));
            //Bottom
	    g2d.fill(new Rectangle2D.Double(0, height-borderThickness, width, borderThickness));
	    //Right
            g2d.fill(new Rectangle2D.Double(width-borderThickness, 0, borderThickness, height));
	    
	    //draw black seperator
	    g2d.setColor(borderColour);
            //Top
            g2d.fill(new Rectangle2D.Double(borderThickness, borderThickness, width-(borderThickness*2), 1));
	    //Left
            g2d.fill(new Rectangle2D.Double(borderThickness, borderThickness, 1, height-(borderThickness*2)));
            //Bottom
	    g2d.fill(new Rectangle2D.Double(borderThickness, height-borderThickness-1, width-(borderThickness*2), 1));
	    //Right
            g2d.fill(new Rectangle2D.Double(width-borderThickness-1, borderThickness, 1, height-(borderThickness*2)));
	    
	    //draw sliders an custom position	    
	    g2d.drawImage(scale(secondSliderImage), null, secondSlider.x, secondSlider.y);
	    g2d.drawImage(scale(firstSliderImage), null, firstSlider.x, firstSlider.y);
	}
    }

    @Override
    public Insets getBorderInsets(Component c)
    {
	return (getBorderInsets(c, new Insets(borderThickness, borderThickness, borderThickness, borderThickness)));
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets)
    {
	insets.left = insets.top = insets.right = insets.bottom = borderThickness;
	return insets;
    }

    @Override
    public boolean isBorderOpaque()
    {
	return false;
    }

    //scale slider images to fit border size
    public BufferedImage scale(BufferedImage image)
    {
	BufferedImage resizedImage = null;
	if (image != null)
	{
	    double border = borderThickness;
	    resizedImage = new BufferedImage(borderThickness, borderThickness, TYPE_INT_ARGB);
	    Graphics2D g = resizedImage.createGraphics();
	    AffineTransform at = AffineTransform.getScaleInstance(border / (double)image.getWidth(), border / (double)image.getHeight());
	    g.drawRenderedImage(image, at);
	}
	return resizedImage;
    }
}
